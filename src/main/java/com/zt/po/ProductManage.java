package com.zt.po;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author wl
 * @date 2019年4月26日 
 * 生产管理
 */
@Entity
@Table(name="zt_productmanage")
@org.hibernate.annotations.Table(appliesTo = "zt_productmanage",comment="生产管理")
public class ProductManage extends BasePo{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	/*
	 * 关联生产计划详情
	 * 型号
	 * 数量
	 */
	@ManyToOne
	private ProductionPlanDetails planDetails;
	//备注
	@Column(columnDefinition ="varchar(255)  COMMENT '备注'" )
	private String note;
	//关联员工
	@ManyToOne
	private Employee manger;
	//开票人
	private Long managerId;

	//编号
	private String number;

	//创建时间
	@Column(columnDefinition ="datetime  COMMENT '创建时间'" )
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	//是否可用
	@Column(columnDefinition ="TINYINT(1)  COMMENT '是否可用'" )
	private boolean enabled;

	/**未开始生产-1
	生产中-2
	生产完成-3
	撤销生产-4
	*/
	@Column(columnDefinition ="varchar(255)  COMMENT '生产管理状态'" )
	private Integer mangeStatus;

	//关联生产管理详情
	@OneToMany(mappedBy = "productManage", cascade =CascadeType.ALL)
	private List<ProductManageDetails> details;
	//生产管理进度详情汇总
	@OneToMany(mappedBy = "productManage", cascade =CascadeType.ALL)
	private List<ProductManageProgress> progress;
	//产品所选用工序Id；
	private Long progressId;

	/**
	 *
	 * @return
	 */
	@ManyToMany(mappedBy="manageList" )
	private List<ShippingBill> billList;

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Employee getManger() {
		return manger;
	}

	public void setManger(Employee manger) {
		this.manger = manger;
	}

	public Long getManagerId() {
		return managerId;
	}

	public void setManagerId(Long managerId) {
		this.managerId = managerId;
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

	public Integer getMangeStatus() {
		return mangeStatus;
	}

	public void setMangeStatus(Integer mangeStatus) {
		this.mangeStatus = mangeStatus;
	}

	public List<ProductManageDetails> getDetails() {
		return details;
	}

	public void setDetails(List<ProductManageDetails> details) {
		this.details = details;
	}

	public List<ProductManageProgress> getProgress() {
		return progress;
	}

	public void setProgress(List<ProductManageProgress> progress) {
		this.progress = progress;
	}

	public ProductionPlanDetails getPlanDetails() {
		return planDetails;
	}

	public void setPlanDetails(ProductionPlanDetails planDetails) {
		this.planDetails = planDetails;
	}

	public Long getProgressId() {
		return progressId;
	}

	public void setProgressId(Long progressId) {
		this.progressId = progressId;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public List<ShippingBill> getBillList() {
		return billList;
	}

	public void setBillList(List<ShippingBill> billList) {
		this.billList = billList;
	}

	@Override
	public String toString() {
		return "ProductManage{" +
				"id=" + id +
				", note='" + note + '\'' +
				", managerId=" + managerId +
				", number='" + number + '\'' +
				", createDate=" + createDate +
				", enabled=" + enabled +
				", mangeStatus=" + mangeStatus +
				", progressId=" + progressId +
				", billList=" + billList +
				'}';
	}
}
