package com.zt.dao;

import java.util.List;

import com.zt.po.SalesOrder;
import com.zt.po.SalesOrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


/**
 * @author wl
 * @date 2019年5月9日 
 */
@RepositoryRestResource(collectionResourceRel = "salesorderdetails", path="salesorderdetails")
public interface SalesOrderDetailsDao extends JpaRepository<SalesOrderDetails, Long>{

	/**
	 * @return
	 */
    @Query("select u from SalesOrderDetails u where u.enabled=true")
	List<SalesOrderDetails> findAllsteps();

	@Query("select u from SalesOrderDetails u where u.enabled=true and u.id=?1")
	SalesOrderDetails findById(long id);

	@Query(value = "select sd.id from zt_salesorderdetails sd \n" +
			"\t\t\tLEFT JOIN zt_salesorderdetails_productionplandetails sppd on sppd.s_id = sd.id\n" +
			"\t\t\tLEFT JOIN zt_productionplandetails ppd on ppd.id = sppd.p_id\n" +
			"\t\t\tLEFT JOIN zt_salesplan s on s.id = ppd.salesPlanId\n" +
			"\t\t\twhere sd.enabled = true and s.id=:salesId",nativeQuery = true)
	List<SalesOrderDetails> findBySales(@Param("salesId")long salesId);

	//修改订单详情的状态
	@Modifying
	@Query(value = "update zt_salesorderdetails sd set sd.detailsStatus=:detailsStatus where sd.enabled=true and sd.Id=:id",nativeQuery = true)
	int changeStatus(@Param("detailsStatus") Integer detailsStatus,@Param("id")long id);
}
