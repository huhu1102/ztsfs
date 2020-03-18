package com.zt.controller;

import com.zt.model.ProductionPlanModel;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.ProductionPlan;
import com.zt.po.ProductionPlanDetails;
import com.zt.service.ProductionPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wl
 * @date 2019年5月6日
 */
@RestController
@RequestMapping("/productionplan")
public class ProductionPlanController {

    @Autowired
    ProductionPlanService productionPlanService;

    /*
    分页查询所有
     */
    @RequestMapping(value = "/findbypage", method = RequestMethod.GET)
    public ResultPage<ProductionPlan> findByPage(int page,
                                                 int size,
                                                 String productName,
                                                 String empName,
                                                 String endDate,
                                                 String startDate,
                                                 String clientName,
                                                 Integer contractStatus,
                                                 Integer status) {
        return productionPlanService.findByPage(page,size,productName, empName,endDate, startDate, status,contractStatus,clientName );
    }

    /**
     *   分页查询出 新的生产计划  即 生产计划状态为 status=1
     * @param page
     * @param size
     * @param productName
     * @param empName
     * @param endDate
     * @param startDate
     * @param clientName
     * @return
     */

    @RequestMapping(value = "/findByPlan", method = RequestMethod.GET)
    public ResultPage<ProductionPlanDetails> findByPage(int page,
                                                 int size,
                                                 String productName,
                                                 String empName,
                                                 String endDate,
                                                 String startDate,
                                                 String clientName
                                                 ) {
        return productionPlanService.findByNewPlan
                (page,size,  productName, empName,endDate, startDate,clientName );
    }

    /*
    页面初始化
     */
    @RequestMapping(value = "/basedata", method = RequestMethod.GET)
    public ResultObject<ProductionPlan> basedata() {
        return productionPlanService.basedata();
    }

    /**
      *新增生产计划
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultObject<ProductionPlan> addNew(ProductionPlanModel productionPlanModel) throws Exception {
        return productionPlanService.addNew(productionPlanModel);
    }

    /*
    修改
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultObject<ProductionPlan> updatePlan(long id, String notes) throws Exception {
        return productionPlanService.updatePlan(id, notes);
    }

    /*
     * 删除
     */
    @RequestMapping(value = "/deletmark", method = RequestMethod.GET)
    public ResultObject<ProductionPlan> deletDeli(long id) {
        return productionPlanService.deletDel(id);
    }

    /*
    根据客户Id查最新一次销售计划详情
     */
    @RequestMapping(value = "/getPlanDetails", method = RequestMethod.GET)
    public ResultObject<ProductionPlanDetails> findPlanDetails(long clientId) {
        return productionPlanService.findPlanDetails(clientId);
    }

    /*
    根据状态查询数量
     */
    @RequestMapping(value = "/findStatus",method = RequestMethod.GET)
    public ResultObject<ProductionPlan> findStatus(){
        return productionPlanService.findStatus();
    }


}
