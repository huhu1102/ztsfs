package com.zt.model;

import com.zt.po.BasePo;
import com.zt.po.Contract;

import java.math.BigDecimal;
import java.util.Date;

public class ContractModel extends BasePo {

    private static final long serialVersionUID = 1L;
    private long contractId;
    private String uploadIds;
    //合同名称
    private String contractName;
    //合同编号
    private String contractNumber;
    //负责人
    private Long empId;
    //客户Id
    private Long cliId;

    //附件名字
    private String uploadName;
    //附件url
    private String imageUrl;

    //前台展示使用；
    private String clientArr;
    //质保金 e.g 5%
    private String qualityDeposit;
    //增值税 e.g 16%
    private String tax;
    //合同类型
    private String type;
    //总金额
    private BigDecimal totalMoney;
    //签约时间
    private Date signContractDate;
    //合同履约期限
    //开始时间
    private Date startDate;
    //截止时间
    private Date endDate;
    //备注
    private String notes;
    //是否可用
    private boolean enabled;
    //创建时间
    private Date createDate;

    public long getContractId() {
        return contractId;
    }

    public void setContractId(long contractId) {
        this.contractId = contractId;
    }

    public String getUploadIds() {
        return uploadIds;
    }

    public void setUploadIds(String uploadIds) {
        this.uploadIds = uploadIds;
    }

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

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public Long getCliId() {
        return cliId;
    }

    public void setCliId(Long cliId) {
        this.cliId = cliId;
    }

    public String getClientArr() {
        return clientArr;
    }

    public void setClientArr(String clientArr) {
        this.clientArr = clientArr;
    }

    public String getQualityDeposit() {
        return qualityDeposit;
    }

    public void setQualityDeposit(String qualityDeposit) {
        this.qualityDeposit = qualityDeposit;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Date getSignContractDate() {
        return signContractDate;
    }

    public void setSignContractDate(Date signContractDate) {
        this.signContractDate = signContractDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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

    public Contract v2p(ContractModel mo){
        Contract c = new Contract();
        if(mo.getContractId()!=0){
            c.setId(mo.getContractId());
        }
        c.setImageUrl(mo.getImageUrl());
        c.setUploadName(mo.getUploadName());
        c.setCliId(mo.getCliId());
        c.setEmpId(mo.getEmpId());
        c.setSignContractDate(mo.getSignContractDate());
        c.setTotalMoney(mo.getTotalMoney());
        c.setStartDate(mo.getStartDate());
        c.setEndDate(mo.getEndDate());
        c.setType(mo.getType());
        c.setTax(mo.getTax());
        c.setQualityDeposit(mo.getQualityDeposit());
        c.setContractNumber(mo.getContractNumber());
        c.setClientArr(mo.getClientArr());
        c.setNotes(mo.getNotes());
        c.setCreateDate(mo.getCreateDate());
        return c;
    }
}
