/**
 * 
 */
package com.zt.serviceImp;


import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.zt.dao.SysUnitDao;
import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.SysRole;
import com.zt.po.SysUnit;
import com.zt.service.SysUnitService;

/**
 * @author wl
 * @date 2019年4月15日 
 * 客户信息业务层的实现类
 */
 @Service("unitService")
 @CacheConfig(cacheNames = "zt_caches_sysUnit")
public class SysUnitServiceImp implements SysUnitService{
	 
	@Autowired
	SysUnitDao unitDao; 
	/*
	 * 修改客户信息
	 */

	@Override
	public ResultPage<SysUnit> findbypages(String queryName, int page, int size) throws BusinessRuntimeException {
		ResultPage<SysUnit> ro=new ResultPage<SysUnit>();
		Pageable pageable = PageRequest.of(page-1,size);
		Page<SysUnit> pages = unitDao.findbypages(queryName,pageable);
		
		 if (pages!=null) {
	        	int totals=(int) pages.getTotalElements();
	        	 ro.setData(pages.getContent());
	    	     ro.setTotal(totals);
	    	     ro.setTotalPages(pages.getTotalPages());
	    	     ro.setSuccess(true);
			} else {
				ro.setSuccess(false);
				throw new BusinessRuntimeException(ResultCode.UNKNOWN_ERROR);
			}
		return ro;
	}

	@Override
	public ResultObject<SysUnit> save(SysUnit unit) throws BusinessRuntimeException {
		ResultObject<SysUnit> ro=new ResultObject<>();
		if (Long.valueOf(unit.getId())==null||unit.getId()==0) {
			unit.setCreateDate(new Date());
		}
		unit.setEnabled(true);
		unit= unitDao.saveAndFlush(unit);
		 if(unit!=null) {
			   ro.setSuccess(true);
			   ro.setMsg("操作成功");
		   }else {
			   ro.setSuccess(false);		
			   ro.setMsg("操作失败");
			   throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
		   }
		
		return ro;
	}

	@Override
	public ResultObject<SysUnit> delete(long id) throws BusinessRuntimeException {
		ResultObject<SysUnit> ro=new ResultObject<SysUnit>();
	   	SysUnit role = unitDao.findById(id);
		role.setEnabled(false);
		role= unitDao.saveAndFlush(role);
		if (role!=null) {
			ro.setSuccess(true);
			ro.setMsg("操作成功！");
		}else {
			ro.setSuccess(false);
			ro.setMsg("操作失败");
		}
		return ro;
	}
	

	

}
