package com.zt.po;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

/**
 * @author wl
 * @date 2019年4月26日 
 * 发货回执单
 */
@Entity
@Table(name= "zt_shippingreceipt")
public class ShippingReceipt extends BasePo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	//货运部名称
	@Column(columnDefinition ="varchar(255)  COMMENT '货运部名称'" )
	private String deName;
	//货运部电话
	@Column(columnDefinition ="varchar(255)  COMMENT '货运部电话'" )
	private String dePhone;
	//实际发货时间
	@Column(columnDefinition ="datetime  COMMENT '发货时间'" )
	@Temporal(TemporalType.TIMESTAMP)
	private Date faDate;
	
	//发货地址
	@Column(columnDefinition ="varchar(255)  COMMENT '发货地址'" )
	private String destination;
	
	/*
	 * 关联员工表
	 * 1.发货人
	 * 2.电话
	 */
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "employeeid")
	private Employee employee;
	
	//预计到达时间
	@Column(columnDefinition ="datetime  COMMENT '预计到达时间'" )
	@Temporal(TemporalType.TIMESTAMP)
	private Date daoDate;
	
	//收货人
	@Column(columnDefinition ="varchar(255)  COMMENT '收货人'" )
	private String shName;
	
	//收货人电话
	@Column(columnDefinition ="varchar(255)  COMMENT '收货人电话'" )
	private String shPhone;
	
	//收货方式
	@Column(columnDefinition ="varchar(255)  COMMENT '收货方式'" )
	private String shWay;
	
	//备注
	@Column(columnDefinition ="varchar(255)COMMENT  '备注'" )
	private String remarks;
	
	//是否可用
	@Column(columnDefinition ="TINYINT(1)  COMMENT '是否可用'" )
	private boolean enabled;
	
	//创建时间
	@Column(columnDefinition ="date  COMMENT '创建时间'" )
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDeName() {
		return deName;
	}

	public void setDeName(String deName) {
		this.deName = deName;
	}

	public Date getFaDate() {
		return faDate;
	}

	public void setFaDate(Date faDate) {
		this.faDate = faDate;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Date getDaoDate() {
		return daoDate;
	}

	public void setDaoDate(Date daoDate) {
		this.daoDate = daoDate;
	}

	public String getShName() {
		return shName;
	}

	public void setShName(String shName) {
		this.shName = shName;
	}

	public String getShPhone() {
		return shPhone;
	}

	public void setShPhone(String shPhone) {
		this.shPhone = shPhone;
	}

	public String getShWay() {
		return shWay;
	}

	public void setShWay(String shWay) {
		this.shWay = shWay;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

	public String getDePhone() {
		return dePhone;
	}

	public void setDePhone(String dePhone) {
		this.dePhone = dePhone;
	}

	
	
}
