package com.zt.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.zt.po.VEmployee;
import com.zt.po.VehicleRecord;

/**
 * @author wl
 * @date 2019年4月26日 
 */
@RepositoryRestResource(collectionResourceRel = "vemployee", path="vemployee")
public interface VEmployeeDao extends JpaRepository<VEmployee, String>{
	@Query("select new com.zt.po.VEmployee(e.name,e.email) from Employee e where e.id like concat(?1,'%')")
	List<VEmployee> findVE(long id);
	
	
//	VEmployee findById(long id);
}
