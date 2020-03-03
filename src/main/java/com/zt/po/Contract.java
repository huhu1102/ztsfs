package com.zt.po;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="zt_contract")
@Inheritance(strategy = InheritanceType.JOINED)
@org.hibernate.annotations.Table(appliesTo = "zt_contract",comment="合同")
public class Contract extends BasePo{

    private static final long serialVersionUID = 7134708623983128710L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //合同名称
    @Column(columnDefinition ="varchar(255)  COMMENT '合同名称.'" )
    private String contractName;

    //合同编号
    @Column(columnDefinition ="varchar(255)  COMMENT '合同编号.'" )
    private String contractNumber;

    //负责人
    @ManyToOne
    @JoinColumn(name="employeeId")
    private Employee employee;

    //负责人
    @Column(columnDefinition ="bigint(20)  COMMENT '负责人.'" )
    private long empId;
    //客户
    @ManyToOne
    @JoinColumn(name="clienteleid")
    private Client cliente;
    //客户Id
    @Column(columnDefinition ="bigint(20)  COMMENT '客户Id.'" )
    private long cliId;
    //前台展示使用；
    private String clientArr;
    //质保金 e.g 5%
    private String qualityDeposit;

    //增值税 e.g 16%
    private String tax;

    //合同类型
    @Column(columnDefinition ="varchar(255)  COMMENT '合同类型.'" )
    private String type;

    //总金额
    @Column(columnDefinition ="decimal(19)  COMMENT '总金额.'" )
    private BigDecimal totalMoney;

    //签约时间
    @Column(columnDefinition ="datetime COMMENT '签约时间'" )
    @Temporal(TemporalType.TIMESTAMP)
    private Date signContractDate;

    //合同履约期限
    //开始时间
    @Column(columnDefinition ="datetime COMMENT '开始时间'" )
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    //截止时间
    @Column(columnDefinition ="datetime COMMENT '截止时间'" )
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    //合同状态
    /**
     * 1.新建
     * 2.生效
     * 3.废弃
     */
    private int contractStatus;
    //备注
    private String notes;
    //是否可用
    @Column(columnDefinition ="TINYINT(1)  COMMENT '是否可用'" )
    private boolean enabled;
    //创建时间
    @Column(columnDefinition ="datetime COMMENT '创建时间'" )
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    //附件名字
    private String uploadName;
    //附件url
    private String imageUrl;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public Client getCliente() {
        return cliente;
    }

    public void setCliente(Client cliente) {
        this.cliente = cliente;
    }

    public long getCliId() {
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

    public int getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(int contractStatus) {
        this.contractStatus = contractStatus;
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

    public void setEmpId(long empId) {
        this.empId = empId;
    }

    public void setCliId(long cliId) {
        this.cliId = cliId;
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
}
