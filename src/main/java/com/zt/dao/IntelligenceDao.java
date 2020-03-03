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


import com.zt.po.Intelligence;

/**
 * @author yh
 * @date 2019年5月9日
 */
@RepositoryRestResource(collectionResourceRel = "intelligenceinfo", path="intelligenceinfo") 
public interface IntelligenceDao extends JpaRepository<Intelligence, Long>{
	 //新加的方法
    @Query("select  itge from  Intelligence itge where itge.enabled=true and itge.type like %?1% ") 
    Page<Intelligence> findbypage(String type, Pageable pageable);

    
    @Query(value="select * from zt_intelligence as et where et.enabled=true and et.type like %?1% order by et.createDate desc limit ?2,?3",
    		nativeQuery =true) //..
    List<Intelligence> findAllByPage(String type,Integer pageNumber,Integer pageSize);
    
    @Query(value="select count(*) from zt_intelligence as et where et.enabled=true and et.type like %?1%",
    		   nativeQuery =true) //.
    Integer countAllData(String type);
    Intelligence findById(long id);
}
