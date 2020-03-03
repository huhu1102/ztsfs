/**
 * 
 */
package com.zt.dao;

import com.zt.po.MidProgressRecieving;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Set;

/**
 * @author yh
 * @date 2019年5月9日
 */
@RepositoryRestResource(collectionResourceRel = "midprogressrecieving", path="midprogressrecieving")
public interface MidProgressRecievingDao extends JpaRepository<MidProgressRecieving,Long>{

    @Query(value="SELECT\n" +
			"\tm.* \n" +
			"FROM\n" +
			"\tzt_midprogressrecieving m\n" +
			"\tLEFT JOIN zt_employee e ON e.id = m.operatorId\n" +
			"\tleft join zt_productmanagedetails pd on pd.id=m.productManageDetailsId\n" +
			"\tLEFT JOIN zt_productionplandetails pp on pp.id=pd.productManage_id\n" +
			"\tleft JOIN zt_salesplan s on s.id=pp.salesPlanId\n" +
			"WHERE\n" +
			"\tm.enable = TRUE \n" +
			"AND\n" +
			"IF\n" +
			"\t( :clientName != '', s.clientName LIKE %:clientName%, 1 = 1 ) \n" +
			"AND\n" +
			"IF\n" +
			"\t( :productName != '', s.productName LIKE %:productName%, 1 = 1 ) \n" +
			"AND\n" +
			"IF\n" +
			"\t( :name != '', e.NAME LIKE %:name%, 1 = 1 ) \n" +
			"AND\n" +
			"IF\n" +
			"\t( :start != '', DATE_FORMAT( m.createDate, '%Y-%m-%d %k:%i:%s' ) >=:start, 1 = 1 ) \n" +
			"AND\n" +
			"IF\n" +
			"\t( :end != '', DATE_FORMAT( m.createDate, '%Y-%m-%d %k:%i:%s' ) <=:end, 1 = 1 ) ",
			countQuery = "SELECT\n" +
					"count(*)" +
					"FROM\n" +
					"\tzt_midprogressrecieving m\n" +
					"\tLEFT JOIN zt_employee e ON e.id = m.operatorId\n" +
					"\tleft join zt_productmanagedetails pd on pd.id=m.productManageDetailsId\n" +
					"\tLEFT JOIN zt_productionplandetails pp on pp.id=pd.productManage_id\n" +
					"\tleft JOIN zt_salesplan s on s.id=pp.salesPlanId\n" +
					"WHERE\n" +
					"\tm.enable = TRUE \n" +
					"AND\n" +
					"IF\n" +
					"\t( :clientName != '', s.clientName LIKE %:clientName%, 1 = 1 ) \n" +
					"AND\n" +
					"IF\n" +
					"\t( :productName != '', s.productName LIKE %:productName%, 1 = 1 ) \n" +
					"AND\n" +
					"IF\n" +
					"\t( :name != '', e.NAME LIKE %:name%, 1 = 1 ) \n" +
					"AND\n" +
					"IF\n" +
					"\t( :start != '', DATE_FORMAT( m.createDate, '%Y-%m-%d %k:%i:%s' ) >=:start, 1 = 1 ) \n" +
					"AND\n" +
					"IF\n" +
					"\t( :end != '', DATE_FORMAT( m.createDate, '%Y-%m-%d %k:%i:%s' ) <=:end, 1 = 1 ) ",
			nativeQuery = true)
	Page<MidProgressRecieving> findbyPage(@Param("clientName") String clientName,
										  @Param("productName") String productName,
										  @Param("name") String empName,
										  @Param("start") String start,
										  @Param("end")String end,
										  Pageable pageable);

	/*
	修改周转表
	 */
	@Modifying
	@Query("update MidProgressRecieving m set m.quantity=:quantity where m.productManageDetailsId=:productManageDetailsId")
	int update(@Param("quantity") float quantity,@Param("productManageDetailsId") long productManageDetailsId);

	/*
	根据销售计划Id查询对应的信息
	 */
    @Query(value = "select * from zt_midprogressrecieving m where m.enable = true and m.salesPlanId=:salesPlanId and m.productProcessCode = 'ENDSTEP'",nativeQuery = true)
    List<MidProgressRecieving> findBySalesPlanId(@Param("salesPlanId")long salesPlanId);

    @Query(value = "select sum(m.quantity) from zt_midprogressrecieving m where m.enable = true and m.salesPlanId=:salesPlanId and m.productProcessCode = 'ENDSTEP'",nativeQuery = true)
	float findSumQuantity(@Param("salesPlanId")long salesPlanId);

    @Query(value = "select * from zt_midprogressrecieving m where m.enable = true and m.productManageDetailsId=:productManageDetailsId",nativeQuery = true)
	MidProgressRecieving findByManageDetailId(@Param("productManageDetailsId")long productManageDetailsId);
}
