/**  
* 
*/  
 
package com.zt.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.zt.po.MsgContent;
import com.zt.po.Position;
import com.zt.po.Product;
import com.zt.po.Storage;
import com.zt.po.SysMessage;

/**
 * @author whl
 * @date 2019年5月20日 
 */
@RepositoryRestResource(collectionResourceRel = "sysMsg", path="sysMsg")
public interface SysMessageDao extends JpaRepository<SysMessage, Long>{


	/**
	 * @param userId
	 * @param pageable
	 * @return
	 */
	@Query("from SysMessage s where s.userId =?1") 
	 Page<SysMessage> findbySyspages(Long userId, Pageable pageable);
//	  @Query("SELECT v.id AS id,v.msgState AS msgState,v.userId AS userId,IFNULL(m.backoutDate, ''),IFNULL(m.backoutId, 0),	IFNULL(m.backoutName, ''), m.id AS mid,m.title,m.username,m.message,m.userId FROM SysMessage AS v ,MsgContent m WHERE v.userId = ?1 and v.mid=m.id ORDER BY v.msgState DESC "
//	    		)
	  @Query(" FROM SysMessage AS v  WHERE v.userId = ?1  ORDER BY v.msgState ASC,v.id desc ")
		List<SysMessage> findAllByPage(Long userId);

		/**
		 * @param queryName
		 * @return
		 */
	    @Query(value="select count(*) from zt_sysmsg as v where  v.userId=?1",
	 		   nativeQuery =true) //
		int countAllData(Long userId);
	    
	    SysMessage findById(long id);
		/**
		 * @param mid
		 * @param userId
		 * @return
		 */
	    @Query("from SysMessage s where s.mid=?1 and s.userId=?2")
		SysMessage findChecked(Long mid, Long userId);
		/**
		 * @param id
		 * @return  修改sys状态为撤销 3
		 */
	    @Modifying
	    @Transactional
	    @Query("Update SysMessage s set s.msgState=3 where s.mid=?1 ")
		boolean updateSysMsg(Long id);
		/**
		 * @param userId
		 * @return
		 */
	    @Modifying
	    @Transactional
	    @Query("Update SysMessage s set s.msgState=2 where s.userId=?1 ")
	    int findbyuserId(Long userId);

}
