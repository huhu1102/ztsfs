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

import com.zt.po.BiddingNotice;
/**
 * @author yh
 * @date 2019年5月10日
 */
@RepositoryRestResource(collectionResourceRel = "bidnotice", path="bidnotice")
public interface BiddingNoticeDao extends JpaRepository<BiddingNotice, Long>{
	 @Query("select b  from BiddingNotice b where b.enabled=true and b.name like %?1% ") 
	 Page<BiddingNotice> findbypage(String query, Pageable pageable);
    
	 @Query(value="select * from zt_biddingnotice b where b.enabled=true and b.numbers like %?1% order by b.createDate desc limit ?2,?3",
    		nativeQuery =true) //..
	 List<BiddingNotice> findAllByPage(String numbers,Integer pageNumber,Integer pageSize);
    
	 @Query(value="select count(*) from zt_biddingnotice  as b where b.enabled=true and b.numbers like %?1%",
    		   nativeQuery =true) //.
	 Integer countAllData(String tdInstitution);

	 @Query("select b from BiddingNotice b where b.enabled=true and b.id=?1")
	 BiddingNotice findById(long id);
}
