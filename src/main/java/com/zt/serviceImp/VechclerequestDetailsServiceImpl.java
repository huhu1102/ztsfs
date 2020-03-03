/**
 * 
 */
package com.zt.serviceImp;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.zt.dao.VechclerequestDao;
import com.zt.dao.VechclerequestDetailsDao;
import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultPage;
import com.zt.po.VechclerequestDetails;
import com.zt.service.VechclerequestDetailsService;

/**
 * @author yh
 * @date 2019年5月21日
 */
@Service("vechclerequestdetails")
@CacheConfig(cacheNames = "zt_caches_vechclerequestdetails")
public class VechclerequestDetailsServiceImpl implements VechclerequestDetailsService {
	@Autowired
	private  VechclerequestDetailsDao vdd;
	@Autowired
	private  VechclerequestDao vqd;
	@Override
	public ResultPage<VechclerequestDetails> findVechclerequestDetails(Date preStartDate, Date preEndDate)
			throws BusinessRuntimeException {
		return null;
	}
//	//返回了一个日期清单列表
//	@Override
//	public List<VechclerequestDetails> findNotUse(List<Date> datelist) {
//		
//		return  vdd.findNotUse(datelist);
//	}
//	
}
