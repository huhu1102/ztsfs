package com.zt.serviceImp;

import java.util.Date;
import java.util.List;

import com.zt.dao.SalesOrderDetailsDao;
import com.zt.po.SalesOrderDetails;
import com.zt.service.SalesOrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;

/**
 * @author wl
 * @date 2019年5月9日 
 */
@Service("salesOrderDetailsService")
//@CacheConfig(cacheNames = "zt_caches_contractstatus")
public class SalesOrderDetailsServiceImp implements SalesOrderDetailsService {
	@Autowired
	SalesOrderDetailsDao salesOrderDetailsDao;
	/*
	 * 修改信息
	 */
	@Override
//	@CacheEvict(allEntries = true)
	public ResultObject<SalesOrderDetails> save(SalesOrderDetails contractStatus) {
		ResultObject<SalesOrderDetails> ro=new ResultObject<>();
		if(Long.valueOf(contractStatus.getId())==null||contractStatus.getId()==0) {
			contractStatus.setCreateDate(new Date());
		}
		contractStatus.setEnabled(true);
		SalesOrderDetails pro = salesOrderDetailsDao.saveAndFlush(contractStatus);
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
	 * 查找所有
	 */
	@Override
	
	public ResultObject<SalesOrderDetails> findAll() {
		ResultObject<SalesOrderDetails> ro = new ResultObject<>();
		List<SalesOrderDetails> wor =  salesOrderDetailsDao.findAll();
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
	 * 删除
	 */
	@Override
//	@CacheEvict(allEntries = true)
	public ResultObject<SalesOrderDetails> delet(long id) throws BusinessRuntimeException {
		ResultObject<SalesOrderDetails> ro=new ResultObject<>();
		SalesOrderDetails wor=salesOrderDetailsDao.findById(id);
		 if (wor!=null) {
			wor.setEnabled(false);
			 salesOrderDetailsDao.saveAndFlush(wor);
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
