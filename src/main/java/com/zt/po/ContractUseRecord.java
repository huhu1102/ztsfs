package com.zt.po;

import javax.persistence.*;
import java.util.Date;

/**
 * 合同归档记录(也就是谁用了,用了多久的记录表)
 */
@Entity
@Table(name="zt_contractuserecord")
@Inheritance(strategy = InheritanceType.JOINED)
@org.hibernate.annotations.Table(appliesTo = "zt_contractuserecord",comment="合同归档记录")
public class ContractUseRecord extends BasePo{
    private static final long serialVersionUID = 7134708623983128710L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //借阅人
    @ManyToOne
    @JoinColumn(name="documentUser")
    private Employee documentUser;

    //借阅时间
    @Column(columnDefinition ="datetime COMMENT '借阅时间'" )
    @Temporal(TemporalType.TIMESTAMP)
    private Date useDate;

    //交回人
    @ManyToOne
    @JoinColumn(name="returnedUser")
    private Employee  returnedUser;

    //交回时间
    @Column(columnDefinition ="datetime COMMENT '交回时间'" )
    @Temporal(TemporalType.TIMESTAMP)
    private Date returnedDate;

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

    public Employee getDocumentUser() {
        return documentUser;
    }

    public void setDocumentUser(Employee documentUser) {
        this.documentUser = documentUser;
    }

    public Date getUseDate() {
        return useDate;
    }

    public void setUseDate(Date useDate) {
        this.useDate = useDate;
    }

    public Employee getReturnedUser() {
        return returnedUser;
    }

    public void setReturnedUser(Employee returnedUser) {
        this.returnedUser = returnedUser;
    }

    public Date getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(Date returnedDate) {
        this.returnedDate = returnedDate;
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
