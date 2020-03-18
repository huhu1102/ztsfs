package com.zt.model;

import com.zt.po.BasePo;

import java.util.Date;

public class ContractUseRecordModel extends BasePo {
    private static final long serialVersionUID = 1L;
    //合同进度Id
    private long contractScheduleId;
    //员工Id
    private long empId;
    //备注
    private String note;
    //合同ContractCode id
    private long contractCodeId;
    //状态
    private Integer state;
    //创建时间
    private Date createDate;

}
