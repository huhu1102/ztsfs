package com.zt.controller;

import com.zt.model.ResultObject;
import com.zt.po.ClientContact;
import com.zt.service.ClientContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clientConcat")
public class CientConcatController {
    @Autowired
    ClientContactService contactService;
    /*
     * 分页模糊条件查询
     */
    @RequestMapping(value="/findByPage",method= RequestMethod.GET)
    public ResultObject<ClientContact> findALL(int page, int size ,String qureyName ) {
        return contactService.findAll(page,size,qureyName);
    }
    /*
    新增
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResultObject<ClientContact> add(ClientContact types){
        return contactService.add(types);
    }
    /**
     更新
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public ResultObject<ClientContact> update(ClientContact types){
        return contactService.update(types);
    }
    /*
    删除
     */
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public ResultObject<ClientContact> delete(long id){
        return contactService.delete(id);
    }

}
