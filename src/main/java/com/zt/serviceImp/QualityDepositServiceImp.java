package com.zt.serviceImp;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.zt.dao.QualityDepositDao;
import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject; 
import com.zt.po.QualityDeposit;
import com.zt.service.QualityDepositService;

/**
 * @author wl
 * @date 2019年5月6日 
 */
@Service("qualityDepositService")
@CacheConfig(cacheNames = "zt_caches_qualitydeposit")
public class QualityDepositServiceImp implements QualityDepositService{
	@Autowired
	QualityDepositDao qualityDeposiDao;
	
	
	/*
	 * 信息的修改
	 */
	@Override
	@CacheEvict(allEntries = true)
	public ResultObject<QualityDeposit> savePosition(QualityDeposit qualityDeposit) throws BusinessRuntimeException {
		ResultObject<QualityDeposit> ro=new ResultObject<>();
		if(Long.valueOf(qualityDeposit.getId())==null) {
			qualityDeposit.setCreateDate(new Date());
		}
		qualityDeposit.setEnabled(true);
		QualityDeposit dere= qualityDeposiDao.saveAndFlush(qualityDeposit);
		 if(dere!=null) {
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
	 * 信息的删除
	 */
	@Override
	@CacheEvict(allEntries = true)
	public ResultObject<QualityDeposit> deletDel(long id) throws BusinessRuntimeException {
		ResultObject<QualityDeposit> ro=new ResultObject<>(); 
		QualityDeposit pos=qualityDeposiDao.findById(id);
		 if (pos!=null) {
			pos.setEnabled(false);
			qualityDeposiDao.saveAndFlush(pos);
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
