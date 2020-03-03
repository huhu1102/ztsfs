package com.zt.po;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

/**
 * @author wl
 * @date 2019年4月26日 
 * 发货请求详情
 */
@Entity
@Table(name = "zt_shippingrequestdetails")
@org.hibernate.annotations.Table(appliesTo = "zt_shippingrequestdetails",comment="发货请求详情")
public class  ShippingRequestDetails extends BasePo{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	//销售计划Id
    private long salePlanId;

	//请求发货数量
	@Column(columnDefinition ="float(21,4) COMMENT  '请求发货数量'" )
	private float planNumber;
	//目的地
	@Column(columnDefinition ="varchar(255)  COMMENT '发货目的地'" )
	private String destination;
	//是否可用
	@Column(columnDefinition ="TINYINT(1)  COMMENT '是否可用'" )
	private boolean enabled;
	//收货方式
	@Column(columnDefinition ="varchar(255)  COMMENT '收货方式'" )
	private String receiveWay;
	//收货联系人
	@Column(columnDefinition ="varchar(255)  COMMENT '联系人'" )
    private String receiver;
	//收货人联系方式
	@Column(columnDefinition ="varchar(255)  COMMENT '收货人联系方式'" )
	private String relation;

    //发送的是第几次
    @Column(columnDefinition ="varchar(255)  COMMENT '发送的是第几次'" )
    private Integer sendTimes;

    //备注
	@Column(columnDefinition ="varchar(255)COMMENT  '备注'" )
	private String note;
	/**
	 发货状态
	 1.请求发起
	 2.发货确认
	 3.收货确认
     4.完成
     5.撤销
	 */
	@Column(columnDefinition ="int(255)COMMENT  '状态'" )
	private Integer deliveryStatus;
	/**
	 请求状态
	 1.普通请求
	 2.紧急
	 3.加急
	 */
	@Column(columnDefinition ="int(255)COMMENT  '请求等级'" )
	private Integer expectLevel;

	//创建记录时间
	@Column(columnDefinition ="datetime COMMENT '创建时间'" )
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	public float getPlanNumber() {
		return planNumber;
	}

	public void setPlanNumber(float planNumber) {
		this.planNumber = planNumber;
	}

	public String getReceiveWay() {
		return receiveWay;
	}

	public void setReceiveWay(String receiveWay) {
		this.receiveWay = receiveWay;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(Integer deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}


	public Integer getExpectLevel() {
		return expectLevel;
	}

	public void setExpectLevel(Integer expectLevel) {
		this.expectLevel = expectLevel;
	}

    public long getSalePlanId() {
        return salePlanId;
    }

    public void setSalePlanId(long salePlanId) {
        this.salePlanId = salePlanId;
    }

    public Integer getSendTimes() {
        return sendTimes;
    }

    public void setSendTimes(Integer sendTimes) {
        this.sendTimes = sendTimes;
    }


}
