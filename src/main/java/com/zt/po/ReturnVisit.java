package com.zt.po;

import javax.persistence.*;
import java.util.Date;

/**
 * @author wl
 * @date 2019年5月7日 
 * 回访信息
 */
@Entity
@Table(name = "zt_returnvisit")
public class ReturnVisit extends BasePo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	//状态是否到货 1 未到 2 到达 3 已签收
	@Column(columnDefinition ="varchar(255)  COMMENT '状态.'" )
	private Integer billstatus;

	//回访方式
	private String visitType;

	//关联发货确认
	@ManyToOne
	private  ShippingBill shippingBill;
	/**
	 * 发货记录Id；
	 */
	private  Long billId;
	
	//操作人名称
	@Column(columnDefinition ="bigint(20)  COMMENT '操作人Id.'" )

	private Long  recorderId;
	//记录人
	@ManyToOne
	private Employee recorder;
	//操作时间
	@Column(columnDefinition ="datetime  COMMENT '创建时间'" )
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	//备注
	@Column(columnDefinition ="varchar(255)  COMMENT '备注.'")
	private String note;

	//是否可用
	@Column(columnDefinition ="TINYINT(1)  COMMENT '是否可用'" )
	private boolean enabled;

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

	public String getVisitType() {
		return visitType;
	}

	public void setVisitType(String visitType) {
		this.visitType = visitType;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getRecorderId() {
		return recorderId;
	}

	public void setRecorderId(Long recorderId) {
		this.recorderId = recorderId;
	}

	public Employee getRecorder() {
		return recorder;
	}

	public void setRecorder(Employee recorder) {
		this.recorder = recorder;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}


	public ShippingBill getShippingBill() {
		return shippingBill;
	}

	public void setShippingBill(ShippingBill shippingBill) {
		this.shippingBill = shippingBill;
	}

	public Long getBillId() {
		return billId;
	}

	public void setBillId(Long billId) {
		this.billId = billId;
	}

	public Integer getBillstatus() {
		return billstatus;
	}

	public void setBillstatus(Integer billstatus) {
		this.billstatus = billstatus;
	}
}
