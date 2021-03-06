package com.zt.controller;

import com.zt.model.ResultObject;
import com.zt.po.ProductUse;
import com.zt.service.ProductedUseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *  生产用途控制器
 * @author  whl
 * */
@RestController
@RequestMapping("/productuse")
public class ProdutUsecontroller {
    @Autowired
    ProductedUseService productedUseService;
    /*
     * 分页模糊条件查询
     */
    @RequestMapping(value="/findAll",method= RequestMethod.GET)
    public ResultObject<ProductUse> findALL() {
        return productedUseService.findAll();
    }
    /*
    新增
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResultObject<ProductUse> add(ProductUse color){
        return productedUseService.add(color);
    }
    /**
     更新
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public ResultObject<ProductUse> update(ProductUse color){
        return productedUseService.update(color);
    }
    /*
    删除
     */
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public ResultObject<ProductUse> delete(long id){
        return productedUseService.delete(id);
    }

}
