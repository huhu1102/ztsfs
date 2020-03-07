package com.zt.controller;

import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.ContractFile;
import com.zt.service.ContractFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contractfile")
public class ContractFileController {
    @Autowired
    ContractFileService contractFileService;
    /*
    分页查询
     */
    @RequestMapping(value = "/find",method = RequestMethod.GET)
    public ResultPage<ContractFile> findSearch(int page, int size) {
        return contractFileService.findSearch(page, size);
    }

    /*
    新增
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResultObject<ContractFile> add(ContractFile contractFile) {
        return contractFileService.add(contractFile);
    }

    /*
    删除
     */
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public ResultObject<ContractFile> delete(long id) {
        return contractFileService.delete(id);
    }

}
