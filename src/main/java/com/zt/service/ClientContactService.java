package com.zt.service;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.po.ClientContact;

/**
 * 客户种类
 */
public interface ClientContactService {
    /*
   查询所有
    */
    ResultObject<ClientContact> findAll(int page, int size, String qureyName) throws BusinessRuntimeException;
    /*
    新增和修改
     */
    ResultObject<ClientContact> add(ClientContact type)throws BusinessRuntimeException;
    /*
    删除
     */
    ResultObject<ClientContact> delete(long id)throws BusinessRuntimeException;

    ResultObject<ClientContact> update(ClientContact types);

}
