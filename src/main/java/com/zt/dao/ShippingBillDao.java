package com.zt.dao;

import com.zt.po.ShippingBill;
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
 * @date 2019年6月11日 
 */
@RepositoryRestResource(collectionResourceRel = "shippingBill", path="shippingBill")
public interface ShippingBillDao extends JpaRepository<ShippingBill, Long>{
	/*
	 * 自定义分页模糊条件查询
	 */
    @Query("select u from ShippingBill u where u.enabled=true  ")
    Page<ShippingBill> findSearch(  String query, Pageable pageable);

    @Query("select u from ShippingBill u where u.enabled=true and u.id=?1")
    ShippingBill findById(long id);

    /*
    查询有统一销售计划id的发货单
     */
    @Query(value = "SELECT * FROM zt_shippingbill z WHERE z.endble=true and  z.shippingRequestDetailsId   in(SELECT d.id from  zt_shippingrequestdetails  d WHERE  d.shippingRequest_id=(SELECT s.shippingRequest_id FROM   zt_shippingrequestdetails  s WHERE s.id=?1))",nativeQuery = true)
    Page<ShippingBill> findBySales(long shippingRequestDetailsId,Pageable pageable);

    /*
    根据生产管理Id查询发货记录
     */
    @Query(value = "select s.* from zt_shippingbill s where s.shippingRequestDetailsId=?1",nativeQuery = true)
    Page<ShippingBill> findForManage(long productManageId,Pageable pageable);

    @Query(value = "select * from zt_shippingbill sb LEFT JOIN zt_productmanage pm on pm.id=sb.shippingRequestDetailsId LEFT JOIN zt_productionplandetails ppd on ppd.id = pm.planDetails_id where ppd.id=?1 and sb.enabled=true",nativeQuery = true)
    Page<ShippingBill> findForPlan(long productPlanId,Pageable pageable);

    //由生产管理详情Id 更改销售计划中发货数量
    @Modifying
    @Query(value = "update zt_salesplan s LEFT JOIN zt_productionplandetails  ppd on ppd.salesPlanId=s.id LEFT JOIN zt_productmanage pm on pm.planDetails_id = ppd.id SET s.shippingQuantity = s.shippingQuantity + :shippingQuantity,s.shippingStr=:shippingStr where pm.id=:managerId",nativeQuery = true)
    int changeSale(@Param("shippingQuantity") Float shippingQuantity,@Param("shippingStr")String shippingStr, @Param("managerId") long managerId);

    @Modifying
    @Query(value = "UPDATE zt_productionplandetails ppd LEFT JOIN zt_productmanage pm ON pm.planDetails_id = ppd.id SET ppd.sendQuantity = ppd.sendQuantity + :sendQuantity,ppd.shippingStr =:shippingStr WHERE pm.id =:managerId",nativeQuery = true)
    int changeProduction(@Param("sendQuantity") Float sendQuantity,@Param("shippingStr")String shippingStr, @Param("managerId") long managerId);

    @Modifying
    @Query(value = "update zt_salesplan s LEFT JOIN zt_productionplandetails  ppd on ppd.salesPlanId=s.id LEFT JOIN zt_productmanage pm on pm.planDetails_id = ppd.id SET s.shippingStr =:shippingStr where pm.id=:managerId",nativeQuery = true)
    int changeShipping(String shippingStr, long managerId);

    //修改确认发货下的状态
    @Modifying
    @Query(value = "update zt_shippingbill s set s.shipingStatus =:shipingStatus where s.id =:id",nativeQuery = true)
    int changeStatus(Integer shipingStatus, long id);
    @Query("select u from ShippingBill u where u.enabled=true and u.shippingRequestDetailsId=?1 order by u.createDate ASC")
    List<ShippingBill> findByManagerId(long managerId);
    @Modifying
    @Query(value = " INSERT INTO `zt_productmanager_shippingbill` (`bill_id`, `manage_id`) VALUES (?1, ?2)",nativeQuery = true)
    int changeMidTable(long id, long shippingRequestDetailsId);
}
