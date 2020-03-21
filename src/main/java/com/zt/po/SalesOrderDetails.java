package com.zt.po;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wl
 * @date 2019年5月9日 
 * 销售订单详情
		*/
@Entity
@Table(name = "zt_salesorderdetails")
public class SalesOrderDetails extends BasePo{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long Id;
	
	//物资编码
	@Column(columnDefinition ="varchar(255)  COMMENT '物资编码.'" )
	private String resourcesNumber;
	
	//产品数量
	@Column(columnDefinition ="float(20,4)  COMMENT  '产品数量'" )
	private double productNo;
	
	//不含税单价
	@Column(columnDefinition ="varchar(255)COMMENT  '不含税单价'" )
	private BigDecimal unitPrice;
	
	//不含税总价
	@Column(columnDefinition ="varchar(255)COMMENT  '不含税总价'" )
	private BigDecimal totalMoney;
	
	//交货期限
	@Column(columnDefinition ="date COMMENT '交货期限'" )
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;

	//关联生产计划详情
//	@ManyToMany(cascade = CascadeType.ALL)
//	@JoinTable(name="zt_salesorderdetails_productionplandetails",joinColumns={@JoinColumn(name="s_id")}
//			,inverseJoinColumns={@JoinColumn(name="p_id")})
	@ManyToOne
	private ProductionPlanDetails productionPlanDetails;

	private  long productDetailsId;

	//交货地点
	@Column(columnDefinition ="varchar(255)COMMENT  '交货地点'" )
	private String address;
	
	//备注
	@Column(columnDefinition ="varchar(255)  COMMENT '备注.'" )
	private String remarks;
	
	//关联订单
	@JsonIgnore
	@ManyToOne
	private SalesOrder salesOrder;

	/**
	 * 状态
	 * 1.新建
	 * 2.完成
	 * 3.撤销
	 */
	private Integer detailsStatus;

	//是否可用
	@Column(columnDefinition ="TINYINT(1)  COMMENT '是否可用'" )
	private boolean enabled;
	
	//创建时间
	@Column(columnDefinition ="datetime COMMENT '创建时间'" )
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
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

	public String getResourcesNumber() {
		return resourcesNumber;
	}

	public void setResourcesNumber(String resourcesNumber) {
		this.resourcesNumber = resourcesNumber;
	}

	public double getProductNo() {
		return productNo;
	}

	public void setProductNo(double productNo) {
		this.productNo = productNo;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public SalesOrder getSalesOrder() {
		return salesOrder;
	}

	public void setSalesOrder(SalesOrder salesOrder) {
		this.salesOrder = salesOrder;
	}

	public Integer getDetailsStatus() {
		return detailsStatus;
	}

	public void setDetailsStatus(Integer detailsStatus) {
		this.detailsStatus = detailsStatus;
	}

	public ProductionPlanDetails getProductionPlanDetails() {
		return productionPlanDetails;
	}

	public void setProductionPlanDetails(ProductionPlanDetails productionPlanDetails) {
		this.productionPlanDetails = productionPlanDetails;
	}

	public long getProductDetailsId() {
		return productDetailsId;
	}

	public void setProductDetailsId(long productDetailsId) {
		this.productDetailsId = productDetailsId;
	}
}
