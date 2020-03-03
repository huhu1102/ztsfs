package com.zt.dao;

import com.zt.po.ProductionPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Set;

/**
 * @author wl
 * @date 2019年4月24日 
 * 生产计划
 */
@RepositoryRestResource(collectionResourceRel = "productionplan", path="productionplan")
public interface ProductionPlanDao extends JpaRepository<ProductionPlan, Long>{
	/*
	 * 自定义分页模糊条件查询
	 */
//    @Query("select u from MarketingPlan u where u.enabled=true and u.empName like %?1%")
//    Page<MarketingPlan> findSearch(String query, Pageable pageable);
    @Query("from ProductionPlan u where u.enabled=true and u.id=?1")
    ProductionPlan findById(long id);
	/**
	 * 查找最大的单号
	 */
    @Query("select max(p.planNo) from ProductionPlan p")
    String findMaxPlanNo();

    /*
    查询所有
     */
    @Query(value = "SELECT pd.* " +
            "FROM\n" +
            "\tzt_productionplanserialnumber num\n" +
            "\tLEFT JOIN zt_productionplandetails detail ON detail.id = num.produtionPlan_id\n" +
            "\tLEFT JOIN zt_productionplan  pd ON pd.id = detail.productionPlan_id\n" +
            "\tLEFT JOIN zt_salesplan sp ON sp.id = detail.salesPlan_id\n" +
            "\tINNER JOIN zt_client c ON c.id = sp.client_id\n" +
            "\tINNER JOIN zt_products p ON p.id = sp.productId \n" +
            "\tINNER JOIN zt_employee e ON e.id = detail.employeeId\n" +
            "WHERE detail.enabled = TRUE \n" +
            "AND\n" +
            "IF\n" +
            "\t(:statused IS NOT NULL, detail.`status` = :statused, 1 = 1 ) \n" +
            "AND\n" +
            "IF\n" +
            "\t( ISNULL(:clientName ) || LENGTH( trim(:clientName ) ), c.NAME = :clientName, 1 = 1 ) \n" +
            "AND\n" +
            "IF\n" +
            "\t(\n" +
            "\tISNULL (:productName) || LENGTH( trim(:productName ) ) < 1,\n" +
            "\t1 = 1,\n" +
            "\tp.producteName LIKE %:productName% \n" +
            "\t) \n" +
            "AND\n" +
            "IF\n" +
            "\t(\n" +
            "\tISNULL (:empName ) || LENGTH( trim(:empName ) ) < 1,\n" +
            "\t1 = 1,\n" +
            "\te.`name` LIKE %:empName% \n" +
            "\t) \n" +
            "AND\n" +
            "IF\n" +
            "\t(\n" +
            "\tISNULL (:startDate ) || LENGTH( trim(:startDate ) ) < 1 || ISNULL(:endDate ) || LENGTH( trim(:endDate ) ) < 1,\n" +
            "\t1 = 1,\n" +
            "\tdetail.createDate BETWEEN DATE_FORMAT(:startDate, '%Y-%m-%d %k:%i:%s' ) \n" +
            "\tAND DATE_FORMAT(:endDate, '%Y-%m-%d %k:%i:%s' ) \n" +
            "\t) \n" +
            "GROUP BY\n" +
            "\tdetail.productionPlan_id  ORDER BY pd.createDate DESC",nativeQuery = true)
//    @Query(value = "SELECT DISTINCT pp.*,s.id,s.clientName,s.orderNo,s.orderStr,s.`status` FROM zt_productionplan pp  LEFT JOIN zt_productionplandetails  dt on  dt.productionPlan_id=pp.id  LEFT JOIN zt_salesplan  s on s.id=dt.salesPlan_id WHERE pp.enabled = TRUE and  s.`status`=1",nativeQuery = true)
	Page<ProductionPlan> findSearch(@Param("productName") String productName,
                                    @Param("empName") String empName,
                                    @Param("endDate") String endDate,
                                    @Param("startDate") String startDate,
                                    @Param("statused") Integer statused,
                                    @Param("clientName") String clientName,
                                    Pageable pageable);
//    @Query(value = "SELECT DISTINCT pp.* FROM zt_productionplan pp  LEFT JOIN zt_productionplandetails  dt on  dt.productionPlan_id=pp.id  LEFT JOIN zt_salesplan  s on s.id=dt.salesPlan_id WHERE pp.enabled = TRUE and  s.`status`=:statused ORDER BY  pp.createDate DESC",nativeQuery = true)
//    Page<ProductionPlan> findSearch(String productName, String empName, String endDate, String startDate, @Param("statused") Integer statused, String clientName, Pageable pageable);
    /**
    查询当天是否有单号
     */
    @Query("select m from ProductionPlan m where TO_DAYS(m.createDate) = TO_DAYS(NOW()) and m.enabled=true")
	List<ProductionPlan> currentPlan();

     @Override
     @EntityGraph(value = "ProductionPlan.detaillist")
     List<ProductionPlan>findAll();
    /*
    修改生产计划的备注
     */
    @Modifying
    @Query("update ProductionPlan  p set p.notes=?1 where p.id=?2 and p.enabled=true")
    Integer update(String notes,long id );

    @Query("select count(p.status) from ProductionPlan p where p.enabled=true group by p.status")
    Set<ProductionPlan> findStatus();

}
