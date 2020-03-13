package com.zt.service;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.po.ContractCode;


public interface ContractCodeService {
    /*
    查询所有
    */
    ResultObject<ContractCode> findAll() throws BusinessRuntimeException;
    /*
    新增和修改
     */
    ResultObject<ContractCode> add(ContractCode contractCode)throws BusinessRuntimeException;
    /*
    删除
     */
    ResultObject<ContractCode> delete(long id)throws BusinessRuntimeException;


}
