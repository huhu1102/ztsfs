package com.zt.model;

import com.zt.po.BasePo;
import com.zt.po.Contract;
import com.zt.po.SalesOrder;

import java.math.BigDecimal;
import java.util.Date;

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
    //合同编号
    private String contractNumber;
    //质保金
    private float qualityDeposit;
    //展示使用
    private String clientArr;
    //负责人Id
    private Long empId;

    //附件名字
    private String uploadName;
    //附件url
    private String imageUrl;
    private Boolean hasContract;
    //质保金
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

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public Long getCliId() {
        return cliId;
    }

    public float getQualityDeposit() {
        return qualityDeposit;
    }

    public void setQualityDeposit(float qualityDeposit) {
        this.qualityDeposit = qualityDeposit;
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
        SalesOrder s = new SalesOrder();
        Contract c = new Contract();
        if (mo.getId() != 0) {
            s.setId(mo.getId());
        }
        s.setTotalMoney(mo.getTotalMoney());
        s.setCliId(mo.getCliId());
        s.setEmpId(mo.getEmpId());
        s.setClientArr(mo.getClientArr());
        s.setTax(mo.getTax());
        s.setCreateDate(new Date());
        s.setNotes(mo.getNotes());
        s.setContractName(mo.getContractName());
        s.setTotalMoney(mo.getTotalMoney());
        if(mo.getHasContract()){
            //新建合同
            c.setContractNumber(mo.getContractNumber());
            c.setContractName(mo.getContractName());
            c.setQualityDeposit(mo.getQualityDeposit());

        }else{
            c.setContractNumber(mo.getContractNumber());
        }
        s.setHasContract(mo.getHasContract());
        s.setEnabled(true);
        return s;
    }

}
