package com.zt.controller;

import com.zt.model.ResultObject;
import com.zt.po.ClientType;
import com.zt.po.ProductUse;
import com.zt.service.ClientTypeService;
import com.zt.service.ProductedUseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clientType")
public class CientTypeController {
    @Autowired
    ClientTypeService productedUseService;
    /*
     * 分页模糊条件查询
     */
    @RequestMapping(value="/findAll",method= RequestMethod.GET)
    public ResultObject<ClientType> findALL() {
        return productedUseService.findAll();
    }
    /*
    新增
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResultObject<ClientType> add(ClientType types){
        return productedUseService.add(types);
    }
    /**
     更新
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public ResultObject<ClientType> update(ClientType types){
        return productedUseService.update(types);
    }
    /*
    删除
     */
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public ResultObject<ClientType> delete(long id){
        return productedUseService.delete(id);
    }

}
