/**
 * 
 */
package com.zt.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.zt.po.Storage;

/**
 * @author yh
 * @date 2019年5月10日
 */
@RepositoryRestResource(collectionResourceRel = "storage", path="storage")
public interface StorageDao extends JpaRepository<Storage, Long>{
	 @Query("select bdg  from Storage bdg where bdg.enabled=true and bdg.designation like %?1% ") 
	    Page<Storage> findbypage(String query, Pageable pageable);
	  //
	    
	    @Query(value="select * from zt_storage as bdd where bdd.enabled=true and bdd.designation like %?1% order by bdd.createDate desc limit ?2,?3",
	    		nativeQuery =true) //..
	    List<Storage> findAllByPage(String designation,Integer pageNumber,Integer pageSize);
	    
	    @Query(value="select count(*) from zt_storage as bdd where bdd.enabled=true and bdd.designation like %?1%",
	    		   nativeQuery =true) //.
	    Integer countAllData(String designation);
	    
	    Storage findById(long id);
}
