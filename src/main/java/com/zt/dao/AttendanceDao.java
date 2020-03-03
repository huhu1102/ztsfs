/**
 * 
 */
package com.zt.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.zt.po.Attendance;

/**
 * @author yh
 * @date 2019年5月7日
 */
@RepositoryRestResource(collectionResourceRel = "attendance", path="attendance")
public interface AttendanceDao extends JpaRepository<Attendance,Long>{

	Attendance findById(long id);
}
