package com.zt.service;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.Shipping;

/**
 * @author wl
 * @date 2019年5月30日 
 */
public interface ShippingService {
	/*
     * 分页模糊条件查询
     */
	public ResultPage<Shipping> findSearch(String queryName, int page, int size) throws BusinessRuntimeException;
    
    /*
     * 删除
     */
    public ResultObject<Shipping> deletSh(long id)throws BusinessRuntimeException;
    
    /*
     * 修改或者增加
     * */
    public ResultObject<Shipping> updateSh(Shipping shipping)throws BusinessRuntimeException;
   
}
