package com.zt.controller;


import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.ContractUseRecord;
import com.zt.service.ContractUseRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contractUseRecord")
public class ContractUseRecordController {
    @Autowired
    ContractUseRecordService contractUseRecordService;

    /*
    分页查询
     */
    @RequestMapping(value = "/find",method = RequestMethod.GET)
    public ResultPage<ContractUseRecord> findSearch(int page, int size) {
        return contractUseRecordService.findSearch(page, size);
    }

    /*
    新增
    */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResultObject<ContractUseRecord> add(ContractUseRecord contractUseRecord) {
        return contractUseRecordService.add(contractUseRecord);
    }

    /*
    删除
     */
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public ResultObject<ContractUseRecord> delete(long id) {
        return contractUseRecordService.delete(id);
    }
}
