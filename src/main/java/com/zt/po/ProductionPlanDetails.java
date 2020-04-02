package com.zt.po;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author wl
 * @date 2019年5月10日 
 * 生产计划详情
 */
@Entity
@Table(name="zt_productionplandetails")
@org.hibernate.annotations.Table(appliesTo = "zt_productionplandetails",comment="生产计划详情")
public class ProductionPlanDetails extends BasePo{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	//关联销售计划
	@ManyToOne
	private SalesPlan salesPlan ;

	@Column(columnDefinition ="bigint(20)COMMENT  '销售计划Id'" )
	private long salesPlanId;
	//本次计划实际要求数量
	@Column(columnDefinition ="float(20,4)COMMENT  '实际数量'" )
	private float actualQuantity;
    //该单销售计划对应的生产计划发货量  在发货记录完完成后 该数据变化 该数据变化时 对应更新时间变化
    @Column(columnDefinition ="float(20,4) default 0.00  COMMENT '发货量'" )
	private Float sendQuantity=0f;

    //发货记录
    private String shippingStr;

	/**
	添加合同的状态
	1.没有合同
	2.合同完结
	3.未完结
 */
	private Integer contractStatus;
	/*
	完成合同中的数量
	 */
	private double contractNo;
	//单号
	@Column(columnDefinition ="varchar(255)COMMENT  '单号'" )
	private String planNo;
	//操作者	
	@ManyToOne  //代表是一对多关联
	private Employee employee;
	//记录人Id
	private Long employeeId;
	//完成数量
	private float accomplishNO;
	//关联编码
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "produtionPlan" ,fetch =FetchType.LAZY )
	private List<ProductionPlanSerialNumber> serialNumbers;
	//备注
	@Column(columnDefinition ="varchar(255)COMMENT  '备注'" )
	private String note;
	@Column(columnDefinition ="varchar(255)COMMENT  '标记'" )
	private String mark;
	/**
	 *状态
	 * 1--新建
	 * 2--开始生产
	 * 3--生产完成
	 * 4--生产撤销
	 */
	@Column(columnDefinition ="int(2) COMMENT  '状态'" )
	private Integer status;
	/**
	 * 查看状态 是否被查看  查看后更新该记录的updateDate
	 * null 未查看
	 * 1---未查看
	 * 2---已查看
	 */
	@Column(columnDefinition ="int(2) COMMENT  '查看状态'" )
	private Integer checkStatus;

	//是否可用
	@Column(columnDefinition ="TINYINT(1)  COMMENT '是否可用'" )
	private boolean enabled;

	//创建时间
	@Column(columnDefinition ="datetime  COMMENT '创建时间'" )
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	/**
	 * *****************************************************************************
	 *  生产计划 状态标识
	 *
	 * */

	/**
	是否合并成订单状态
	 1  未合并
	 2.合并
	 */
	@Column(columnDefinition ="int(2) COMMENT  '是否合并成订单状态'" )
	private Integer manageStatus;

	//更新时间 该时间为可更新时间，在该计划状态变化时该时间变化前台显示以该时间排序
	@Column(columnDefinition ="datetime  COMMENT '更新时间'" )
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;

	//发货请求的时间
	@Column(columnDefinition = "datetime  COMMENT '发货请求时间'")
	@Temporal(TemporalType.TIMESTAMP)
	private Date sendDate;

	/**
	 * 96+
	 * 发货等级   该等级为生产计划下发的等级 该对象与生产
	 * 1--紧急
	 * 2--加急
	 * 3--备货
	 * 4--正常
	 * 5--特殊
	 */
	private Integer expectLevel;
	/**
	 * 最近一次请求发货数量
	 * */
	private float resendNo;
	//发货请求Id
	private Long resendId;
	/**
	 * 发货请求描述
	 * */
	@Column(columnDefinition = "varchar(255)COMMENT  '发货请求描述文字'")
	private String resendStr;
	/**
	 * *******************************************************************
	 *
	 */
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPlanNo() {
		return planNo;
	}

	public void setPlanNo(String planNo) {
		this.planNo = planNo;
	}

	public float getActualQuantity() {
		return actualQuantity;
	}

	public void setActualQuantity(float actualQuantity) {
		this.actualQuantity = actualQuantity;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String getShippingStr() {
		return shippingStr;
	}

	public void setShippingStr(String shippingStr) {
		this.shippingStr = shippingStr;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public void setSalesPlanId(long salesPlanId) {
		this.salesPlanId = salesPlanId;
	}

	public Integer getManageStatus() {
		return manageStatus;
	}

	public void setManageStatus(Integer manageStatus) {
		this.manageStatus = manageStatus;
	}

	public List<ProductionPlanSerialNumber> getSerialNumbers() {
		return serialNumbers;
	}

	public void setSerialNumbers(List<ProductionPlanSerialNumber> serialNumbers) {
		this.serialNumbers = serialNumbers;
	}
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public SalesPlan getSalesPlan() {
		return salesPlan;
	}

	public void setSalesPlan(SalesPlan salesPlan) {
		this.salesPlan = salesPlan;
	}

	public Long getSalesPlanId() {
		return salesPlanId;
	}

	public void setSalesPlanId(Long salesPlanId) {
		this.salesPlanId = salesPlanId;
	}

	public float getAccomplishNO() {
		return accomplishNO;
	}

	public void setAccomplishNO(float accomplishNO) {
		this.accomplishNO = accomplishNO;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public float getResendNo() {
		return resendNo;
	}

	public void setResendNo(float resendNo) {
		this.resendNo = resendNo;
	}

	public Long getResendId() {
		return resendId;
	}

	public void setResendId(Long resendId) {
		this.resendId = resendId;
	}

	public Integer getExpectLevel() {
		return expectLevel;
	}

	public void setExpectLevel(Integer expectLevel) {
		this.expectLevel = expectLevel;
	}

	public String getResendStr() {
		return resendStr;
	}

	public void setResendStr(String resendStr) {
		this.resendStr = resendStr;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Float getSendQuantity() {
		return sendQuantity;
	}

	public void setSendQuantity(Float sendQuantity) {
		this.sendQuantity = sendQuantity;
	}

	public Integer getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public Integer getContractStatus() {
		return contractStatus;
	}

	public void setContractStatus(Integer contractStatus) {
		this.contractStatus = contractStatus;
	}

	public double getContractNo() {
		return contractNo;
	}

	public void setContractNo(double contractNo) {
		this.contractNo = contractNo;
	}
}
