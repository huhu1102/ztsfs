/**
 * 
 */
package com.zt.dao;

import com.zt.po.Bidding;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * @author yh
 * @date 2019年5月7日
 */
@RepositoryRestResource(collectionResourceRel = "bidding", path="bidding")
public interface BiddingDao extends JpaRepository<Bidding, Long> {
//新自定义模糊查询
    
    @Query("select b  from Bidding b where b.enabled=true and b.client.name like %?1% ") 
    Page<Bidding> findbypage(String query, Pageable pageable);
  
    
    @Query(value="select * from zt_bidding as bdd where bdd.enabled=true and bdd.numbers like %?1% order by bdd.createDate desc limit ?2,?3",
    		nativeQuery =true) //..
    List<Bidding> findAllByPage(String numbers,Integer pageNumber,Integer pageSize);
    
    @Query(value="select count(*) from zt_bidding as bdd where bdd.enabled=true and bdd.numbers like %?1%",
    		   nativeQuery =true) //.
    Integer countAllData(String numbers);
    
    @Query("select b from Bidding b where b.enabled=true and b.id=?1")
    Bidding findById(long id);
}
