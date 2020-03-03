package com.zt.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.zt.po.ProductManage;

import java.util.Set;

/**
 * @author wl
 * @date 2019年4月26日
 */
@RepositoryRestResource(collectionResourceRel = "productmanage", path = "productmanage")
public interface ProductManageDao extends JpaRepository<ProductManage, Long> {
    /*
     * 自定义分页模糊条件查询
     */
    @Query(value = "SELECT DISTINCT pm.*\n" +
            "       FROM\n" +
            " zt_productmanage pm \n" +
            " LEFT JOIN zt_productionplandetails detail on detail.id=pm.planDetails_id\n" +
            "      LEFT JOIN zt_salesplan sp ON sp.id = detail.salesPlanId\n" +
            "      LEFT JOIN zt_employee e on e.id = pm.managerId\n" +
            "      WHERE pm.enabled = true\n" +
            "      AND IF(:clientName !='', sp.clientName LIKE %:clientName%, 1 = 1 ) \n" +
            "      AND IF( :productName !='', sp.productName LIKE %:productName%, 1 = 1 ) \n" +
            "      AND IF( :empName != '', e.name LIKE %:empName%, 1 = 1 )\n" +
            "      AND IF( :start != '', DATE_FORMAT(pm.createDate, '%Y-%m-%d %k:%i:%s' ) >=:start, 1 = 1 )\n" +
            "      AND IF( :end != '', DATE_FORMAT(pm.createDate, '%Y-%m-%d %k:%i:%s' ) <=:end, 1 = 1 )\n" +
            "      AND IF( :status != '', pm.mangeStatus =:status, 1 = 1 ) order by pm.id desc",
            countQuery = "SELECT DISTINCT count(*) " +
                    "       FROM\n" +
                    " zt_productmanage pm \n" +
                    " LEFT JOIN zt_productionplandetails detail on detail.id=pm.planDetails_id\n" +
                    "      LEFT JOIN zt_salesplan sp ON sp.id = detail.salesPlanId\n" +
                    "      LEFT JOIN zt_employee e on e.id = pm.managerId\n" +
                    "      WHERE pm.enabled = true\n" +
                    "      AND IF(:clientName !='', sp.clientName LIKE %:clientName%, 1 = 1 ) \n" +
                    "      AND IF( :productName !='', sp.productName LIKE %:productName%, 1 = 1 ) \n" +
                    "      AND IF( :empName != '', e.name LIKE %:empName%, 1 = 1 )\n" +
                    "      AND IF( :start != '', DATE_FORMAT(pm.createDate, '%Y-%m-%d %k:%i:%s' ) >=:start, 1 = 1 )\n" +
                    "      AND IF( :end != '', DATE_FORMAT(pm.createDate, '%Y-%m-%d %k:%i:%s' ) <=:end, 1 = 1 )\n" +
                    "      AND IF( :status != '', pm.mangeStatus =:status, 1 = 1 ) order by pm.id desc",
            nativeQuery = true)
    Page<ProductManage> findSearch(@Param("clientName") String clientName,
                                   @Param("productName") String productName,
                                   @Param("empName") String empName,
                                   @Param("start") String start,
                                   @Param("end") String end,
                                   @Param("status") Integer status,
                                   Pageable pageable);

    /*
     * 编号
     */
    @Query("select max(p.id) from ProductManage p where YEAR(create_date)=YEAR(NOW())")
    Long findCurrentNo();

    @Query("select p from ProductManage p where p.enabled=true and p.id=?1")
    ProductManage findById(long id);

    /*
    根据id修改备注
     */
    @Modifying
    @Query("update ProductManage p set p.note=?1 where p.id=?2 and p.enabled=true")
    Integer updateNote(String note, long id);

    /*
    根据销售计划修改状态
     */
    @Modifying
    @Query(value = "UPDATE zt_productmanage p LEFT JOIN zt_productionplandetails d ON p.planDetails_id = d.id SET p.mangeStatus = ?1 WHERE d.salesPlan_id = ?2", nativeQuery = true)
    int updateStatus(Integer status, long salesPlanId);

    /*
    根据生产管理Id修改状态
     */
    @Modifying
    @Query("update ProductManage p set p.mangeStatus=?1 where p.id=?2")
    int upStatusId(Integer status, long id);

    @Query("select p from ProductManage p where p.planDetails.salesPlan.id=?1")
    ProductManage findBySalesPlanId(long id);

    /*
     * 查找最大的单号
     */
    @Query("select max(p.number) from ProductManage p where p.enabled=true")
    String findMaxPlanNo();

    /*
    查询状态数量
     */
    @Query("select count(p.mangeStatus) from ProductManage p where p.enabled=true group by p.mangeStatus")
    Set<ProductManage> findStatus();

    /**
     * 查询state==1  或 ==2的
     *
     * @param clientName
     * @param productName
     * @param empName
     * @param start
     * @param end
     * @param status
     * @param pageable
     * @return
     */
    @Query(value = "SELECT DISTINCT pm.*\n" +
            "       FROM\n" +
            "      zt_productmanage pm \n" +
            "      LEFT JOIN zt_productionplandetails detail on detail.id=pm.planDetails_id\n" +
            "      LEFT JOIN zt_salesplan sp ON sp.id = detail.salesPlanId\n" +
            "      LEFT JOIN zt_employee e on e.id = pm.managerId\n" +
            "      WHERE pm.enabled = true\n" +
            "      AND IF(:clientName !='', sp.clientName LIKE %:clientName%, 1 = 1 ) \n" +
            "      AND IF( :productName !='', sp.productName LIKE %:productName%, 1 = 1 ) \n" +
            "      AND IF( :empName != '', e.name LIKE %:empName%, 1 = 1 )\n" +
            "      AND IF( :start != '', DATE_FORMAT(pm.createDate, '%Y-%m-%d %k:%i:%s' ) >=:start, 1 = 1 )\n" +
            "      AND IF( :end != '', DATE_FORMAT(pm.createDate, '%Y-%m-%d %k:%i:%s' ) <=:end, 1 = 1 )\n" +
            "      AND IF( :status != '', (pm.mangeStatus =1 or pm.mangeStatus =2) , 1 = 1 ) order by pm.id desc",
            countQuery = "SELECT DISTINCT  count(*)\n" +
                    "       FROM\n" +
                    "      zt_productmanage pm \n" +
                    "      LEFT JOIN zt_productionplandetails detail on detail.id=pm.planDetails_id\n" +
                    "      LEFT JOIN zt_salesplan sp ON sp.id = detail.salesPlanId\n" +
                    "      LEFT JOIN zt_employee e on e.id = pm.managerId\n" +
                    "      WHERE pm.enabled = true\n" +
                    "      AND IF(:clientName !='', sp.clientName LIKE %:clientName%, 1 = 1 ) \n" +
                    "      AND IF( :productName !='', sp.productName LIKE %:productName%, 1 = 1 ) \n" +
                    "      AND IF( :empName != '', e.name LIKE %:empName%, 1 = 1 )\n" +
                    "      AND IF( :start != '', DATE_FORMAT(pm.createDate, '%Y-%m-%d %k:%i:%s' ) >=:start, 1 = 1 )\n" +
                    "      AND IF( :end != '', DATE_FORMAT(pm.createDate, '%Y-%m-%d %k:%i:%s' ) <=:end, 1 = 1 )\n" +
                    "      AND IF( :status != '', (pm.mangeStatus =1 or pm.mangeStatus =2) , 1 = 1 ) order by pm.id desc",
            nativeQuery = true)
    Page<ProductManage> findstateSearch(@Param("clientName") String clientName,
                                        @Param("productName") String productName,
                                        @Param("empName") String empName,
                                        @Param("start") String start,
                                        @Param("end") String end,
                                        @Param("status") Integer status,
                                        Pageable pageable);

    @Query(value = "SELECT DISTINCT p.* from  zt_productmanage  p   INNER JOIN  zt_productmanager_shippingbill ps  on  p.id= ps.manage_id " +
            "LEFT  JOIN zt_productionplandetails m ON m.id =p.planDetails_id " +
            " LEFT JOIN  zt_salesplan  s  on  s.id= m.salesPlan_id " +
            "LEFT JOIN  zt_employee e on s.editer_id=e.id" +
            "      WHERE p.enabled = true\n" +
            "      AND IF(:clientName !='', s.clientName LIKE %:clientName%, 1 = 1 ) \n" +
            "      AND IF( :productName !='', s.productName LIKE %:productName%, 1 = 1 ) \n" +
            "      AND IF( :empName != '', e.name LIKE %:empName%, 1 = 1 )\n" +
            "      AND IF( :start != '', DATE_FORMAT(p.createDate, '%Y-%m-%d %k:%i:%s' ) >=:start, 1 = 1 )\n" +
            "      AND IF( :end != '', DATE_FORMAT(p.createDate, '%Y-%m-%d %k:%i:%s' ) <=:end, 1 = 1 )\n" +
            "      AND IF( :status != '', p.mangeStatus =:status, 1 = 1 ) order by p.createDate desc",
            countQuery = "SELECT DISTINCT count(*) from  zt_productmanage  p   INNER JOIN  zt_productmanager_shippingbill ps  on  p.id= ps.manage_id " +
                    "LEFT  JOIN zt_productionplandetails m ON m.id =p.planDetails_id " +
                    " LEFT JOIN  zt_salesplan  s  on  s.id= m.salesPlan_id " +
                    "LEFT JOIN  zt_employee e on s.editer_id=e.id" +
                    "      WHERE p.enabled = true\n" +
                    "      AND IF(:clientName !='', s.clientName LIKE %:clientName%, 1 = 1 ) \n" +
                    "      AND IF( :productName !='', s.productName LIKE %:productName%, 1 = 1 ) \n" +
                    "      AND IF( :empName != '', e.name LIKE %:empName%, 1 = 1 )\n" +
                    "      AND IF( :start != '', DATE_FORMAT(p.createDate, '%Y-%m-%d %k:%i:%s' ) >=:start, 1 = 1 )\n" +
                    "      AND IF( :end != '', DATE_FORMAT(p.createDate, '%Y-%m-%d %k:%i:%s' ) <=:end, 1 = 1 )\n" +
                    "      AND IF( :status != '', p.mangeStatus =:status, 1 = 1 ) order by p.createDate desc",
            nativeQuery = true)
    Page<ProductManage> findForSend(@Param("clientName") String clientName,
                                    @Param("productName") String productName,
                                    @Param("empName") String empName,
                                    @Param("start") String start,
                                    @Param("end") String end,
                                    @Param("status") Integer status,
                                    Pageable pageable);
    @Modifying
    @Query(value = "update  zt_productmanage pt  right join zt_productionplandetails  p on p.id=pt.planDetails_id  set p.expectLevel=4, where pt.id=?1",nativeQuery = true)
    int revoke(long id);

    @Query(value = "SELECT p.id FROM `zt_productmanage`  p  WHERE  p.planDetails_id=?1",nativeQuery = true)
    Long findByManagerId(long manageId);
}
