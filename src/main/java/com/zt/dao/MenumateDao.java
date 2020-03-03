/**  
* 
*/  
 
package com.zt.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.zt.po.Menu;
import com.zt.po.Menumate;

/**
 * @author whl
 * @date 2019年4月16日 
 */
@RepositoryRestResource(collectionResourceRel = "menumate", path="menumate")
public interface MenumateDao  extends JpaRepository<Menumate, Long> {
//	Menu findById(long id);
}
