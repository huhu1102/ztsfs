package com.zt.service;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ContractScheduleModel;
import com.zt.model.ResultObject;
import com.zt.po.ContractSchedule;

public interface ContractScheduleService {

    /*
    查询所有
    */
    ResultObject<ContractSchedule> findAll() throws BusinessRuntimeException;
    /*
    新增和
     */
    ResultObject<ContractSchedule> add(ContractScheduleModel contractScheduleModel)throws BusinessRuntimeException;
    /*
    修改
     */
    ResultObject<ContractSchedule> update(ContractScheduleModel contractScheduleModel)throws BusinessRuntimeException;
    /*
    删除
     */
    ResultObject<ContractSchedule> delete(long id)throws BusinessRuntimeException;

}
