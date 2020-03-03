package com.zt.service;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.ReturnVisit;

/**
 * @author wl
 * @date 2019年5月9日 
 */
public interface ReturnVisitService {
	/*
	 * 分页模糊条件查询
	 */
	ResultPage<ReturnVisit> findSearch(String queryName, int page, int size) throws BusinessRuntimeException;
	/*
	 * 更新和修改
	 */
	ResultObject<ReturnVisit> savePosition(ReturnVisit returnVisit)throws BusinessRuntimeException;
	/*
	 * 信息的删除
	 */
	ResultObject<ReturnVisit> deletDel(long id)throws BusinessRuntimeException;

    ResultPage<ReturnVisit> findByBillId(long billId);
}
