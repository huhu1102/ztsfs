package com.zt.service;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ProductManageModel;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.ProductManage;

/**
 * @author wl
 * @date 2019年5月7日 
 */
public interface ProductManageService {
	/*
	 * 分页模糊条件查询
	 */
	ResultPage<ProductManage> findSearch(String clientName, String productName, String empName, String start, String end, Integer status,int page, int size) throws BusinessRuntimeException;
	/*
	 * 新增生产管理
	 */
	ResultObject<ProductManage> addProductionManage(String notes,long planDetailsId,long progressId)throws BusinessRuntimeException;
	/*
	 * 修改生产管理
	 */
	ResultObject<ProductManage> update(String note,long id) throws BusinessRuntimeException;
	/*
	 * 信息的删除
	 */
	ResultObject<ProductManage> deletPm(long id)throws BusinessRuntimeException;
	/*
	生产完成
	 */
    ResultObject<ProductManage> endProduction(long id)throws BusinessRuntimeException;

    ResultObject<ProductManage> findStatus()throws BusinessRuntimeException;

    ResultPage<ProductManage> findForSend(String clientName, String productName, String empName, String start, String end, Integer status, int page, int size);

    ResultObject<ProductManage> revoke(long id)throws BusinessRuntimeException;
}
