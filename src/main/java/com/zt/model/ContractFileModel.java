package com.zt.model;

import com.zt.po.BasePo;
import com.zt.po.ContractFile;

import java.util.Date;

public class ContractFileModel extends BasePo {

    private static final long serialVersionUID = 1L;

    //合同档案Id
    private long contractFileId;

    //客户Id
    private long clientId;

    //合同编号
    private String contractSerialNo;

    //签订时间
    private  Date signDate;

    //份数
    private int amount;

    //合同摘要
    private String contractDescription;

    //合同相关
    private String contractNote;

    //质保金
    private String  retentionMoney;

    //到期时间
    private Date endDate;
    //状态
    private Integer  status;

    //附件名字
    private String uploadName;
    //附件url
    private String imageUrl;

    public long getContractFileId() {
        return contractFileId;
    }

    public void setContractFileId(long contractFileId) {
        this.contractFileId = contractFileId;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public String getContractSerialNo() {
        return contractSerialNo;
    }

    public void setContractSerialNo(String contractSerialNo) {
        this.contractSerialNo = contractSerialNo;
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getContractDescription() {
        return contractDescription;
    }

    public void setContractDescription(String contractDescription) {
        this.contractDescription = contractDescription;
    }

    public String getContractNote() {
        return contractNote;
    }

    public void setContractNote(String contractNote) {
        this.contractNote = contractNote;
    }

    public String getRetentionMoney() {
        return retentionMoney;
    }

    public void setRetentionMoney(String retentionMoney) {
        this.retentionMoney = retentionMoney;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public ContractFile v2p(ContractFileModel model){
        ContractFile con = new ContractFile();
        if(model.getContractFileId()!=0&&model.getClientId()!=0){
            con.setId(model.getContractFileId());
            con.setClientId(model.getClientId());
        }
        con.setContractSerialNo(model.getContractSerialNo());
        con.setSignDate(model.getSignDate());
        con.setAmount(model.getAmount());
        con.setContractDescription(model.getContractDescription());
        con.setContractNote(model.getContractNote());
        con.setRetentionMoney(model.getRetentionMoney());
        con.setEndDate(model.getEndDate());
        con.setStatus(model.getStatus());
        con.setUploadName(model.getUploadName());
        con.setImageUrl(model.getImageUrl());
        return con;
    }
}
