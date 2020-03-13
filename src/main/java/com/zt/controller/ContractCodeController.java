package com.zt.controller;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.po.ContractCode;
import com.zt.service.ContractCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contractcode")
public class ContractCodeController {
    @Autowired
    ContractCodeService contractCodeService;
    /*
    查询方法
     */
    @RequestMapping(value = "/findAll",method = RequestMethod.GET)
    public ResultObject<ContractCode> findAllcodes() throws BusinessRuntimeException {
        return contractCodeService.findAllcodes();
    }

    /*
    新增修改`
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResultObject<ContractCode> add(ContractCode contractCode) throws BusinessRuntimeException {
        return contractCodeService.add(contractCode);
    }
    /*
    删除
     */
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public ResultObject<ContractCode> delete(long id) throws BusinessRuntimeException {
        return contractCodeService.delete(id);
    }
}
