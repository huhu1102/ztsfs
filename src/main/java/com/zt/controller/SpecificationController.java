package com.zt.controller;

import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.Specification;
import com.zt.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/specification")
public class SpecificationController {
    @Autowired
    SpecificationService specificationService;
    /*
    分页查询
     */
    @RequestMapping(value = "/findbypage",method = RequestMethod.GET)
    public ResultPage<Specification> findSearch(String queryName, int page, int size){
        return specificationService.findSearch(queryName, page, size);
    }
    /*
    新增和更新
     */
    @RequestMapping(value = "/create",method =RequestMethod.POST)
    public ResultObject<Specification> create(Specification specification){
        return specificationService.create(specification);
    }
    /*
    删除
     */
    @RequestMapping(value = "delete",method = RequestMethod.GET)
    public ResultObject<Specification> delete(long id){
        return  specificationService.delete(id);
    }

}
