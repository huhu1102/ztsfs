package com.zt.model;

import com.zt.po.BasePo;
import com.zt.po.ContractSchedule;

public class ContractScheduleModel extends BasePo{

    private static final long serialVersionUID = 1L;
    //合同进度Id
    private long contractScheduleId;
    //员工Id
    private long operatorId;
    //备注
    private String note;
    //合同ContractCode id
    private long contractCodeId;
    //状态
    private Integer statue;

    public long getContractScheduleId() {
        return contractScheduleId;
    }

    public void setContractScheduleId(long contractScheduleId) {
        this.contractScheduleId = contractScheduleId;
    }

    public long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(long operatorId) {
        this.operatorId = operatorId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public long getContractCodeId() {
        return contractCodeId;
    }

    public void setContractCodeId(long contractCodeId) {
        this.contractCodeId = contractCodeId;
    }

    public Integer getStatue() {
        return statue;
    }

    public void setStatue(Integer statue) {
        this.statue = statue;
    }

    public ContractSchedule v2p(ContractScheduleModel model){
        ContractSchedule con = new ContractSchedule();
        if(model.getContractScheduleId()!=0){
            con.setId(model.getContractScheduleId());
        }
        con.setContractCodeId(model.getContractCodeId());
        con.setNote(model.getNote());
        con.setStatue(model.getStatue());
        con.setOperatorId(model.getOperatorId());
        return con;
    }

}
