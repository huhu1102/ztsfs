/**
 * 
 */
package com.zt.dao;





import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import com.zt.po.VechclerequestDetails;

/**
 * @author yh
 * @date 2019年5月20日
 */
@RepositoryRestResource(collectionResourceRel = "vechclerequestdetails", path="vechclerequestdetails")
public interface VechclerequestDetailsDao extends JpaRepository<VechclerequestDetails, Long> {

	//申请单详情表分页
	
	//
	//detailsDao.findNotUse(use);
	@Query("from Vechclerequest vcrd where vcrd.preStartDate=?1 and vcrd.preEndDate=?2")
	List<VechclerequestDetails> getDatelist(Date preStartDate, Date preEndDate);
	VechclerequestDetails findById(long id);
	/**
	 * @param use
	 * @return
	 */
	//这个sql 5/21 集合   传递车牌号  和该车辆申请时间
	@Query("from VechclerequestDetails  v where v.requestDetail.carNo=?1 and v.requestTime in(?2)")
	List<VechclerequestDetails> findNotUse(String carno, List<Date> datelist);
	
	//@Modifying
	//@Transactional
	//@Query("delete from EngineerServices es where es.engineerId = ?1")
	//int deleteByEgId(String engineerId);
}
