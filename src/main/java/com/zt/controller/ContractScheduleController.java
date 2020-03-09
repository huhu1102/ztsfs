package com.zt.controller;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.po.ContractSchedule;
import com.zt.service.ContractScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contractschedule")
public class ContractScheduleController {
    @Autowired
    ContractScheduleService contractScheduleService;
    /*
   查询方法
    */
    @RequestMapping(value = "/findAll",method = RequestMethod.GET)
    public ResultObject<ContractSchedule> findAll() throws BusinessRuntimeException {
        return contractScheduleService.findAll();
    }
    /*
    新增修改
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResultObject<ContractSchedule> add(ContractSchedule  contractSchedule) throws BusinessRuntimeException {
        return contractScheduleService.add(contractSchedule);
    }
    /*
    删除
     */
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public ResultObject<ContractSchedule> delete(long id) throws BusinessRuntimeException {
        return contractScheduleService.delete(id);
    }
}
