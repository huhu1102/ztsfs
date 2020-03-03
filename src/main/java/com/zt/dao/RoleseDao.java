/**  
* 
*/  
 
package com.zt.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.zt.po.ReturnVisit;
import com.zt.po.Rolese;

/**
 * @author whl
 * @date 2019年4月12日 
 */
@RepositoryRestResource(collectionResourceRel = "roles", path="roles")
public interface RoleseDao  extends  JpaRepository<Rolese, Long>{

	Rolese findById(long id);
	
}
