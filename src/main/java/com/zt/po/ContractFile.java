package com.zt.po;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="zt_contractfile")
@Inheritance(strategy = InheritanceType.JOINED)
@org.hibernate.annotations.Table(appliesTo = "zt_contractfile",comment="合同归档记录")
public class ContractFile extends BasePo{
    private static final long serialVersionUID = 7134708623983128710L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    //序号
    private Integer  contractNo;
    //客户
    @ManyToOne
    @JoinColumn(name="clienteleid")
    private Client client;
    //合同编号
    @Column(columnDefinition ="varchar(255)  COMMENT '合同编号.'" )
    private String contractSerialNo;

    //签订时间
    @Column(columnDefinition ="datetime COMMENT '签订时间'" )
    @Temporal(TemporalType.TIMESTAMP)
    private Date signDate;

    //份数
    @Column(columnDefinition ="int(2)  COMMENT '合同编号.'" )
    private int amount;

    //合同摘要
    @Column(columnDefinition ="varchar(255)  COMMENT '合同摘要.'" )
    private String contractDescription;

    //合同相关
    @Column(columnDefinition ="varchar(255)  COMMENT '合同相关.'" )
    private String contractNote;

    //质保金
    @Column(columnDefinition ="varchar(255)  COMMENT '质保金.'" )
    private String  retentionMoney;

    //到期时间
    @Column(columnDefinition ="datetime COMMENT '到期时间'" )
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    /**
     * 合同状态
     * 1.有
     * 2.无
     * 3.有部分
     */
    private Integer  state;

    //是否可用
    @Column(columnDefinition ="TINYINT(1)  COMMENT '是否可用'" )
    private boolean  enabled;

    //创建时间
    @Column(columnDefinition ="datetime COMMENT '创建时间'" )
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getContractNo() {
        return contractNo;
    }

    public void setContractNo(Integer contractNo) {
        this.contractNo = contractNo;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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
}
