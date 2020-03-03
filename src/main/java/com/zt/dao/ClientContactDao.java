
package com.zt.dao;

import com.zt.po.Client;
import com.zt.po.ClientContact;
import com.zt.po.ClientType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
* @author whl
* @version 2019年10月10日
* JPA库的连接(员工信息)
*/
@RepositoryRestResource(collectionResourceRel = "empContact", path="empContact")
public interface ClientContactDao extends JpaRepository<ClientContact, Long>{

	ClientContact findById(long id);
	/*
      查询所有
       */
	@Query("select c from ClientContact c where c.enabled=true")
	List<ClientContact> findAll();
	@Modifying
	@Query("update  ClientContact  p  set p.enabled=false where p.id=?1")
	int deleteUse(long id);
	//	//     查询所有产品种类的数量
	@Query("from ClientContact c where c.enabled=true and c.name in :resultList")
	List<ClientContact> findByUseName(List<String> resultList);
	@Query(value = "select COUNT(*)  from zt_clientContact c where c.enabled=true and c.name=?1",nativeQuery = true)
	int finddfByName(String name);
	@Query(value = "select COUNT(*)  from zt_clientContact c where c.enabled=true and c.name=?1 and c.id!=?2",

			nativeQuery = true)
	int finddfByNameAndId(String trim, long id);
	@Query(value = "select * from zt_clientContact c where c.enabled=true and  IF (ISNULL(:queryName) || LENGTH(trim(:queryName))<1,1 = 1,c.name LIKE %:queryName%) ",
			countQuery = "select COUNT(*) from zt_clientContact c where c.enabled=true and  IF (ISNULL(:queryName) || LENGTH(trim(:queryName))<1,1 = 1,c.name LIKE %:queryName%)",
			nativeQuery = true)
	Page<ClientContact> findAllByPage( @Param("queryName")String queryName, Pageable pageable);

//	@Query("select  DISTINCT u from Client u   where  u.enabled=true and u.status=:shopType and u.id in(SELECT z.id  from Client  z LEFT JOIN  Client  c  on z.id=c.parentClientId  WHERE (z.name  LIKE %:name% and z.parentClientId=0 ) OR (c.name  LIKE %:name% and c.parentClientId!=0 ))")
//	Page<Client> findSearch(@Param("shopType")String shopType, @Param("name")String name, Pageable pageable);

}
