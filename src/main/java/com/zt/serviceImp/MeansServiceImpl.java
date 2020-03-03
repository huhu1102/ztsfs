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
import com.zt.dao.MeansDao;
import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.Intelligence;
import com.zt.po.Means;
import com.zt.service.MeansService;

/**
 * @author yh
 * @date 2019年5月9日
 */
@Service("meansService")
@CacheConfig(cacheNames = "zt_caches_means")
public class MeansServiceImpl implements MeansService {
	@Autowired
	private MeansDao meansDao;
	@Override
	@Cacheable(key="'means_'+#Status")//?
	public ResultPage<Means> findMeans(String meansName, int page, int size) throws BusinessRuntimeException {
		ResultPage<Means> ro=new ResultPage<Means>();
		Pageable pageable = PageRequest.of(page-1,size);
//		Page<VehicleInfo> pages =vid.findSearch(queryName, pageable);
		List<Means> pagelist=meansDao.findAllByPage(meansName, (page-1)*size, size);
		int count=meansDao.countAllData(meansName);
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
	public ResultObject<Means> addMeans(Means mans) throws BusinessRuntimeException {
		ResultObject<Means> ro=new ResultObject<>();
		if(Long.valueOf(mans.getId())==null||mans.getId()==0) {
			mans.setCreateDate(new Date());
			
		}				
		mans.setEnabled(true);;
		Means ars=meansDao.saveAndFlush(mans);
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
	public ResultObject<Means> deletMeans(long id) throws BusinessRuntimeException {
		ResultObject<Means> ro=new ResultObject<>(); 
		Means acris=meansDao.findById(id);
		 if (acris!=null) {
			 acris.setEnabled(false);
			 meansDao.saveAndFlush(acris);
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


