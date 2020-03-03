/**  
* 
*/  
 
package com.zt.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.zt.po.SysMessage;
import com.zt.po.SysRole;

/**
 * @author whl
 * @date 2019年4月12日 
 */
@RepositoryRestResource(collectionResourceRel = "sysroles", path="sysroles")
public interface SysRoleseDao  extends  JpaRepository<SysRole, Long>{
	SysRole findById(Integer id);

	/**
	 * @param
	 * @param
	 * @return
	 */
   @Query("from SysRole s where s.enabled=true   order by s.id desc") //..
	List<SysRole> findbypages();

	/**
	 * @param roles
	 * @return
	 */
   @Query("from SysRole s where s.id in(?1)")
	List<SysRole> findbyIds(List<Integer> roles);
	@Query("from SysRole s where s.enabled=true and s.id =?1")
    SysRole findByRoleId(Integer parseInt);

	@Query("select s from SysRole s where s.enabled=true")
	List<SysRole> findAll();
}
