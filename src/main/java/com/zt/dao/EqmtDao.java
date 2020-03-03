/**
 * 
 */
package com.zt.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import com.zt.po.Bidding;
import com.zt.po.DeviceInfo;
import com.zt.po.Eqmt;

/**
 * @author yh
 * @date 2019年4月17日
 */
@RepositoryRestResource(collectionResourceRel = "eqmtinfo", path="eqmtinfo") 
public interface EqmtDao  extends JpaRepository<Eqmt, Long>{
		
	//修改设备记录表
	@Transactional
	@Modifying
	@Query(value="update empt ept set ept.repairResult=?1 where ept.repairman=?2",nativeQuery = true )
	public int updateByrepairman(String repairResult,String repairman);
	//查询设备表根据设备型号
	
	@Query(value="from empt e  where e.def.deviceName=?1",nativeQuery = true)
	public List<Eqmt> updateBymachineName(String deviceName);
	
	//自定义分页查询
	/*
	 * 自定义分页查询
	 */
	
	@Query("from Eqmt epte where epte.def.deviceName=:deviceName")  
	public List<Eqmt> findEquipmentMaintenance(@Param("deviceName") String deviceName);
	/*
	 * 自定义分页模糊条件查询
	 */
    @Query("select t from Eqmt t where t.def.deviceName like %?1% ")
    Page<Eqmt> findSearch(String query, Pageable pageable);
	
    //新加的方法
    @Query("select emt  from Eqmt emt where emt.enabled=true and emt.repairman like %?1% ") 
    Page<Eqmt> findbypage(String repairman, Pageable pageable);
  //
    
    @Query(value="select * from zt_eqmt as et where et.enabled=true and et.repairman like %?1% order by et.createDate desc limit ?2,?3",
    		nativeQuery =true) //..
    List<Eqmt> findAllByPage(String repairman,Integer pageNumber,Integer pageSize);
    
    @Query(value="select count(*) from zt_eqmt as et where et.enabled=true and et.repairman like %?1%",
    		   nativeQuery =true) //.
    Integer countAllData(String repairman);
    Eqmt findById(long id);
}
