
package com.zt.dao;

import com.zt.po.ClientContact;
import com.zt.po.ClientType;
import com.zt.po.ProductUse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Set;

/**
* @author whl
* @version 2019年10月10日
* JPA库的连接(员工信息)
*/
@RepositoryRestResource(collectionResourceRel = "clientType", path="clientType")
public interface ClientTypeDao extends JpaRepository<ClientType, Long>{
	/*
      查询所有
       */
	@Query("select c from ClientType c where c.enabled=true")
	List<ClientType> findAll();

    ClientType findById(long id);
//	@Query("select c from ProductUse c where c.enabled=true and c.id in :ids")
//	List<ClientType> findAllUse(@Param("ids") List<Long> ids);
	@Modifying
	@Query("update  ClientType  p  set p.enabled=false where p.id=?1")
	int deleteUse(long id);
//	//     查询所有产品种类的数量
	@Query(value = "SELECT u.id, COUNT(c.id) as num,u.name FROM zt_clienttype  u  LEFT JOIN  zt_client   c  ON c.types=u.`name` GROUP BY u.id ORDER BY  u.id DESC",nativeQuery = true)
	Set<Object> findallprodutType();
	@Query("from ClientType c where c.enabled=true and c.name in :resultList")
	List<ClientType> findByUseName(List<String> resultList);
	@Query(value = "select COUNT(*)  from zt_clienttype c where c.enabled=true and c.name=?1",nativeQuery = true)
	int finddfByName(String name);
	@Query(value = "select COUNT(*)  from zt_clienttype c where c.enabled=true and c.name=?1 and c.id!=?2",nativeQuery = true)
	int finddfByNameAndId(String trim, long id);
}


