package com.zt.model;

import com.zt.po.BasePo;
import com.zt.po.ProductionPlan;

import java.util.Date;

public class ProductionPlanModel extends BasePo {

    private static final long serialVersionUID = 1L;

    private long id;
    //单号
    private String productionPlanNo;
    //销售计划详情
    private String productionPlanDetails;
    private String notes;
    //时间
    private Date createDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductionPlanNo() {
        return productionPlanNo;
    }

    public void setProductionPlanNo(String productionPlanNo) {
        this.productionPlanNo = productionPlanNo;
    }

    public String getProductionPlanDetails() {
        return productionPlanDetails;
    }

    public void setProductionPlanDetails(String productionPlanDetails) {
        this.productionPlanDetails = productionPlanDetails;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public ProductionPlan v1p(ProductionPlanModel mo){
        ProductionPlan marketingPlan = new ProductionPlan();
        if (mo.getId()!=0){
            marketingPlan.setId(mo.getId());
        }
        marketingPlan.setEnabled(true);
        marketingPlan.setCreateDate(mo.getCreateDate());
        marketingPlan.setPlanNo(mo.getProductionPlanNo());
        marketingPlan.setNotes (mo.getNotes());
        return marketingPlan;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
