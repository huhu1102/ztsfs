package com.zt.controller;

import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.Client;
import com.zt.po.Product;
import com.zt.po.ProductManage;
import com.zt.po.SalesPlan;
import com.zt.service.SalesPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sales")
public class SalesPlanController {
    @Autowired
    SalesPlanService salesPlanService;

    /*
    查询
     */
    @RequestMapping(value = "/find",method = RequestMethod.GET)
    public ResultPage<Object> find(String clientName, String productName, String empName, String start, String end, Integer status,int page, int size){
        return salesPlanService.find(clientName,productName,empName,start,end,status, page, size);
    }
    /*
    查询销售计划状态的个数
     */
    @RequestMapping(value = "/findStatus",method = RequestMethod.GET)
    public ResultObject<SalesPlan> findStatus(){
        return salesPlanService.findStatus();
    }
    /**
    *根据下发状态查询销售计划
    */
    @RequestMapping(value = "/findplan",method = RequestMethod.GET)
    public ResultPage<Object> findForplan(String clientName, String productName, String empName, String start, String end, Integer status,int page, int size){
        return salesPlanService.findForPlan(clientName,productName,empName,start,end,status, page, size);
    }
    /**
     *根据下发状态1或者2查询销售计划
     */
    @RequestMapping(value = "/findfornoplan",method = RequestMethod.GET)
    public ResultPage<Object> findForNoplan(String clientName, String productName, String empName, String start, String end, Integer status,int page, int size){
        return salesPlanService.findForNoPlan(clientName,productName,empName,start,end,status,page,size);
    }

    /*
    查询未发货完成的销售计划
     */
    @RequestMapping(value = "/findnotshipping",method = RequestMethod.GET)
    public ResultPage<SalesPlan> findForShipping(String clientName, String productName, String empName, String start, String end, Integer status,int page, int size){
        return salesPlanService.findForShipping(clientName,productName,empName,start,end,status, page, size);
    }
    /*
    根据销售计划id，查询客户
     */
    @RequestMapping(value = "/findProductById",method = RequestMethod.GET)
    public ResultObject<Product> findProductById(long salesPlanId){
        return salesPlanService.findClient(salesPlanId);
    }

    /*
    新建
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResultObject<SalesPlan> add(SalesPlan salesPlan){
        return salesPlanService.add(salesPlan);
    }
    /*
    修改
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public ResultObject<SalesPlan> update(SalesPlan salesPlan){
        return salesPlanService.update(salesPlan);
    }
    /*
    删除
     */
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public ResultObject<SalesPlan> delete(long id){
        return salesPlanService.delete(id);
    }
    /*
    撤销
     */
    @RequestMapping(value = "/annul",method = RequestMethod.GET)
    public ResultObject<SalesPlan> annul(long id){
        return salesPlanService.annul(id);
    }
    /*
    撤销
     */
    @RequestMapping(value = "/batchEdit",method = RequestMethod.GET)
    public ResultObject<SalesPlan> batchEdit(String ids,int important){
        return salesPlanService.batchEdit(ids,important);
    }

}
