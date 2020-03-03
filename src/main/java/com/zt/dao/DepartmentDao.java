/**  
* 
*/  
 
package com.zt.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.zt.po.Department;

/**
 * @author whl
 * @date 2019年4月15日 
 */
@RepositoryRestResource(collectionResourceRel = "department", path="department")
public interface DepartmentDao  extends  JpaRepository<Department, Long>{
	/*
	 * 自定义分页模糊条件查询
	 */
    @Query("from Department u where u.enable=true and u.name like %?1% ")
    Page<Department> findSearch(String query, Pageable pageable);
	/**
	 * @return
	 */
    @Query("from Department u where u.enable=true")
	List<Department> findAllDep();
    Department findById(long id);
	/**
	 *  根据部门 分组 查询员工
	 */
	@Query(value = "SELECT u.id, COUNT(p_q.e_id) as num,u.name FROM zt_department  u  LEFT JOIN  zt_emp_dept  p_q  ON p_q.dept_id=u.id GROUP BY u.id",nativeQuery = true)
    Set<Object> findAlldeps();
    @Query("from Department u where u.enable=true and u.id in ?1")
    List<Department> findAllbyIds(List<Long> deptlist);
}
