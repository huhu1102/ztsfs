/**
 * 
 */
package com.zt.po;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author yh
 * @date 2019年4月24日
 */
//采购计划
@Entity
@Table(name="zt_rowmaterialpurchaseplan")//
@org.hibernate.annotations.Table(appliesTo = "zt_rowmaterialpurchaseplan",comment="原材料采购计划")
public class RowMaterialPurchasePlan extends BasePo{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//采购序列号
	private long id;
	
	/**
	 *    采购单名称
	 *    采购单名称    为  生成的采购单名称 == 物料名称+购买数量+单位+购买单下单时间戳
	 */
	@Column(columnDefinition ="varchar(255) COMMENT '名称'" )
	private String planName;

	/**
	 * 1---已确认
	 * 2,--未确认
	 */
	@Column(columnDefinition ="varchar(255) COMMENT '是否确认'" )
	private  Integer confirmStatus;
	/**
	 * 
	 */
	@ManyToOne
	private  Client client;
	@Column(columnDefinition ="bigint(20) COMMENT '客户Id'" )
	private Long clientId;
	@Column(columnDefinition ="varchar(255) COMMENT '客户名称'" )
	private String clientName;
	//备注
	private String notes;
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "plan")
	private List<RowMaterialPurchasePlanDetail>  detail;
	
	//采购计划单编号
	@Column(columnDefinition ="varchar(255) COMMENT '采购计划单编号'" )
	private String  serialNumber;
	@Column(columnDefinition ="TINYINT(1)  COMMENT '是否可见'" )
	private boolean enabled;

	//下单时间
	@Column(columnDefinition ="datetime  COMMENT '创建时间'" )
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
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
	 * @return the confirmStatus
	 */
	public Integer getConfirmStatus() {
		return confirmStatus;
	}
	/**
	 * @param confirmStatus the confirmStatus to set
	 */
	public void setConfirmStatus(Integer confirmStatus) {
		this.confirmStatus = confirmStatus;
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
	 * @return the serialNumber
	 */
	public String getSerialNumber() {
		return serialNumber;
	}
	/**
	 * @param serialNumber the serialNumber to set
	 */
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
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

	
	public List<RowMaterialPurchasePlanDetail> getDetail() {
		return detail;
	}
	public void setDetail(List<RowMaterialPurchasePlanDetail> detail) {
		this.detail = detail;
	}
	/**
	 * @return the planName
	 */
	public String getPlanName() {
		return planName;
	}
	/**
	 * @param planName the planName to set
	 */
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	@Override
	public String toString() {
		return "PurchasePlan [id=" + id 
				+ ", confirmStatus=" + confirmStatus + ", notes=" + notes + ", serialNumber=" + serialNumber
				+ ", enabled=" + enabled + ", createDate=" + createDate + "]";
	}
	/**
	 * @return the client
	 */
	public Client getClient() {
		return client;
	}
	/**
	 * @param client the client to set
	 */
	public void setClient(Client client) {
		this.client = client;
	}
	/**
	 * @return the clientId
	 */
	public Long getClientId() {
		return clientId;
	}
	/**
	 * @param clientId the clientId to set
	 */
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	/**
	 * @return the clientName
	 */
	public String getClientName() {
		return clientName;
	}
	/**
	 * @param clientName the clientName to set
	 */	
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	/**
	 * 
	 */
	public RowMaterialPurchasePlan() {
		super();
		this.detail=new ArrayList<RowMaterialPurchasePlanDetail>();
	}
	public RowMaterialPurchasePlan(long id, String planName, Integer materialType, Integer confirmStatus, Client client,
			Long clientId, String clientName, String notes, List<RowMaterialPurchasePlanDetail> detail, String serialNumber,
			boolean enabled, Date createDate) {
		super();
		this.id = id;
		this.planName = planName;
		this.confirmStatus = confirmStatus;
		this.client = client;
		this.clientId = clientId;
		this.clientName = clientName;
		this.notes = notes;
		this.detail = detail;
		this.serialNumber = serialNumber;
		this.enabled = enabled;
		this.createDate = createDate;
	}
	
	
    
	
}
