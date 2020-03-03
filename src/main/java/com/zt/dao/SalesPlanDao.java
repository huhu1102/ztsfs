package com.zt.dao;

import com.zt.po.SalesPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Set;

@RepositoryRestResource(collectionResourceRel = "sales", path = "sales")
public interface SalesPlanDao extends JpaRepository<SalesPlan, Long> {

    @Query("select s from SalesPlan s where s.enabled=true and s.id=?1")
    SalesPlan findById(long id);

    /*
    查询
     */
    @Query(value = "SELECT\n" +
            "\ts.id AS sid,\n" +
            "\ts.`status` AS sStatus,\n" +
            "\tc.`name` AS companyName,\n" +
            "\tp.producteName AS pName,\n" +
            "\te.`name` AS empName,\n" +
            "\tsp.`name` AS spName,\n" +
            "\ts.createDate AS screateDate,\n" +
            "\ts.quantity AS expectNumber,\n" +
            "\ts.endQuantity AS completeNum,\n" +
            "\ts.nextQuantity AS sendproductNum,\n" +
            "\ts.planString AS recordstr,\n" +
            "\ts.notes AS snote,\n" +
            "\tc2.`name` AS colorName,\n" +
            "IF (ISNULL(c.parentName) || LENGTH(trim(c.parentName)) < 1,'',c.parentName" +
            ") AS cpName," +
            "s.pcCode AS parentIds, " +
            "p.id AS productId, " +
            "c2.id AS colorId, " +
            "s.specsId AS specsId, " +
            "s.expectLevel AS expectLevel, " +
            "s.shippingQuantity AS shippingQuantity, " +
            "s.resendNo AS resendNo, " +
            "s.serialNumber AS serialNumber, " +
            "s.resendStr AS resendStr, " +
            "s.editerId AS editerId, " +
            "s.shippingStr AS shippingStr, " +
            "s.resendId AS resendId, " +
            "s.produtRecordStr AS produtRecordStr, " +
            "s.upsDate AS upsDate, " +
            "s.sendDate AS sendDate " +
            "FROM\n" +
            "\tzt_salesplan s\n" +
            "LEFT JOIN zt_employee e ON e.id = s.editerId\n" +
            "LEFT JOIN zt_client c ON c.id = s.clientId\n" +
            "LEFT JOIN zt_color c2 ON c2.id = s.colorId\n" +
            "LEFT JOIN zt_products p ON p.id = s.productId\n" +
            "LEFT JOIN zt_specification sp ON sp.id = s.specsId\n" +
            "WHERE\n" +
            "\ts.enabled = TRUE " +
            "and if(:clientName !='',s.clientName like %:clientName%,1=1) " +
            "and if(:productName !='',s.productName like %:productName%,1=1) " +
            "and if(:empName !='',e.name like %:empName% ,1=1) \n" +
            "and if(:start !='',DATE_FORMAT(s.createDate,'%Y-%m-%d %k:%i:%s')>=:start,1=1) and if(:end !='',DATE_FORMAT(s.createDate,'%Y-%m-%d %k:%i:%s')<=:end,1=1) and if(:status !='',s.`status`=:status,1=1)" +
            "order by s.upsDate desc",
            countQuery = "SELECT COUNT(*) " +
                    "FROM\n" +
                    "\tzt_salesplan s\n" +
                    "LEFT JOIN zt_employee e ON e.id = s.editerId\n" +
                    "LEFT JOIN zt_client c ON c.id = s.clientId\n" +
                    "LEFT JOIN zt_color c2 ON c2.id = s.colorId\n" +
                    "LEFT JOIN zt_products p ON p.id = s.productId\n" +
                    "LEFT JOIN zt_specification sp ON sp.id = s.specsId\n" +
                    "WHERE\n" +
                    "\ts.enabled = TRUE " +
                    "and if(:clientName !='',s.clientName like %:clientName%,1=1) " +
                    "and if(:productName !='',s.productName like %:productName%,1=1) " +
                    "and if(:empName !='',e.name like %:empName% ,1=1) \n" +
                    "and if(:start !='',DATE_FORMAT(s.createDate,'%Y-%m-%d %k:%i:%s')>=:start,1=1) and if(:end !='',DATE_FORMAT(s.createDate,'%Y-%m-%d %k:%i:%s')<=:end,1=1) and if(:status !='',s.`status`=:status,1=1)" +
                    "order by s.upsDate desc",
            nativeQuery = true)
    Page<Object> find(@Param("clientName") String clientName,
                      @Param("productName") String productName,
                      @Param("empName") String empName,
                      @Param("start") String start,
                      @Param("end") String end,
                      @Param("status") Integer status,
                      Pageable pageable);

    /*
   根据生产计划下发状态查询销售计划
    */
    @Query(value = "SELECT\n" +
            "\ts.id AS sid,\n" +
            "\ts.`status` AS sStatus,\n" +
            "\tc.`name` AS companyName,\n" +
            "\tp.producteName AS pName,\n" +
            "\te.`name` AS empName,\n" +
            "\tsp.`name` AS spName,\n" +
            "\ts.createDate AS screateDate,\n" +
            "\ts.quantity AS expectNumber,\n" +
            "\ts.endQuantity AS completeNum,\n" +
            "\ts.nextQuantity AS sendproductNum,\n" +
            "\ts.planString AS recordstr,\n" +
            "\ts.notes AS snote,\n" +
            "\tc2.`name` AS colorName,\n" +
            "IF (ISNULL(c.parentName) || LENGTH(trim(c.parentName)) < 1,'',c.parentName" +
            ") AS cpName," +
            "s.pcCode AS parentIds, " +
            "p.id AS productId, " +
            "c2.id AS colorId, " +
            "s.specsId AS specsId, " +
            "s.expectLevel AS expectLevel, " +
            "s.shippingQuantity AS shippingQuantity, " +
            "s.resendNo AS resendNo, " +
            "s.serialNumber AS serialNumber, " +
            "s.resendStr AS resendStr, " +
            "s.editerId AS editerId, " +
            "s.shippingStr AS shippingStr, " +

            "s.resendId AS resendId, " +
            "s.produtRecordStr AS produtRecordStr, " +
            "s.upsDate AS upsDate , " +
            "s.sendDate AS sendDate " +
            "FROM\n" +
            "\tzt_salesplan s\n" +
            "INNER JOIN zt_employee e ON e.id = s.editerId\n" +
            "INNER JOIN zt_client c ON c.id = s.clientId\n" +
            "INNER JOIN zt_color c2 ON c2.id = s.colorId\n" +
            "INNER JOIN zt_products p ON p.id = s.productId\n" +
            "INNER JOIN zt_specification sp ON sp.id = s.specsId\n" +
            "WHERE\n" +
            "\ts.enabled = TRUE " +
            "and if(:clientName !='',s.clientName like %:clientName%,1=1) " +
            "and if(:productName !='',s.productName like %:productName%,1=1) " +
            "and if(:empName !='',e.name like %:empName% ,1=1) \n" +
            "and if(:start !='',DATE_FORMAT(s.createDate,'%Y-%m-%d %k:%i:%s')>=:start,1=1) and if(:end !='',DATE_FORMAT(s.createDate,'%Y-%m-%d %k:%i:%s')<=:end,1=1) and if(:status !='',s.status=:status,1=1)" +
            "order by s.upsDate desc",
            countQuery = "SELECT COUNT(*) " +
                    "FROM\n" +
                    "\tzt_salesplan s\n" +
                    "INNER JOIN zt_employee e ON e.id = s.editerId\n" +
                    "INNER JOIN zt_client c ON c.id = s.clientId\n" +
                    "INNER JOIN zt_color c2 ON c2.id = s.colorId\n" +
                    "INNER JOIN zt_products p ON p.id = s.productId\n" +
                    "INNER JOIN zt_specification sp ON sp.id = s.specsId\n" +
                    "WHERE\n" +
                    "\ts.enabled = TRUE " +
                    "and if(:clientName !='',s.clientName like %:clientName%,1=1) " +
                    "and if(:productName !='',s.productName like %:productName%,1=1) " +
                    "and if(:empName !='',e.name like %:empName% ,1=1) \n" +
                    "and if(:start !='',DATE_FORMAT(s.createDate,'%Y-%m-%d %k:%i:%s')>=:start,1=1) and if(:end !='',DATE_FORMAT(s.createDate,'%Y-%m-%d %k:%i:%s')<=:end,1=1) " +
                    "and if(:status !='',s.planStatus=:status,1=1) order by s.upsDate desc",
            nativeQuery = true)
    Page<Object> findForPlan(@Param("clientName") String clientName,
                             @Param("productName") String productName,
                             @Param("empName") String empName,
                             @Param("start") String start,
                             @Param("end") String end,
                             @Param("status") Integer status,
                             Pageable pageable);

    /*
     查询下发状态为1和2的销售计划
    */
    @Query(value = "SELECT\n" +
            "\ts.id AS sid,\n" +
            "\ts.`status` AS sStatus,\n" +
            "\tc.`name` AS companyName,\n" +
            "\tp.producteName AS pName,\n" +
            "\te.`name` AS empName,\n" +
            "\tsp.`name` AS spName,\n" +
            "\ts.createDate AS screateDate,\n" +
            "\ts.quantity AS expectNumber,\n" +
            "\ts.endQuantity AS completeNum,\n" +
            "\ts.nextQuantity AS sendproductNum,\n" +
            "\ts.planString AS recordstr,\n" +
            "\ts.notes AS snote,\n" +
            "\tc2.`name` AS colorName,\n" +
            "IF (ISNULL(c.parentName) || LENGTH(trim(c.parentName)) < 1,'',c.parentName" +
            ") AS cpName," +
            "s.pcCode AS parentIds, " +
            "p.id AS productId, " +
            "c2.id AS colorId, " +
            "s.specsId AS specsId, " +
            "s.expectLevel AS expectLevel, " +
            "s.shippingQuantity AS shippingQuantity, " +
            "s.resendNo AS resendNo, " +
            "s.serialNumber AS serialNumber, " +
            "s.resendStr AS resendStr, " +
            "s.editerId AS editerId, " +
            "s.shippingStr AS shippingStr, " +
            "s.resendId AS resendId, " +
            "s.produtRecordStr AS produtRecordStr, " +
            "s.upsDate AS upsDate , " +
            "s.sendDate AS sendDate " +
            "FROM\n" +
            "\tzt_salesplan s\n" +
            "INNER JOIN zt_employee e ON e.id = s.editerId\n" +
            "INNER JOIN zt_client c ON c.id = s.clientId\n" +
            "INNER JOIN zt_color c2 ON c2.id = s.colorId\n" +
            "INNER JOIN zt_products p ON p.id = s.productId\n" +
            "INNER JOIN zt_specification sp ON sp.id = s.specsId\n" +
            "WHERE\n" +
            "\ts.enabled = TRUE " +
            "and if(:clientName !='',s.clientName like %:clientName%,1=1) " +
            "and if(:productName !='',s.productName like %:productName%,1=1) " +
            "and if(:empName !='',e.name like %:empName% ,1=1) \n" +
            "and if(:start !='',DATE_FORMAT(s.createDate,'%Y-%m-%d %k:%i:%s')>=:start,1=1) " +
            "and if(:end !='',DATE_FORMAT(s.createDate,'%Y-%m-%d %k:%i:%s')<=:end,1=1) " +
            "and (s.planStatus = 1 or s.planStatus = 2) order by s.upsDate desc",
            countQuery = "SELECT COUNT(*) " +
                    "FROM\n" +
                    "\tzt_salesplan s\n" +
                    "INNER JOIN zt_employee e ON e.id = s.editerId\n" +
                    "INNER JOIN zt_client c ON c.id = s.clientId\n" +
                    "INNER JOIN zt_color c2 ON c2.id = s.colorId\n" +
                    "INNER JOIN zt_products p ON p.id = s.productId\n" +
                    "INNER JOIN zt_specification sp ON sp.id = s.specsId\n" +
                    "WHERE\n" +
                    "\ts.enabled = TRUE " +
                    "and if(:clientName !='',s.clientName like %:clientName%,1=1) " +
                    "and if(:productName !='',s.productName like %:productName%,1=1) " +
                    "and if(:empName !='',e.name like %:empName% ,1=1) \n" +
                    "and if(:start !='',DATE_FORMAT(s.createDate,'%Y-%m-%d %k:%i:%s')>=:start,1=1) and if(:end !='',DATE_FORMAT(s.createDate,'%Y-%m-%d %k:%i:%s')<=:end,1=1) " +
                    "and (s.planStatus = 1 or s.planStatus = 2) order by s.upsDate desc",
            nativeQuery = true)
    Page<Object> findForNoPlan(@Param("clientName") String clientName,
                               @Param("productName") String productName,
                               @Param("empName") String empName,
                               @Param("start") String start,
                               @Param("end") String end,
                               Pageable pageable);

    /*
    查询
     */
    @Query(value = "select s.* from zt_salesplan s  left join zt_employee e on e.id=s.editerId where   s.enabled=true  and if(:clientName !='',s.clientName like %:clientName%,1=1) and if(:productName !='',s.productName like %:productName%,1=1) and if(:empName !='',e.name like %:empName% ,1=1) \n" +
            "and if(:start !='',DATE_FORMAT(s.createDate,'%Y-%m-%d %k:%i:%s')>=:start,1=1) and if(:end !='',DATE_FORMAT(s.createDate,'%Y-%m-%d %k:%i:%s')<=:end,1=1) and if(:status !='',s.shippingStatus=1 or s.shippingStatus=2,1=1) order by s.upsDate desc",
            countQuery = "select count(*) from zt_salesplan s  left join zt_employee e on e.id=s.editerId where   s.enabled=true  and if(:clientName !='',s.clientName like %:clientName%,1=1) and if(:productName !='',s.productName like %:productName%,1=1) and if(:empName !='',e.name like %:empName% ,1=1) \n" +
                    "and if(:start !='',DATE_FORMAT(s.createDate,'%Y-%m-%d %k:%i:%s')>=:start,1=1) and if(:end !='',DATE_FORMAT(s.createDate,'%Y-%m-%d %k:%i:%s')<=:end,1=1) and if(:status !='',s.shippingStatus=1 or s.shippingStatus=2,1=1) order by s.upsDate desc",
            nativeQuery = true)
    Page<SalesPlan> findForShipping(@Param("clientName") String clientName,
                                    @Param("productName") String productName,
                                    @Param("empName") String empName,
                                    @Param("start") String start,
                                    @Param("end") String end,
                                    @Param("status") Integer status,
                                    Pageable pageable);

    /*
    修改销售计划状态
    */
    @Modifying
    @Query("update SalesPlan p set p.status=?1 where p.id =?2")
    int updateStatus(Integer status, long id);

    /*
    修改生产进度的状态
     */
    @Modifying
    @Query("update SalesPlan p set p.manageStatus=:status,p.status=:state, p.upsDate=NOW() where p.id =:id")
    int updateManageStatus(@Param("status") Integer status,@Param("state") Integer state, @Param("id") long id);

    /*
    修改下发记录
     */
    @Modifying
    @Query("update SalesPlan p set p.planString=:str,p.status=:myStatus, p.planStatus=:state,p.nextQuantity=p.nextQuantity+:quantity where p.id=:salesPlanId")
    int updatePlanString(@Param("str") String str,@Param("myStatus")Integer myStatus,@Param("state")int state,  @Param("salesPlanId")Long salesPlanId,@Param("quantity")float quantity);

    /*
    拼接产品详情
     */
    @Query(value = "select concat_ws(';',s.clientName,s.productName,(SELECT c.name from zt_color c where c.id=s.colorId),(select p.name FROM zt_specification p where p.id=s.specsId),(select t.planNumber from zt_shippingRequestDetails t where t.id=(select d.shippingRequestDetailsId FROM zt_shippingbill d where d.id=?1))) from zt_salesPlan s where s.id=(select h.salesPlanId from zt_shippingRequest h where h.id=(select i.shippingRequestId from zt_shippingRequestDetails i where i.id=(select b.shippingRequestDetailsId FROM zt_shippingbill b where b.id=?1)))", nativeQuery = true)
    String find(long id);

    /*
     * 查找最大的单号
     */
    @Query("select max(p.serialNumber) from SalesPlan p where p.enabled=true")
    String findMaxPlanNo();

    /*
    修改销售计划中状态
     */
    @Modifying
    @Query("update SalesPlan s set s.planStatus=4,s.manageStatus=4,s.shippingStatus=4 where s.id=?1")
    int annul(long id);

    /*
    查询客户和产品的关系表
     */
    @Query(value = "select count(*) from zt_client_product as  c where c.c_id=?1 and c.p_id=?2", nativeQuery = true)
    int selectClientPro(long cid, long pid);

    /*
    查询该客户下的所有产品名称
     */
    @Query(value = "select p.producteName from zt_products as p left join zt_client_product as cp on p.id = cp.p_id where cp.c_id=?1 ", nativeQuery = true)
    List<String> findProducts(long cid);

    /*
    客户和产品的关系表添加新数据
     */
    @Modifying
    @Query(value = "INSERT INTO zt_client_product (c_id,p_id) VALUES (:cid,:pid)", nativeQuery = true)
    int addClientPro(@Param("cid") long cid, @Param("pid") long pid);


    /*
    根据客户查询近三次的销售计划
     */
    @Query(value = "select s from SalesPlan s where s.clientId=?1 order by s.id desc limit 3", nativeQuery = true)
    Page<SalesPlan> findByClient(long clientId, Pageable pageable);


    /*
    查询销售计划状态的数量
     */
    @Query("select count(s.status) from SalesPlan s where s.enabled=true group by s.status")
    Set<SalesPlan> findStatus();

    /**
     * 批量修改方法；
     *
     * @param resultList
     * @param important
     * @return
     */
    @Modifying
    @Query(value = "update zt_salesplan s SET  s.expectLevel=:important where s.id in(:resultList) ", nativeQuery = true)
    int changeLever(@Param("resultList") List<Long> resultList, @Param("important") int important);

    @Modifying
    @Query(value = "update zt_salesplan s SET  s.expectLevel=:expectLevel , s.resendStr=:sendStr,s.resendNo=:planNumber,s.resendId=:resendId,s.sendDate=NOW() where s.id =:salesPlanId ", nativeQuery = true)
    int changeSend(@Param("expectLevel") Integer expectLevel, @Param("salesPlanId") long salesPlanId, @Param("sendStr") String sendStr, @Param("planNumber") float planNumber, @Param("resendId") long resendId);

    /*
    查询销售计划中的未下发生产计划的个数
     */
    @Query(value = "select count(*) from zt_salesplan sa where sa.planStatus=1", nativeQuery = true)
    Long findByPlanStatus();

    //根据生产管理id查询
    @Query(value = "SELECT * from zt_salesplan AS s\n" +
            "LEFT JOIN zt_productionplandetails ppd ON ppd.salesPlanId = s.id\n" +
            "LEFT JOIN zt_productmanage pm ON pm.planDetails_id = ppd.id \n" +
            "WHERE\n" +
            "\ts.enabled = true \n" +
            "\tand \n" +
            "\tpm.id = :managerId order by s.upsDate desc", nativeQuery = true)
    SalesPlan findByManage(@Param("managerId") long managerId);

    /*
    修改生产完成的数量和完成记录
     */
    @Modifying
    @Query(value = "update zt_salesPlan s set s.endQuantity=:endQuantity,s.produtRecordStr=:produtRecordStr where s.id=:salesPlanId",nativeQuery = true)
    int changeEndQuantity(@Param("endQuantity")float endQuantity,@Param("produtRecordStr")String produtRecordStr,@Param("salesPlanId")long salesPlanId);

    @Modifying
    @Query(value = "UPDATE  `zt_salesplan` s  set s.endQuantity=s.endQuantity+:endQuantity,s.produtRecordStr=:str WHERE s.id=:saleId",nativeQuery = true)
    int updateProductStr(@Param("str") String str, @Param("endQuantity") float endQuantity, @Param("saleId") long saleId);
}
