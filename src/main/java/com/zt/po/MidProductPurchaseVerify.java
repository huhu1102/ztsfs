package com.zt.po;

import javax.persistence.*;
import java.util.Date;

/**
 * @author wl
 * @date 2019年6月20日 
 * 原料采购确认单
 */
@Entity
@Table(name="zt_midproductpurchaseverify")
@org.hibernate.annotations.Table(appliesTo = "zt_midproductpurchaseverify",comment="半成品采购确认详情")
public class MidProductPurchaseVerify extends BasePo{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	//关联采购计划详情
	@ManyToOne
	private MidProductPurchasePlanDetail purchasePlanDetail;
	
	//采购计划单编号
	@Column(columnDefinition ="varchar(255) COMMENT '采购计划单编号'" )
	private String  serialNumber;
	
	//下单时间
	@Column(columnDefinition ="datetime  COMMENT '创建时间'" )
	@Temporal(TemporalType.TIMESTAMP)
	private Date planDate;
	
	//名称采购物料名称
	@Column(columnDefinition ="varchar(255) COMMENT '名称'" )
	private String name;
	//规格
	@Column(columnDefinition ="varchar(255) COMMENT '规格'" )
	private  String specifications;
	//数量
	@Column(columnDefinition ="float(10,4) COMMENT '采购计划数量'" )
	private float  quantity;
	
	//实收数量
	@Column(columnDefinition ="float(10,4) COMMENT '实收数量'" )
	private float  practicalQuantity;
	
	//撤销状态 1--未撤销  2--撤销
	private Integer verifyStatus;
	
	//单位Id        
	@Column(columnDefinition ="bigint(20) COMMENT '单位Id'" )
	private  Long unitId;	

	
	//备注
	@Column(columnDefinition ="varchar(255) COMMENT '备注'" )
	private String notes;
	
	//是否可见
	private boolean enabled;
	
	// 执行人
	@Column(columnDefinition ="bigint(20) COMMENT '执行人Id'" )
	private Long employeeId;
	@ManyToOne
	private Employee employee;
	
	//确认时间
	@Column(columnDefinition ="datetime  COMMENT '创建时间'" )
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getSpecifications() {
		return specifications;
	}


	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}


	public float getQuantity() {
		return quantity;
	}


	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}



	public Long getUnitId() {
		return unitId;
	}


	public void setUnitId(Long unitId) {
		this.unitId = unitId;
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




	public String getSerialNumber() {
		return serialNumber;
	}


	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
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


	public Date getPlanDate() {
		return planDate;
	}


	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}


	public float getPracticalQuantity() {
		return practicalQuantity;
	}


	public void setPracticalQuantity(float practicalQuantity) {
		this.practicalQuantity = practicalQuantity;
	}

	public MidProductPurchasePlanDetail getPurchasePlanDetail() {
		return purchasePlanDetail;
	}


	public void setPurchasePlanDetail(MidProductPurchasePlanDetail purchasePlanDetail) {
		this.purchasePlanDetail = purchasePlanDetail;
	}


	public Integer getVerifyStatus() {
		return verifyStatus;
	}


	public void setVerifyStatus(Integer verifyStatus) {
		this.verifyStatus = verifyStatus;
	}
	
	
}
