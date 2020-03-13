package com.zt.service;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.po.ContractCode;
import com.zt.po.ContractSchedule;

public interface ContractScheduleService {

    /*
    查询所有
    */
    ResultObject<ContractSchedule> findAll() throws BusinessRuntimeException;
    /*
    新增和修改
     */
    ResultObject<ContractSchedule> add(ContractSchedule contractSchedule)throws BusinessRuntimeException;
    /*
    删除
     */
    ResultObject<ContractSchedule> delete(long id)throws BusinessRuntimeException;

    ResultObject<ContractSchedule> findByContractId(long id);
}
