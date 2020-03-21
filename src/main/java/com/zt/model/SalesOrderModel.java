package com.zt.model;

import com.zt.po.BasePo;
import com.zt.po.SalesOrder;

import java.math.BigDecimal;

/**
 * @author wl
 * @date 2019年6月15日
 * 合同模板
 */
public class SalesOrderModel extends BasePo {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    //订单Id
    private long id;
    //合同名称
    private String contractName;

    //展示使用
    private String clientArr;
    //负责人Id
    private Long empId;

    //附件名字
    private String uploadName;
    //附件url
    private String imageUrl;
    private Boolean hasContract;

    private  BigDecimal totalMoney;
    //客户Id
    private Long cliId;
    //税收
    private  Float  tax;
    //备注
    private String notes;
    //关联订单详情
    private String orderDetails;


    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public Long getCliId() {
        return cliId;
    }


    public String getUploadName() {
        return uploadName;
    }

    public void setUploadName(String uploadName) {
        this.uploadName = uploadName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setCliId(Long cliId) {
        this.cliId = cliId;
    }

    public String getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(String orderDetails) {
        this.orderDetails = orderDetails;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public String getClientArr() {
        return clientArr;
    }

    public void setClientArr(String clientArr) {
        this.clientArr = clientArr;
    }


    public Boolean getHasContract() {
        return hasContract;
    }

    public void setHasContract(Boolean hasContract) {
        this.hasContract = hasContract;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Float getTax() {
        return tax;
    }

    public void setTax(Float tax) {
        this.tax = tax;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public SalesOrder v2p(SalesOrderModel mo) {
        SalesOrder c = new SalesOrder();
        if (mo.getId() != 0) {
            c.setId(mo.getId());
        }
        c.setTotalMoney(mo.getTotalMoney());
        c.setCliId(mo.getCliId());
        c.setEmpId(mo.getEmpId());
        c.setContractName(mo.getContractName());
        c.setClientArr(mo.getClientArr());
        c.setTax(mo.getTax());
        if(null!=mo.getContractName()){
            c.setContractName(mo.getContractName());
        }
//		c.setCon
//        c.setCreateDate(new Date());
        c.setNotes(mo.getNotes());
        c.setHasContract(mo.getHasContract());
        c.setEnabled(true);
        return c;
    }

}
