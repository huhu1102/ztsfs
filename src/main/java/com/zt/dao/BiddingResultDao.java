/**
 * 
 */
package com.zt.dao;

import com.zt.po.BiddingResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * @author yh
 * @date 2019年5月9日
 */
@RepositoryRestResource(collectionResourceRel = "bidResutl", path="bidResutl")
public interface BiddingResultDao extends JpaRepository<BiddingResult, Long> {
//新自定义模糊查询
    
    @Query("select b  from BiddingResult  b  where b.enabled=true and b.client.name like %?1% ") 
    Page<BiddingResult> findbypage(String completeStatus, Pageable pageable);
  
    
    @Query(value="select * from zt_biddingresult  as bdgc where bdgc.enabled=true and bdgc.status like %?1% order by bdgc.createDate desc limit ?2,?3",
    		nativeQuery =true) 
    List<BiddingResult> findAllByPage(String completeStatus,Integer pageNumber,Integer pageSize);
    
    @Query(value="select count(*) from zt_biddingresult  as bdd where bdd.enabled=true and bdd.status like %?1%",
    		   nativeQuery =true) 
    Integer countAllData(String completeStatus);
    BiddingResult findById(long id);
}
