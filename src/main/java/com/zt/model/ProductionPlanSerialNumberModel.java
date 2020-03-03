package com.zt.model;

import com.zt.po.BasePo;
import com.zt.po.ProductionPlanSerialNumber;

import java.util.Date;

public class ProductionPlanSerialNumberModel extends BasePo {

    private static final long serialVersionUID = 1L;
    private long id;
    /**
     * 起始编号
     */
    private String startNo;
    /**
     * 结束编号
     */
    private String endNo;
    private String notes;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStartNo() {
        return startNo;
    }

    public void setStartNo(String startNo) {
        this.startNo = startNo;
    }

    public String getEndNo() {
        return endNo;
    }

    public void setEndNo(String endNo) {
        this.endNo = endNo;
    }


    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public ProductionPlanSerialNumber v2p(ProductionPlanSerialNumberModel  mo){
        ProductionPlanSerialNumber productionPlanSerialNumber = new ProductionPlanSerialNumber();
        if(mo.getId()>0){
            productionPlanSerialNumber.setId(mo.getId());
        }
        productionPlanSerialNumber.setEnabled(true);
        productionPlanSerialNumber.setStartNo(mo.getStartNo());
        productionPlanSerialNumber.setEndNo(mo.getEndNo());
        return productionPlanSerialNumber;
    }
}
