/**
 * 
 */
package com.zt.dao;

import com.zt.po.SysUnit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Set;

/**
 * @author yh
 * @date 2019年5月7日
 */
@RepositoryRestResource(collectionResourceRel = "unit", path="unit")
public interface SysUnitDao extends JpaRepository<SysUnit,Long>{

	/**
	 * @param queryName
	 * @param pageable
	 * @return
	 */
   @Query("from SysUnit s where s.enabled=true and s.name  like %?1% order by s.id desc") //..
	Page<SysUnit> findbypages(String queryName, Pageable pageable);
   @Query("from SysUnit s where s.enabled=true order by s.id desc") //..
   Set<SysUnit> findAllUnits();
/**
 * @param unitId
 * @return
 */
   @Query("select s.name from SysUnit s where  s.id=?1 ")
    String findUtilsName(Long unitId);


   SysUnit findById(long id);
}
