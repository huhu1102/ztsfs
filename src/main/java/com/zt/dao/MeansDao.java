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

import com.zt.po.Means;

/**
 * @author yh
 * @date 2019年5月9日
 */
//认证资料
@RepositoryRestResource(collectionResourceRel = "means", path="means") 
public interface MeansDao extends JpaRepository<Means, Long> {
	//新加的方法
    @Query("select means  from  Means means where means.enabled=true and means.meansName like %?1% ") 
    Page<Means> findbypage(String meansName, Pageable pageable);

    
    @Query(value="select * from zt_means as mas where mas.enabled=true and mas.meansName like %?1% order by mas.createDate desc limit ?2,?3",
    		nativeQuery =true) //..
    List<Means> findAllByPage(String meansName,Integer pageNumber,Integer pageSize);
    
    @Query(value="select count(*) from zt_means as mas where mas.enabled=true and mas.meansName like %?1%",
    		   nativeQuery =true) //.
    Integer countAllData(String meansName);
    Means findById(long id);
}
