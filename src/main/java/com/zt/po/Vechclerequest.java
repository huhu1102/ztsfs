/**
 * 
 */
package com.zt.po;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author yh
 * @date 2019年4月16日
 */

//申请表
@Entity
@Table(name="zt_vechclerequest")
@org.hibernate.annotations.Table(appliesTo = "zt_vechclerequest",comment="申请表")
public class Vechclerequest extends BasePo{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	//申请人
	/**
	 * 申请人名字
	 */
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="empId")//解决多出的中间表
	private Employee  emp;
	/**
	 * 申请人名
	 */
	@Column(columnDefinition ="varchar(255)  COMMENT '申请人名.'" )
	private String  empName;
	/**
	 * 实际执行人
	 */
	@Column(columnDefinition ="varchar(255)  COMMENT '实际执行人.'" )
	private String operatorName;
	/**
	 * 实际执行人Id
	 */
	@Column(columnDefinition ="bigint(20)  COMMENT '实际执行人id'" )
	private Long operatorId;
	
	@ManyToOne
	@JoinColumn(name="vehicleInfoId")
	private VehicleInfo vehicleInfo;//外键  关联初级对象   可根据请求对象

	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "requestDetail")// 惰性加载,维护端在多的一方 不需要加载出来
	private List<VechclerequestDetails> details;
	//申请事由
	@Column(columnDefinition ="varchar(255)  COMMENT '申请事由.'" )
	private String business;
	//计划出行时间
	@Column(columnDefinition ="datetime  COMMENT '计划出行时间'" )
	@Temporal(TemporalType.TIMESTAMP)
	private Date preStartDate;
	//计划出行结束时间
	@Column(columnDefinition ="datetime  COMMENT '计划出行结束时间'" )
	@Temporal(TemporalType.TIMESTAMP)
	private Date preEndDate;
	@Column(columnDefinition ="varchar(255)  COMMENT '车牌'" )
	private String carNo;
	/**
	 * 申请时间 填表时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	//多久
//	@NotBlank(message="")
	@Column(columnDefinition ="varchar(255)  COMMENT '事情周期.'" )
	private String duration;
	/**
	 * 申请状态  未处理, 已分配,已完成;
	 *   UN_MANAGED
	 *   MANAGED
	 *   FINISHED
	 *   REFUSE//拒绝了~
	 */
	private String requestStatus;
	/**
	 * 是否删除；
	 */
	@Column(columnDefinition ="TINYINT(1)  COMMENT '是否可见'" )
	private boolean enabled;
	@Column(columnDefinition ="varchar(255)  COMMENT '备注.'" )
	private String  notes;
	/**
	 * @return the emp
	 */
	public Employee getEmp() {
		return emp;
	}
	/**
	 * @param emp the emp to set
	 */
	public void setEmp(Employee emp) {
		this.emp = emp;
	}
	/**
	 * @return the business
	 */
	public String getBusiness() {
		return business;
	}
	/**
	 * @param business the business to set
	 */
	public void setBusiness(String business) {
		this.business = business;
	}
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
	 * @return the vehicleInfo
	 */
	public VehicleInfo getVehicleInfo() {
		return vehicleInfo;
	}
	/**
	 * @param vehicleInfo the vehicleInfo to set
	 */
	public void setVehicleInfo(VehicleInfo vehicleInfo) {
		this.vehicleInfo = vehicleInfo;
	}
	/**
	 * @return the carNo
	 */
	public String getCarNo() {
		return carNo;
	}
	/**
	 * @param carNo the carNo to set
	 */
	public void setCarNo(String carNo) {
		this.carNo = carNo;
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
	/**
	 * @return the empName
	 */
	public String getEmpName() {
		return empName;
	}
	/**
	 * @param empName the empName to set
	 */
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	/**
	 * @return the preStartDate
	 */
	public Date getPreStartDate() {
		return preStartDate;
	}
	/**
	 * @param preStartDate the preStartDate to set
	 */
	public void setPreStartDate(Date preStartDate) {
		this.preStartDate = preStartDate;
	}
	/**
	 * @return the duration
	 */
	public String getDuration() {
		return duration;
	}
	/**
	 * @param duration the duration to set
	 */
	public void setDuration(String duration) {
		this.duration = duration;
	}
	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}
	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}
	/**
	 * @return the requestStatus
	 */
	public String getRequestStatus() {
		return requestStatus;
	}
	/**
	 * @param requestStatus the requestStatus to set
	 */
	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}
	/**
	 * @return the preEndDate
	 */
	public Date getPreEndDate() {
		return preEndDate;
	}
	/**
	 * @param preEndDate the preEndDate to set
	 */
	public void setPreEndDate(Date preEndDate) {
		this.preEndDate = preEndDate;
	}
	/**
	 * @return the details
	 */
	public List<VechclerequestDetails> getDetails() {
		return details;
	}
	/**
	 * @param details the details to set
	 */
	public void setDetails(List<VechclerequestDetails> details) {
		this.details = details;
	}
	/**
	 * @return the operatorName
	 */
	public String getOperatorName() {
		return operatorName;
	}
	/**
	 * @param operatorName the operatorName to set
	 */
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	/**
	 * @return the operatorId
	 */
	public Long getOperatorId() {
		return operatorId;
	}
	/**
	 * @param operatorId the operatorId to set
	 */
	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}
	@Override
	public String toString() {
		return "Vechclerequest [id=" + id + ", emp=" + emp + ", empName=" + empName + ", operatorName=" + operatorName
				+ ", operatorId=" + operatorId + ",  business=" + business
				+ ", preStartDate=" + preStartDate + ", preEndDate=" + preEndDate + ", carNo=" + carNo + ", createDate="
				+ createDate + ", duration=" + duration + ", requestStatus=" + requestStatus + ", enabled=" + enabled
				+ ", notes=" + notes + "]";
	}
	
	
	
	
}
