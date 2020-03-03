
package com.zt.po;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

/**
 * @author whl
 * @date 2019年6月17日
 */
//采购计划
@Entity
@Table(name="zt_rowmaterialpurchaseplandetail")//
@org.hibernate.annotations.Table(appliesTo = "zt_rowmaterialpurchaseplandetail",comment="采购计划详情")
public class RowMaterialPurchasePlanDetail extends BasePo{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//采购序列号
	private long id;
	
	//名称采购物料名称
	@Column(columnDefinition ="varchar(255) COMMENT '名称'" )
	private String name;
	//规格
	@Column(columnDefinition ="varchar(255) COMMENT '规格'" )
	private  String specifications;
	//数量
	@Column(columnDefinition ="float(10,4) COMMENT '数量'" )
	private float  quantity;
	/**
	 * 1,--未确认
	 * 2---已确认
	 */
	@Column(columnDefinition ="varchar(255) COMMENT '是否确认'" )
	private  Integer confirmStatus;
	@ManyToOne
	@JoinColumn(name="unitsId")
	private  SysUnit units;
	//单位Id               
	private  Long unitId;	 
	//备注
	private String notes;
	private String unitName;
	//原料Id
	private Long  materialId;
	
	//原料
	@ManyToOne
	private RowMaterial rowMaterial;
	
	@JsonIgnore
	@ManyToOne
	private RowMaterialPurchasePlan plan;
	
	
	
	private boolean enabled;
	
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the specifications
	 */
	public String getSpecifications() {
		return specifications;
	}
	/**
	 * @param specifications the specifications to set
	 */
	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}
	/**
	 * @return the quantity
	 */
	public float getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}

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
	 * @return the units
	 */
	public SysUnit getUnits() {
		return units;
	}
	/**
	 * @param units the units to set
	 */
	public void setUnits(SysUnit units) {
		this.units = units;
	}
	/**
	 * @return the unitId
	 */
	public Long getUnitId() {
		return unitId;
	}
	/**
	 * @param unitId the unitId to set
	 */
	public void setUnitId(Long unitId) {
		this.unitId = unitId;
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
	@Override
	public String toString() {
		return "PurchasePlanDetail [id=" + id + ", name=" + name + ", specifications=" + specifications + ", quantity="
				+ quantity + ", confirmStatus=" + confirmStatus + ", unitId=" + unitId + ", notes=" + notes
				+ ", createDate=" + createDate + "]";
	}
	
	public RowMaterialPurchasePlan getPlan() {
		return plan;
	}
	public void setPlan(RowMaterialPurchasePlan plan) {
		this.plan = plan;
	}
	/**
	 * @return the unitName
	 */
	public String getUnitName() {
		return unitName;
	}
	/**
	 * @param unitName the unitName to set
	 */
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	/**
	 * @return the materialId
	 */
	public Long getMaterialId() {
		return materialId;
	}
	/**
	 * @param materialId the materialId to set
	 */
	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}
	public RowMaterial getRowMaterial() {
		return rowMaterial;
	}
	public void setRowMaterial(RowMaterial rowMaterial) {
		this.rowMaterial = rowMaterial;
	}

}
