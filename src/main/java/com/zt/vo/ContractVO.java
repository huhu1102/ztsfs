package com.zt.vo;

import com.zt.po.ContractSchedule;
import com.zt.po.Employee;
import com.zt.po.SalesOrder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class ContractVO {
    private static final long serialVersionUID = 1L;
    //合同主键
    private long id;
    //合同序号
    private String sequence;
    //合同编号
    private String contractNumber;
    //客户名称
    private String clientName;
    //合同金额
    private BigDecimal totalMoney;
    //签订时间
    private Date signContractDate;
    //质保金 e.g 5%
    private float qualityDeposit;
    //质保金状态
    private Integer qualityDepositStatus;
    //合同进度
    private List<ContractSchedule> contractScheduleList;
    //合同内容
    private List<SalesOrder> orderList;
}
