package com.zt.service;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.po.ClientType;
import com.zt.po.ProductUse;

/**
 * 客户种类
 */
public interface ClientTypeService {
    /*
   查询所有
    */
    ResultObject<ClientType> findAll() throws BusinessRuntimeException;
    /*
    新增和修改
     */
    ResultObject<ClientType> add(ClientType type)throws BusinessRuntimeException;
    /*
    删除
     */
    ResultObject<ClientType> delete(long id)throws BusinessRuntimeException;

    ResultObject<ClientType> update(ClientType types);

}
