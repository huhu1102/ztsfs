/**  
* 
*/  
 
package com.zt.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.zt.po.MsgContent;

import java.util.List;

/**
 * @author whl
 * @date 2019年5月20日 
 */
@RepositoryRestResource(collectionResourceRel = "msg", path="msg")
public interface MsgContentDao extends  JpaRepository<MsgContent,Long> {

	MsgContent findById(long id);

	/**
	 * @param styles
	 * @param styleId
	 * @return
	 */
	 @Query("from MsgContent m where m.msgStyle=?1 and m.msgStyleId=?2")
	 MsgContent findmsg(String styles, long styleId);

}
