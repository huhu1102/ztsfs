package com.zt.serviceImp;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.zt.dao.WorkstepDao;
import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.Workstep;
import com.zt.service.WorkstepService;

/**
 * @author wl
 * @date 2019年4月18日 
 */
@Service("workstepService")
@CacheConfig(cacheNames = "zt_caches_workstep")
public class WorkstepServiceImp implements WorkstepService {

	@Autowired
	WorkstepDao workstepDao;
	/*
	 * 修改工步信息
	 */

	@Override
	@CacheEvict(allEntries = true)
	public ResultObject<Workstep> saveWorkstep(Workstep workstep) {
		ResultObject<Workstep> ro=new ResultObject<>();
		if(Long.valueOf(workstep.getId())==null||workstep.getId()==0) {
			workstep.setCreateDate(new Date());
		}	
		workstep.setEnabled(true);
		Workstep pro = workstepDao.saveAndFlush(workstep);
		if (pro!=null) {
			ro.setSuccess(true);
			ro.setMsg("操作成功");
		}else {
			ro.setSuccess(false);
			ro.setMsg("操作失败");
		}
		return ro;
	}
	/*
	 * 查询所有
	 */
	@Override
	public ResultObject<Workstep> findAll() {
		ResultObject<Workstep> ro = new ResultObject<>();
		List<Workstep> wor =  workstepDao.findAll();
		if (wor!=null) {
			ro.setData(wor);
			ro.setSuccess(true);
			ro.setMsg("操作成功");
		}else {
			ro.setSuccess(false);
			ro.setMsg("操作失败");
			throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
		}
		return ro;
		
	}
	/*
	 * 分页模糊查询
	 */
	@Override
	@Cacheable(key="'work_'+#queryName+'_'+#page+'_'+#size")
	public ResultPage<Workstep> findSearch(String queryName, int page, int size) throws BusinessRuntimeException {
		ResultPage<Workstep> ro=new ResultPage<Workstep>();
		Pageable pageable = PageRequest.of(page-1,size);
		Page<Workstep> pages = workstepDao.findSearch(queryName,pageable);
		
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
	/*
	 * 删除
	 */
	@Override
	public ResultObject<Workstep> deletWor(long id) throws BusinessRuntimeException {
		ResultObject<Workstep> ro=new ResultObject<>(); 
		Workstep wor=workstepDao.findById(id);
		 if (wor!=null) {
			wor.setEnabled(false);
			workstepDao.saveAndFlush(wor);
			ro.setSuccess(true);
			ro.setMsg("操作成功");
		}else {
			ro.setSuccess(false);
			ro.setMsg("操作失败");
			throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
		}  
		return ro;
	}
	
}
