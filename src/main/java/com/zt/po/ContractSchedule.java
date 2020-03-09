package com.zt.po;


import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="zt_contractschedule")
@EntityListeners(AuditingEntityListener.class)
@org.hibernate.annotations.Table(appliesTo = "zt_contractschedule",comment="合同进度")
public class ContractSchedule extends BasePo{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //合同ContractCode id
    private long contractCodeId;

    @ManyToOne
    private ContractCode contractCode;

    @Column(columnDefinition ="varchar(255)  COMMENT '备注'" )
    private String note;

    @ManyToOne
    private Employee  operator;

    //是否可用
    @Column(columnDefinition ="TINYINT(1)  COMMENT '是否可用'" )
    private boolean enabled;

    @Column(columnDefinition ="datetime COMMENT '操作时间'" )
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getContractCodeId() {
        return contractCodeId;
    }

    public void setContractCodeId(long contractCodeId) {
        this.contractCodeId = contractCodeId;
    }

    public ContractCode getContractCode() {
        return contractCode;
    }

    public void setContractCode(ContractCode contractCode) {
        this.contractCode = contractCode;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Employee getOperator() {
        return operator;
    }

    public void setOperator(Employee operator) {
        this.operator = operator;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
