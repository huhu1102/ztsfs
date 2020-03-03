
package com.zt.dao;

import com.zt.po.Client;
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
 * @date 2019年4月15日 
 * JPA库连接(客户信息)
 */
@RepositoryRestResource(collectionResourceRel = "client", path="client")
public interface ClientDao extends JpaRepository<Client, Long>{
	/*
	 * 自定义分页模糊条件查询
	 */
    @Query("select  DISTINCT u from Client u   where  u.enabled=true and u.status=:shopType and u.id in(SELECT z.id  from Client  z LEFT JOIN  Client  c  on z.id=c.parentClientId  WHERE (z.name  LIKE %:name% and z.parentClientId=0 ) OR (c.name  LIKE %:name% and c.parentClientId!=0 ))")
//    @Query("select  DISTINCT u from Client u   where  u.enabled=true and u.status=:shopType and   u.name  LIKE %:name% ")
    Page<Client> findSearch(@Param("shopType")String shopType, @Param("name")String name, Pageable pageable);
    @Query("select  DISTINCT u from Client u   where  u.enabled=true and u.status=:shopType and u.id in(SELECT z.id  from Client  z LEFT JOIN  Client  c  on z.id=c.parentClientId  WHERE ( z.parentClientId=0 ) OR ( c.parentClientId!=0 ))")
    Set<Client> findAllByTypes(@Param("shopType")String shopType);

    @Query(value = "select  DISTINCT u.*  from zt_client u   where  u.enabled=true and u.status=:shopType and IF(ISNULL(:name) || LENGTH(trim(:name))<1,1=1,u.`name` LIKE %:name%)  and (u.parentClientId=0 or u.parentClientId=u.id) and IF(ISNULL(:types) || LENGTH(trim(:types))<1,1=1,u.types LIKE %:types%)",nativeQuery = true)
    Page<Client> findAllByPages(@Param("shopType")String shopType, @Param("name")String name,@Param("types")String types, Pageable pageable);
	/**a
	 * @return
	 */
    @Query("select u from Client u ")
	List<Client> findallcompay();
    @Query("select u from Client u where  u.enabled=true and u.parentClientId=0")
    Set<Client>findAllParent();
    /*
     * 根据父Id查询所有信息
     */
//    @Query("select c from Client c  where c.parentClientId=:parenetId")
//    Client findByParentId(@Param("parenetId")long id);
    /*
     * 根据id查
     */
    Client findById(long id);


//    @Query("select c.name,c.id,c.parentClientId,c.parentName,c.address,c.phone,c.abbreviation from Client c where c.enabled = true and c.status = 'down' and c.parentClientId!=0 and c.id!=c.parentClientId")
//    @Query("from Client c where c.enabled = true and c.status = 'down' and (c.parentClientId=0 or c.id=c.parentClientId)")
    @Query("from Client c where c.enabled = true and c.status = 'down'")
    Set<Client>  findClient();

    /**
     *  客户更新方法
     * @param id
     * @param name
     * @param abbreviation
     * @param address
     * @param email
     * @param fax
     * @param infor
     * @param addressDetails
     * @return
     */
    @Modifying
    @Query("update Client c set c.name=:name,c.abbreviation=:abbreviation,c.address=:address,c.email=:email,c.fax=:fax,c.infor=:infor,c.addressDetails=:addressDetails where c.id=:id")
    int upDateClient(@Param("id") long id, @Param("name") String name,@Param("abbreviation") String abbreviation,@Param("address") String address,@Param("email") String email,@Param("fax") String fax,@Param("infor") String infor,@Param("addressDetails") String addressDetails);

    @Query("update Client c set c.name=:name,c.abbreviation=:abbreviation,c.address=:address,c.email=:email,c.fax=:fax,c.infor=:infor,c.addressDetails=:addressDetails where c.id=:id")
    Set<Object> findAllTypes();

    //修改定制过的产品
    @Modifying
    @Query("update Client c set c.productStr=:productStr where c.id=:id")
    int updateStr(@Param("productStr")String pro,@Param("id")long id);
}
