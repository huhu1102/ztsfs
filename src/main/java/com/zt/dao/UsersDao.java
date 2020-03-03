/**  
* 
*/  
 
package com.zt.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zt.po.Employee;
import com.zt.po.Users;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author whl
 * @date 2019年5月5日 
 */
public interface UsersDao extends JpaRepository<Users, Long> {
	/**
	 * @param userName
	 * @return
	 */
	@Query("from Users u where u.username=:name")  
    Users findUserByname(@Param("name") String name);

	/**
	 * @param userId
	 * @return
	 */
	@Query(value="SELECT * from zt_users u where u.id!=:userId",nativeQuery = true)  
	List<Users>  getAllUsersExcept(@Param("userId") long userId);


    @Query(value = "SELECT u.id  FROM zt_users u LEFT JOIN zt_employee e ON u.empId = e.id LEFT JOIN zt_department d ON e.departmentIds = d.id LEFT JOIN zt_position p ON e.positionIds = p.id WHERE  d.deNumber = :deNumber and (p.posNumber =:posNumber OR p.posNumber =:pos )",nativeQuery = true)
	Set<Long> findUserIdByDepPro(@Param("deNumber")String depNumber, @Param("posNumber")int posNumber, @Param("pos")int pos);


    /*
    查询所有userIds
     */
    @Query("select u.id from Users u where u.enabled=true")
    Set<Long> findIds();

	/**
	 * @param queryName
	 * @param pageable
	 * @return
	 */
    @Query("from Users u where u.enabled=true and u.username  like %?1% order by u.id desc") //.
	Page<Users> findbypages(String queryName, Pageable pageable);

	/**
	 * @param id
	 * @return
	 */
    @Modifying
    @Transactional
    @Query("Update Users s set s.enabled=false where s.id=?1 ")
	boolean updateUser(long id);

	/**
	 * @param emp
	 * @return
	 */
    @Query("select u.id from Users u where   u.empId=?1 ")
	List<Long> finduserIdbyEmpId(long empId);

    Users findById(long id);

	@Query("select u from Users u where  u.empId=?1 ")
	Users findByEmpId(long empId);

}
