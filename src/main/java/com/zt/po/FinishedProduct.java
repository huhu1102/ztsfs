package com.zt.po;

import javax.persistence.*;
import java.util.Date;

/**
 * @author wl
 * @date 2019年4月24日 
 * 成品库
 */
@Entity
@Table(name="zt_finishedProduct")
public class FinishedProduct extends BasePo{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	//备注
	@Column(columnDefinition ="varchar(255)COMMENT  '备注'" )
	private String note;
	//产品Id
	@Column(columnDefinition ="bigint(20)COMMENT  '产品ID'" )
	private Long productId;
	//产品
	@ManyToOne
	private Product product;
	//销售计划id
//    @Column(columnDefinition ="bigint(20)COMMENT  '客户ID'" )
//    private Long salesPlanId;
//    //销售计划
//    @ManyToOne
//    private SalesPlan salesPlan;
//	生产计划id
    @Column(columnDefinition ="bigint(20)COMMENT  '客户ID'" )
    private Long productionPlanDetailsId;
    //生产计划 详情
    @ManyToOne
    private ProductionPlanDetails productionPlanDetails;
    //客户
	@ManyToOne
	private Client client;
	//数量
	@Column(columnDefinition ="bigint(20)COMMENT  '客户ID'" )
	private Long clientId;
	//数量
	@Column(columnDefinition ="float(10,4)COMMENT  '成品数量'" )
	private float proNumber;
	//是否可用
	@Column(columnDefinition ="TINYINT(1)  COMMENT '是否可用'" )
	private boolean enabled;
	//创建时间
	@Column(columnDefinition ="datetime  COMMENT '创建时间'" )
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public float getProNumber() {
		return proNumber;
	}

	public void setProNumber(float proNumber) {
		this.proNumber = proNumber;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * @return the productId
	 */
	public Long getProductId() {
		return productId;
	}
	/**
	 * @param productId the productId to set
	 */
	public void setProductId(Long productId) {
		this.productId = productId;
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

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}


	public ProductionPlanDetails getProductionPlanDetails() {
		return productionPlanDetails;
	}

	public void setProductionPlanDetails(ProductionPlanDetails productionPlanDetails) {
		this.productionPlanDetails = productionPlanDetails;
	}

	public Long getProductionPlanDetailsId() {
		return productionPlanDetailsId;
	}

	public void setProductionPlanDetailsId(Long productionPlanDetailsId) {
		this.productionPlanDetailsId = productionPlanDetailsId;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	//	public Long getSalesPlanId() {
//		return salesPlanId;
//	}
//
//	public void setSalesPlanId(Long salesPlanId) {
//		this.salesPlanId = salesPlanId;
//	}
//
//	public SalesPlan getSalesPlan() {
//		return salesPlan;
//	}
//
//	public void setSalesPlan(SalesPlan salesPlan) {
//		this.salesPlan = salesPlan;
//	}


}
