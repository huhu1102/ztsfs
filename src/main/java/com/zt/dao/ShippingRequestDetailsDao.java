package com.zt.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.zt.po.ShippingRequestDetails;

import java.util.List;
import java.util.Set;

/**
 * @author wl
 * @date 2019年4月26日 
 */
@RepositoryRestResource(collectionResourceRel = "shippingrequestdetails", path="shippingrequestdetails")
public interface ShippingRequestDetailsDao extends JpaRepository<ShippingRequestDetails, Long>{
	/*
	 * 自定义分页模糊条件查询
	 */
    @Query(value = "SELECT\n" +
            "\t* \n" +
            "FROM\n" +
            "\tzt_shippingrequestdetails sr\n" +
            "\tLEFT Join zt_salesplan s on s.id = sr.salePlanId\n" +
            "\tLEFT Join zt_employee e on e.id = s.editerId\n" +
            "WHERE\n" +
            "\tsr.enabled = 'true'\n" +
            "  and if(:empName != '', e.name LIKE %:empName%,1=1)\n" +
            "  and if(:clientName != '', s.clientName LIKE %:clientName%, 1 = 1)\n" +
            "\tand if(:productName != '', s.productName LIKE %:productName%, 1 = 1)\n" +
            "\t",nativeQuery = true)
    Page<ShippingRequestDetails> findSearch(@Param("empName") String empName,
                                            @Param("clientName") String clientName,
                                            @Param("productName") String productName,
                                            Pageable pageable);
    
    ShippingRequestDetails findById(long id);

    @Modifying
    @Query("update ShippingRequestDetails s set s.deliveryStatus=?1 where s.id=?2")
    int updateStatus(Integer status,long id);

    @Query("select count(s.deliveryStatus) from ShippingRequestDetails s where s.enabled=true group by s.deliveryStatus")
    Set<ShippingRequestDetails> findStatus();

    @Query("select s from ShippingRequestDetails s where s.enabled=true and s.salePlanId=?1 ORDER BY s.id DESC ")
    List<ShippingRequestDetails> findbySalesId(long salesPlanId);

    @Query(value = "select max(s.id) from zt_shippingrequestdetails s where s.enabled=true group by s.salePlanId=:salePlanId",nativeQuery = true)
    ShippingRequestDetails findLately(@Param("salePlanId") long salePlandId);
    @Modifying
    @Query("update ShippingRequestDetails s set s.deliveryStatus=5, s.enabled=false where s.id=?1")
    int revoke(long id);
    @Modifying
    @Query(value = "update   zt_salesplan p   set p.resendNo=0, p.resendId=0,p.expectLevel=4 where p.resendId=?1",nativeQuery = true)
    int revokeSale(long id);

    @Modifying
    @Query(value = "update  zt_productionplandetails p  set p.resendNo = 0, p.resendId = 0,p.expectLevel = 4 WHERE p.resendId =?1",nativeQuery = true)
    int revokePlan(long id);
}
