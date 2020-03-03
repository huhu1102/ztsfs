package com.zt.service;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.po.QualityDeposit;

/**
 * @author wl
 * @date 2019年5月6日 
 */
public interface QualityDepositService {
	/*
	 * 信息的修改
	 */
	public  ResultObject<QualityDeposit> savePosition(QualityDeposit qualityDeposit)throws BusinessRuntimeException;
	/*
	 * 信息的删除
	 */
	public ResultObject<QualityDeposit> deletDel(long id)throws BusinessRuntimeException;
}
