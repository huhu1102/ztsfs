package com.zt.dao;

import com.zt.po.ProductionPlanDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * @author wl
 * @date 2019年5月10日 
 */
@RepositoryRestResource(collectionResourceRel = "productionplandetails", path="productionplandetails")
public interface ProductionPlanDetailsDao extends JpaRepository<ProductionPlanDetails, Long>{
	/*
	 * 自定义分页模糊条件查询
	 */
    @Query( value = "SELECT ppd.* FROM  zt_productionplandetails AS ppd \n" +
            "       \tLEFT JOIN zt_salesplan  sp ON sp.id=ppd.salesPlan_id \n" +
            "         \tLEFT JOIN zt_employee  e ON e.id=ppd.employeeId\n" +
            "          \tWHERE ppd.enabled = TRUE \n" +
            "          \tAND ppd.`status`=:state \n" +
            "\tAND IF(:clientName !='', sp.clientName LIKE %:clientName%, 1 = 1 )\n" +
            "           \tAND IF( :productName !='', sp.productName LIKE %:productName%, 1 = 1 )\n" +
            "            \tAND IF( :empName != '', e.name LIKE %:empName%, 1 = 1 )\n" +
            "            \tAND IF( :startDate != '', DATE_FORMAT(ppd.createDate, '%Y-%m-%d %k:%i:%s' ) >=:startDate, 1 = 1 )\n" +
            "            \tAND IF( :endDate != '', DATE_FORMAT(ppd.createDate, '%Y-%m-%d %k:%i:%s' ) <=:endDate, 1 = 1 ) order by  ppd.id desc",
            countQuery = "SELECT count(*) FROM  zt_productionplandetails AS ppd \n" +
                    "            \tLEFT JOIN zt_salesplan  sp ON sp.id=ppd.salesPlan_id \n" +
                    "            \tLEFT JOIN zt_employee  e ON e.id=ppd.employeeId\n" +
                    "            \tWHERE ppd.enabled = TRUE \n" +
                    "            \tAND ppd.`status`=:state  \n" +
                    "\tAND IF(:clientName !='', sp.clientName LIKE %:clientName%, 1 = 1 )\n" +
                    "            \tAND IF( :productName !='', sp.productName LIKE %:productName%, 1 = 1 )\n" +
                    "            \tAND IF( :empName != '', e.name LIKE %:empName%, 1 = 1 )\n" +
                    "            \tAND IF( :startDate != '', DATE_FORMAT(ppd.createDate, '%Y-%m-%d %k:%i:%s' ) >=:startDate, 1 = 1 )\n" +
                    "            \tAND IF( :endDate != '', DATE_FORMAT(ppd.createDate, '%Y-%m-%d %k:%i:%s' ) <=:endDate, 1 = 1 ) order by  ppd.id desc"
            ,nativeQuery = true)
    Page<ProductionPlanDetails> findSearch(@Param("productName") String productName, @Param("empName") String empName, @Param("endDate")String endDate, @Param("startDate")String startDate, @Param("clientName")String clientName,@Param("state")String state, Pageable pageable);
    /*
    根据销售计划Id
     */
    @Query("select p from ProductionPlanDetails p where p.salesPlanId=?1 and p.enabled=true")
    Page<ProductionPlanDetails> findPlan(Pageable pageable,long  salesPlanId);

    @Query(value = "SELECT p.* FROM zt_productionplandetails p " +
            " LEFT JOIN zt_salesplan s on s.id=p.salesPlanId\n" +
            " WHERE s.clientId =:clientId " +
            "AND p.enabled = TRUE " +
            "AND p.manageStatus =1",nativeQuery = true)
    Page<ProductionPlanDetails> findForOrder(Pageable pageable,@Param("clientId")long clientId);
    /*
    根据状态查
     */
    @Query("select u from ProductionPlanDetails u where u.enabled=true and u.status=1 and u.salesPlan.product.producteName like %?1% order by  u.updateDate desc")
    Page<ProductionPlanDetails> find(String query, Pageable pageable);

    ProductionPlanDetails findById(long id);

    /*
    根据销售价ID修改状态
     */
    @Modifying
    @Query("update ProductionPlanDetails p set p.status=:planStatus where  p.salesPlanId=:salesPlanId")
    int  updateStatusBySales(@Param("planStatus")Integer status,@Param("salesPlanId") long salesPlanId);

    @Modifying
    @Query("update ProductionPlanDetails p set p.status=:planStatus where p.id=:detailsId")
    int  updateStatus(@Param("planStatus")Integer status,@Param("detailsId") long id);


    /*
    根据销售计划id删除
     */
    @Modifying
    @Query("update ProductionPlanDetails  p set p.enabled=false where p.salesPlanId=?1")
    int updateEnabled(long id);

    @Query("select p  from ProductionPlanDetails p where p.salesPlanId=?1")
    List<ProductionPlanDetails> findBySalesPlanId(long id);

    /*
    修改完成数量
     */
    @Modifying
    @Query("update ProductionPlanDetails p set p.accomplishNO=p.accomplishNO+?1 where p.id=?2")
    int updateAccomplishNo(float quantity,long id);

    /*
    查找最大的计划单号
     */
    @Query("select max(p.planNo) from ProductionPlanDetails p")
    String findMaxPlanNo();

    /*
           查询所有
            */
    @Query(value = "SELECT ppd.* FROM  zt_productionplandetails AS ppd \n" +
            "       \tLEFT JOIN zt_salesplan  sp ON sp.id=ppd.salesPlan_id \n" +
            "         \tLEFT JOIN zt_employee  e ON e.id=ppd.employeeId\n" +
            "          \tWHERE ppd.enabled = TRUE \n" +
            "          \tAND ppd.`status`=1  \n" +
            "\tAND IF(:clientName !='', sp.clientName LIKE %:clientName%, 1 = 1 )\n" +
            "           \tAND IF( :productName !='', sp.productName LIKE %:productName%, 1 = 1 )\n" +
            "            \tAND IF( :empName != '', e.name LIKE %:empName%, 1 = 1 )\n" +
            "            \tAND IF( :startDate != '', DATE_FORMAT(ppd.createDate, '%Y-%m-%d %k:%i:%s' ) >=:startDate, 1 = 1 )\n" +
            "            \tAND IF( :endDate != '', DATE_FORMAT(ppd.createDate, '%Y-%m-%d %k:%i:%s' ) <=:endDate, 1 = 1 ) order by  ppd.id desc",
            countQuery = "SELECT count(*) FROM  zt_productionplandetails AS ppd \n" +
            "            \tLEFT JOIN zt_salesplan  sp ON sp.id=ppd.salesPlan_id \n" +
            "            \tLEFT JOIN zt_employee  e ON e.id=ppd.employeeId\n" +
            "            \tWHERE ppd.enabled = TRUE \n" +
            "            \tAND ppd.`status`=1 \n" +
            "\tAND IF(:clientName !='', sp.clientName LIKE %:clientName%, 1 = 1 )\n" +
            "            \tAND IF( :productName !='', sp.productName LIKE %:productName%, 1 = 1 )\n" +
            "            \tAND IF( :empName != '', e.name LIKE %:empName%, 1 = 1 )\n" +
            "            \tAND IF( :startDate != '', DATE_FORMAT(ppd.createDate, '%Y-%m-%d %k:%i:%s' ) >=:startDate, 1 = 1 )\n" +
            "            \tAND IF( :endDate != '', DATE_FORMAT(ppd.createDate, '%Y-%m-%d %k:%i:%s' ) <=:endDate, 1 = 1 ) order by  ppd.updateDate desc"
            ,nativeQuery = true)
    Page<ProductionPlanDetails> findNewPlan(@Param("productName") String productName, @Param("empName") String empName, @Param("endDate") String endDate, @Param("startDate") String startDate, @Param("clientName") String clientName, Pageable pageable);

    /*
    查询新建状态的数量
     */
    @Query(value = "select count(*) from zt_productionplandetails p where p.`status` =1",nativeQuery = true)
    Long findNew();

    /*
    查询进行中的数量
     */
    @Query(value = "select count(*) from zt_productionplandetails p where p.`status` =2",nativeQuery = true)
    Long findIng();
    @Modifying
    @Query(value = " DELETE FROM  `zt_productionplanserialnumber`   where  produtionPlan_id=?1",nativeQuery = true)
    int revokeSerials(long id);
    @Query("from ProductionPlanDetails s where s.salesPlan.id=?1")
    List<ProductionPlanDetails> getDetails(long salesPlanId);
    /*
   查询某些生产计划详情数量的总和
    */
    @Query(value = "select sum(p.actualQuantity) from zt_productionplandetails p \n" +
            "left join zt_salesorderdetails_productionplandetails sp.p_id = p.id\n" +
            "where sp.s_id=:salesOrderDetailsId",nativeQuery=true)
    float orderQuantity(long salesOrderDetailsId);


    @Query(value = "select * from zt_productionplandetails ppd \n" +
            "LEFT JOIN zt_salesorderdetails_productionplandetails sppd on sppd.p_id = ppd.id\n" +
            "LEFT JOIN zt_salesorderdetails sd on sd.id = sppd.s_id\n" +
            "where ppd.enabled =true and sd.id=:orderDetailsId",nativeQuery = true)
    ProductionPlanDetails findByOrderDetails(@Param("orderDetailsId")long orderDetailsId);
     @Modifying
    @Query(value = "update zt_productionplandetails z set z.checkStatus=2,z.updateDate=NOW() where z.id=?1",nativeQuery = true)
    int checkState(long id);

    /*
    根据生产管理详情id查询对应的生产计划
     */
    @Query(value = "select ppd.* from zt_productionplandetails ppd \n" +
            "LEFT JOIN zt_productmanage pm on ppd.id = pm.planDetails_id\n" +
            "LEFT JOIN zt_productmanagedetails pmd on pm.id = pmd.productManage_id\n" +
            "where ppd.enabled = true and pmd.id =:mangeDetailsId",nativeQuery = true)
    List<ProductionPlanDetails> findByMangeDetails(@Param("mangeDetailsId") long mangeDetailsId);
    @Modifying
    @Query(value = "update zt_productionplandetails p set p.manageStatus=:state,p.updateDate=NOW() where p.id in(:planIds)",nativeQuery = true)
    Integer changeContactState(@Param("planIds") List<Long> planIds, @Param("state")int state);

    @Modifying
    @Query(value = "update zt_productionplandetails s SET  s.expectLevel=:expectLevel , s.resendStr=:sendStr,s.resendNo=:planNumber,s.resendId=:resendId,s.sendDate=NOW() where s.id =:productDetailsId ", nativeQuery = true)
    int changeSend(@Param("expectLevel") Integer expectLevel, @Param("productDetailsId")long productDetailsId, @Param("sendStr")String sendStr, @Param("planNumber")float planNumber, @Param("resendId")long resendId);

    /*
     * 模糊条件查询查合同状态为1和3的
     */
    @Query( value = "SELECT ppd.* FROM  zt_productionplandetails AS ppd \n" +
            "       \tLEFT JOIN zt_salesplan  sp ON sp.id=ppd.salesPlan_id \n" +
            "         \tLEFT JOIN zt_employee  e ON e.id=ppd.employeeId\n" +
            "          \tWHERE ppd.enabled = TRUE \n" +
            "          \tAND ppd.`status`=:status \n" +
            "\tAND IF(:clientName !='', sp.clientName LIKE %:clientName%, 1 = 1 )\n" +
            "           \tAND IF( :productName !='', sp.productName LIKE %:productName%, 1 = 1 )\n" +
            "            \tAND IF( :empName != '', e.name LIKE %:empName%, 1 = 1 )\n" +
            "            \tAND IF( :startDate != '', DATE_FORMAT(ppd.createDate, '%Y-%m-%d %k:%i:%s' ) >=:startDate, 1 = 1 )\n" +
            "            \tAND IF( :endDate != '', DATE_FORMAT(ppd.createDate, '%Y-%m-%d %k:%i:%s' ) <=:endDate, 1 = 1 )" +
            "AND ppd.contractStatus=1 or ppd.contractStatus=3 order by  ppd.id desc"
            ,nativeQuery = true)
    List<ProductionPlanDetails> findByCon(@Param("productName") String productName,
                                          @Param("empName") String empName,
                                          @Param("endDate")String endDate,
                                          @Param("startDate")String startDate,
                                          @Param("clientName")String clientName,
                                          @Param("status")Integer status);
    /*
     * 模糊条件查询查合同状态为1和3的
     */
    @Query( value = "SELECT ppd.* FROM  zt_productionplandetails AS ppd \n" +
            "       \tLEFT JOIN zt_salesplan  sp ON sp.id=ppd.salesPlan_id \n" +
            "         \tLEFT JOIN zt_employee  e ON e.id=ppd.employeeId\n" +
            "          \tWHERE ppd.enabled = TRUE \n" +
            "          \tAND ppd.`status`=:status \n" +
            "\tAND IF(:clientName !='', sp.clientName LIKE %:clientName%, 1 = 1 )\n" +
            "           \tAND IF( :productName !='', sp.productName LIKE %:productName%, 1 = 1 )\n" +
            "            \tAND IF( :empName != '', e.name LIKE %:empName%, 1 = 1 )\n" +
            "            \tAND IF( :startDate != '', DATE_FORMAT(ppd.createDate, '%Y-%m-%d %k:%i:%s' ) >=:startDate, 1 = 1 )\n" +
            "            \tAND IF( :endDate != '', DATE_FORMAT(ppd.createDate, '%Y-%m-%d %k:%i:%s' ) <=:endDate, 1 = 1 )" +
            "AND ppd.contractStatus=1 or ppd.contractStatus=3 order by  ppd.id desc"
            ,nativeQuery = true)
    List<ProductionPlanDetails> findAllForContract(@Param("productName") String productName,
                                          @Param("empName") String empName,
                                          @Param("endDate")String endDate,
                                          @Param("startDate")String startDate,
                                          @Param("clientName")String clientName,
                                          @Param("status")Integer status);
}
