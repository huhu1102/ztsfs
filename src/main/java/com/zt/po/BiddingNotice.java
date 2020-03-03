package com.zt.po;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

/**
 * @author yh
 * @date 2019年5月7日
 * 招标公告
 */
@Entity
@Table(name = "zt_biddingnotice ")//
@org.hibernate.annotations.Table(appliesTo = "zt_biddingnotice", comment = "招标公告")
public class BiddingNotice extends BasePo {
    private static final long serialVersionUID = 1L;
    //标书id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    //招标编号
    @Column(columnDefinition = "varchar(255)  COMMENT '招标编号.'")
    private String tenderReference;
    //项目名称
    @Column(columnDefinition = "varchar(255)  COMMENT '项目名称.'")
    private String name;


    //客户Id
    @Column(columnDefinition = "bigint(20)  COMMENT '客户Id.'")
    private Long clientId;

    //关联客户
    @ManyToOne
    private Client client;


    //上传者Id
    @Column(columnDefinition = "bigint(20)  COMMENT '上传者Id.'")
    private Long employeeId;

    //关联员工
    @ManyToOne
    private Employee employee;

    //报名截止日期
    @Column(columnDefinition = "datetime  COMMENT '报名截止日期'")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    // 报名开始日期
    @Column(columnDefinition = "datetime  COMMENT '报名开始日期'")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    //开标时间
    @Column(columnDefinition = "datetime  COMMENT '开标时间'")
    @Temporal(TemporalType.TIMESTAMP)
    private Date openingDate;

    //开标地点
    @Column(columnDefinition = "varchar(255)  COMMENT '开标地点.'")
    private String address;

    //公告状态
    @Column(columnDefinition = "varchar(255)  COMMENT '公告状态.'")
    private String status;

    //备注
    @Column(columnDefinition = "varchar(255)  COMMENT '备注.'")
    private String note;
    //招标内容
    @Column(columnDefinition = "varchar(255)  COMMENT '招标内容.'")
    private String content;

    //是否可用
    @JsonIgnore
    @Column(columnDefinition = "TINYINT(1)  COMMENT '是否可见'")
    private boolean enabled;
    //创建时间
    @Column(columnDefinition = "datetime  COMMENT '创建时间'")
    @Temporal(TemporalType.DATE)
    private Date createDate;

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the tenderReference
     */
    public String getTenderReference() {
        return tenderReference;
    }

    /**
     * @param tenderReference the tenderReference to set
     */
    public void setTenderReference(String tenderReference) {
        this.tenderReference = tenderReference;
    }

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


    /**
     * @return the enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return the createDate
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Date getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(Date openingDate) {
        this.openingDate = openingDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
