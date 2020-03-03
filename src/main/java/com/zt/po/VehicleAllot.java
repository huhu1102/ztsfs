/**
 * 
 */
package com.zt.po;

import javax.persistence.*;
import java.util.Date;

/**
 * @author yh
 * @date 2019年4月28日
 *        车辆分配表
 */
@Entity
@Table(name="zt_vehiallot")//
@org.hibernate.annotations.Table(appliesTo = "zt_vehiallot",comment="车辆分配信息")
public class VehicleAllot extends BasePo{
	private static final long serialVersionUID = 1L;
	//车辆id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	/**
	 * 关联车牌号
	 */
	@Column(columnDefinition ="varchar(255)  COMMENT '车牌'" )
	private String carNo;
	/***
	 * 要分配的车
	 */
	@ManyToOne
	private VehicleInfo vech;
	
	/**
	 * 分配时间
	 */
	@Column(columnDefinition ="datetime  COMMENT '分配时间'" )
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	/**
	 * 申请时间
	 */
	@Column(columnDefinition ="datetime  COMMENT '申请时间'" )
	@Temporal(TemporalType.TIMESTAMP)
	private Date aplyDate;
	/**
	 * 是否删除；
	 */
	@Column(columnDefinition ="TINYINT(1)  COMMENT '是否可见'" )
	private boolean enabled;
	/**
	 * 备注
	 */
	@Column(columnDefinition ="varchar(255)  COMMENT '备注'" )
	private String note;
	/**
	 * 分配人
	 */
	@Column(columnDefinition ="bigint(10)  COMMENT '分配人ID'" )
	private Long assingedId;
	/**
	 * 分配状态 CREATE  已分配---1
	 *     OPERATION 执行中---2
	 *     CANCEL  申请人已取消---3
	 *     COMPLETE 已完成--4
	 */
	@Column(columnDefinition ="bigint(10)  COMMENT '分配人ID'" )
	private Integer assingeState;
	/**
	 *  关联申请表   获得这个id 可以得到申请单的部分信息
	 */	
	private long  carRequestId;
	/**
	 * @return the id
	 */
	
	//计划出行时间
	@Column(columnDefinition ="datetime  COMMENT '计划出行时间'" )
	@Temporal(TemporalType.TIMESTAMP)
	private Date preStartDate;
	//计划出行结束时间
	@Column(columnDefinition ="datetime  COMMENT '计划出行结束时间'" )
	@Temporal(TemporalType.TIMESTAMP)
	private Date preEndDate;
	/**
	 * 申请人名
	 */
	@Column(columnDefinition ="varchar(255)  COMMENT '申请人名.'" )
	private String  empName;
	@Column(columnDefinition ="bigint(20)  COMMENT '申请人Id.'" )
	private Long  requsetUserId;
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
	 * @return the vech
	 */
	public VehicleInfo getVech() {
		return vech;
	}
	/**
	 * @param vech the vech to set
	 */
	public void setVech(VehicleInfo vech) {
		this.vech = vech;
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
	 * @return the aplyDate
	 */
	public Date getAplyDate() {
		return aplyDate;
	}
	/**
	 * @param aplyDate the aplyDate to set
	 */
	public void setAplyDate(Date aplyDate) {
		this.aplyDate = aplyDate;
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
	 * @return the note
	 */
	public String getNote() {
		return note;
	}
	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}
	/**
	 * @return the carRequestId
	 */
	public long getCarRequestId() {
		return carRequestId;
	}
	/**
	 * @return the assingedId
	 */
	public Long getAssingedId() {
		return assingedId;
	}
	/**
	 * @param assingedId the assingedId to set
	 */
	public void setAssingedId(Long assingedId) {
		this.assingedId = assingedId;
	}
	/**
	 * @param carRequestId the carRequestId to set
	 */
	public void setCarRequestId(long carRequestId) {
		this.carRequestId = carRequestId;
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
	 * @return the requsetUserId
	 */
	public Long getRequsetUserId() {
		return requsetUserId;
	}
	/**
	 * @param requsetUserId the requsetUserId to set
	 */
	public void setRequsetUserId(Long requsetUserId) {
		this.requsetUserId = requsetUserId;
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

	/**
	 * @return the assingeState
	 */
	public Integer getAssingeState() {
		return assingeState;
	}
	/**
	 * @param assingeState the assingeState to set
	 */
	public void setAssingeState(Integer assingeState) {
		this.assingeState = assingeState;
	}
	@Override
	public String toString() {
		return "VehicleAllot [id=" + id + ", carNo=" + carNo + ", createDate=" + createDate + ", aplyDate=" + aplyDate
				+ ", enabled=" + enabled + ", note=" + note + ", assingedId=" + assingedId + ", assingeState="
				+ assingeState + ", carRequestId=" + carRequestId + ", preStartDate=" + preStartDate + ", preEndDate="
				+ preEndDate + ", empName=" + empName + ", requsetUserId=" + requsetUserId + ", operatorName="
				+ operatorName + ", operatorId=" + operatorId + "]";
	}
	
	
	
	
}
