/**
 * 
 */
package com.zt.service;

import com.zt.po.SalesPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.Client;
import com.zt.po.Position;
import com.zt.vo.ClientModel;

/**
 * @author wl
 * @date 2019年4月15日 
 * 客户信息业务层接口
 * 
 */
public interface ClientService {
	/*
	 * 新增
	 */
	ResultObject<Client> saveClient(ClientModel client)throws BusinessRuntimeException;
	/*
	 * 修改
	 */
	ResultObject<Client> update(ClientModel client)throws BusinessRuntimeException;
    /*
     * 分页模糊条件查询
     */
	ResultPage<Client> findSearch(String queryName, int page, int size, String types,String shopType) throws BusinessRuntimeException;
    
    /*
     * 删除
     */
    ResultObject<Client> deletCli(long id)throws BusinessRuntimeException;
	/**
	 * @return
	 */
	ResultObject<Client> getbasdata()throws BusinessRuntimeException;

    ResultPage<SalesPlan> findSalesPlan(long clientId, int page, int size);
	
    
    
}
