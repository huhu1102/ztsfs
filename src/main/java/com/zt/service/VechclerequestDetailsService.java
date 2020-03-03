/**
 * 
 */
package com.zt.service;

import java.util.Date;
import java.util.List;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultPage;

import com.zt.po.VechclerequestDetails;

/**
 * @author yh
 * @date 2019年5月21日
 */
public interface VechclerequestDetailsService {
  //返回一个集合
	public ResultPage<VechclerequestDetails> findVechclerequestDetails(Date preStartDate, Date preEndDate) throws BusinessRuntimeException;
	//返回日期列表
	//List<VechclerequestDetails> findNotUse(List<Date> datelist);
//	public List<VechclerequestDetails> findNotUse(List<Date> datelist);
	
	
	
}
