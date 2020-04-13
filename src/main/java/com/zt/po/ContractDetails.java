package com.zt.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wl
 * @date 2020年4月13日
 * 合同详情
 */
@Entity
@Table(name = "zt_contractdetails")
@Data
public class ContractDetails extends BasePo{
    private static final long serialVersionUID = 1L;
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    //物资编码
    @Column(columnDefinition ="varchar(255)  COMMENT '物资编码.'" )
    private String resourcesNumber;

    //产品数量
    @Column(columnDefinition ="float(20,4)  COMMENT  '产品数量'" )
    private double productNo;

    //不含税单价
    @Column(columnDefinition ="varchar(255)COMMENT  '不含税单价'" )
    private BigDecimal unitPrice;

    //不含税总价
    @Column(columnDefinition ="varchar(255)COMMENT  '不含税总价'" )
    private BigDecimal totalMoney;

    //交货期限
    @Column(columnDefinition ="date COMMENT '交货期限'" )
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    //交货地点
    @Column(columnDefinition ="varchar(255)COMMENT  '交货地点'" )
    private String address;

    //备注
    @Column(columnDefinition ="varchar(255)  COMMENT '备注.'" )
    private String remarks;

    //关联合同
    @JsonIgnore
    @ManyToOne
    private Contract contract;

    //是否可用
    @Column(columnDefinition ="TINYINT(1)  COMMENT '是否可用'" )
    private boolean enabled;

    //创建时间
    @Column(columnDefinition ="datetime COMMENT '创建时间'" )
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;


}
