
package com.zt.dao;

import com.zt.po.Employee;
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
* @author wl
* @version 2019年4月13日
* JPA库的连接(员工信息)
*/
@RepositoryRestResource(collectionResourceRel = "employee", path="employee")
public interface EmployeeDao extends JpaRepository<Employee, Long>{
	/*
	 * 自定义分页查询
	 */
	@Query("from Employee u where u.name=:name")
    Employee findEmployee(@Param("name") String name);


	/**
	 * 自定义分页模糊条件查询
	 * @param name
	 * @param phone
	 * @param addr
	 * @param dept
	 * @param pageable
	 * @return
	 */

    @Query(value = "SELECT * from zt_employee e WHERE e.enabled=TRUE  and IF(ISNULL(:name) || LENGTH(trim(:name))<1,1=1,e.`name` LIKE %:name%)  and IF(ISNULL(:phone) || LENGTH(trim(:phone))<1,1=1,e.phone LIKE %:phone%)  and IF(ISNULL(:addr) || LENGTH(trim(:addr))<1,1=1,e.address LIKE %:addr%) and IF(ISNULL(:dept) || LENGTH(trim(:dept))<1,1=1,e.departmentName LIKE %:dept%)",nativeQuery = true)
	Page<Employee> findSearch(@Param("name") String name, @Param("phone")String phone, @Param("addr")String addr, @Param("dept")String dept, Pageable pageable);
	/**
	 * @param telphone
	 */
    @Query("from Employee u where u.enabled=true and u.phone =:telphone")
	public List<Employee>  getTel(@Param("telphone")String telphone);
	/**
	 * @return
	 */
    @Query("select max(u.id) from Employee u")
	Long findCurrentNo();

    Employee findById(long id);
	@Query("from Employee u where u.enabled=true")
	Set<Employee> findAllEmps();
	@Modifying
	@Query(value ="DELETE  FROM  zt_emp_dept WHERE e_id=?1",nativeQuery = true)
    int deleteDepart(long empId);
	@Modifying
	@Query(value = "DELETE  FROM  zt_emp_pos WHERE e_id=?1",nativeQuery = true)
	int deletePost(long id);

	@Modifying
	@Query("update Employee  e set e.enabled=false where e.id=?1 ")
	boolean updateEnabled(long id);

	@Query("select e from Employee e where  e.enabled=true")
	List<Employee> findAll();
}
