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

import com.zt.po.VehicleAllot;
import com.zt.po.VehicleInfo;

/**
 * @author yh
 * @date 2019年4月12日
 */
//映射访问路径
@RepositoryRestResource(collectionResourceRel = "vehicleInfor", path="vehicleInfor")
public interface VehicleInfoDao extends JpaRepository<VehicleInfo, Long> {
    // 根据状态查车辆信息
	 public  List<VehicleInfo> findByCarStatus(String status);
	 //占位符
	 @Transactional
	 @Modifying
	 @Query(value = "UPDATE vehicleinfo vcf set vcf.plateNumber=?1 where vcf.Id=?2",nativeQuery = true)//加上native表示的就是原生的sql语句
	 public int updateById(String licensePlateNumber,long id);
	 //@Transactional 注解加方法上
	@Modifying
	@Transactional
	@Query(value="DELETE FROM vehicleinfo  WHERE id=?1",nativeQuery = true)
	 public int deleteById(long id);
	
	VehicleInfo findById(long id);
	 
	 //根据车牌号查看车辆信息
	@Query("from VehicleInfo  vcif where vcif.plateNumber=:licensePlateNumber")  
	VehicleInfo  findVehicleInfos (@Param("licensePlateNumber") String licensePlateNumber);
	
	
	//根据车牌号查询车辆信息
	//@Query("from VehicleInfo  velf where velf.plateNumber=:plateNumber")  
	//VehicleInfo findVehicleInfo(@Param("plateNumbe") String plateNumbe);
	/*
	 * 自定义分页模糊条件查询  hql语句
	 */
    @Query("select velf from VehicleInfo velf where velf.enabled=true and velf.plateNumber like %?1% ") //...
    Page<VehicleInfo > findSearch(String query, Pageable pageable);
	
    /**
     * 
     * @param plateNumber
     * @param pageNumber
     * @param pageSize
     * @return
     *   自定义分页查询
     */
    @Query(value="select * from zt_vehicleInfo as v where v.enabled=true and v.plateNumber like %?1% order by v.id desc limit ?2,?3",
    		nativeQuery =true) //..
    List<VehicleInfo> findAllByPage(String plateNumber,Integer pageNumber,Integer pageSize);
    
    @Query(value="select count(*) from zt_vehicleInfo as v where v.enabled=true and v.plateNumber like %?1%",
    		   nativeQuery =true) //.
    Integer countAllData(String plateNumber);
    /**
	 * @param plateNumber
	 */
    //@Query("from VehicleInfo vf where vs.enabled=true and u.phone =:telphone")
	//public List<Employee>  getTel(@Param("telphone")String telphone);
	//  测试类
    public   VehicleInfo findByPlateNumber(String plateNumber);
  
  	@Transactional
  	@Modifying
  	@Query(("delete from VehicleInfo vf where vf.id in(?1)"))
  	public void deleteVehicleInfo(List<Long> ids);
	/**
	 * @return
	 */
  	@Query("from VehicleInfo  v where v.enabled=true") 
	public List<VehicleInfo> findAllcars();
  	
  	@Query("from VehicleInfo  v where v.enabled=true and v.carStatus='FREE'") 
	public List<VehicleInfo> findUnused();
	//查找未使用且未维护的车辆信息
  	@Query("from VehicleInfo  v where v.enabled=true and v.carStatus='UN'") 
	public List<VehicleInfo> findUn();
  	
  	
  	
}
