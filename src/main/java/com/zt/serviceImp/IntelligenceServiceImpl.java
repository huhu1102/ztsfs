/**
 * 
 */
package com.zt.serviceImp;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.zt.common.PageUtil;

import com.zt.dao.IntelligenceDao;
import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;

import com.zt.po.Intelligence;
import com.zt.service.IntelligenceService;

/**
 * @author yh
 * @date 2019年5月9日
 */
@Service("intelligenceService")
@CacheConfig(cacheNames = "zt_caches_intelligence")
public class IntelligenceServiceImpl implements IntelligenceService {
	@Autowired
	private IntelligenceDao ilgcd;
	
	@Override
	@Cacheable(key="'intelligence_'+#Status")
	public ResultPage<Intelligence> findIntelligence(String type, int page, int size) throws BusinessRuntimeException {
		ResultPage<Intelligence> ro=new ResultPage<Intelligence>();
		Pageable pageable = PageRequest.of(page-1,size);
//		Page<VehicleInfo> pages =vid.findSearch(queryName, pageable);
		List<Intelligence> pagelist=ilgcd.findAllByPage(type, (page-1)*size, size);
		int count=ilgcd.countAllData(type);
		 if (pagelist!=null) {
//	        	int totals=(int) pages.getTotalElements();
	        	 ro.setData(pagelist);
	    	     ro.setTotal(count);
	    	     ro.setTotalPages(PageUtil.getTotalPages(count, size));
	    	     ro.setSuccess(true);
			} else {
				ro.setSuccess(false);
				throw  new BusinessRuntimeException(ResultCode.UNKNOWN_ERROR);
			}
		
		return ro;
	}

	@Override
	@CacheEvict(allEntries=true)//清除缓存
	public ResultObject<Intelligence> addIntelligence(Intelligence itlgc) throws BusinessRuntimeException {
		ResultObject<Intelligence> ro=new ResultObject<>();
		if(Long.valueOf(itlgc.getId())==null||itlgc.getId()==0) {
			itlgc.setCreateDate(new Date());
			
		}				
		itlgc.setEnabled(true);;
		Intelligence ars=ilgcd.saveAndFlush(itlgc);
		 if(ars!=null) {
			   ro.setSuccess(true);
				ro.setMsg("操作成功");;
		   }else {
			   ro.setSuccess(false);
			   ro.setMsg("操作失败");
			throw   new BusinessRuntimeException(ResultCode.OPER_FAILED);
		   }
		
		return ro;
	}

	@Override
	@CacheEvict(allEntries=true)//清除缓存
	public ResultObject<Intelligence> deletIntelligence(long id) throws BusinessRuntimeException {
		ResultObject<Intelligence> ro=new ResultObject<>(); 
		Intelligence acris=ilgcd.findById(id);
		 if (acris!=null) {
			 acris.setEnabled(false);
			 ilgcd.saveAndFlush(acris);
			ro.setSuccess(true);
			ro.setMsg("操作成功");
		}else {
			ro.setSuccess(false);
			ro.setMsg("操作失败");
		throw	new BusinessRuntimeException(ResultCode.OPER_FAILED);
		}  
		return ro;
	}

}
