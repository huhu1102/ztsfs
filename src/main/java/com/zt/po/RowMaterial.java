package com.zt.po;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;


/**
 * @author yh
 * @date 2019年4月20日
 */
//原料基本信息的录入
//原料
@Entity
@Table(name="zt_rowmaterial")
@org.hibernate.annotations.Table(appliesTo = "zt_rowmaterial",comment="原材料信息")
public class RowMaterial extends BasePo{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	//名称
	@Column(columnDefinition ="varchar(255)  COMMENT '名称.'" )
	private String   materialName;
	//计量单位
	@Column(columnDefinition ="varchar(255)  COMMENT '计量单位.'" )
	private String unitName;
	@Column(columnDefinition ="bigint(20)  COMMENT '计量单位Id.'" )
	private Long unitId;
	@ManyToOne
	private SysUnit unit;
	//规格
	@Column(columnDefinition ="varchar(255)  COMMENT '规格.'" )
	private String specs;
	//类型   单纯消耗品   可加工成其他半成品
	@Column(columnDefinition ="varchar(255)  COMMENT '材料类型.'" )
	private String  moldType;
	/**
	   *  仓库数量状态状态 
	   *     在低于一定数量后该状态改变， 
	 *   
	 */
   	@Column(columnDefinition ="float(12,4)  COMMENT '采购计划基准值.'" )
	private float storeStatue;
	//到货日期
	//private Date delivery;
	//数量
//	@NotBlank(message="数量不能为空")
	@Column(columnDefinition ="float(10,4)  COMMENT '数量.'" )
	private float quantity;
//	@NotBlank(message="名称不能为空")
	@Column(columnDefinition ="TINYINT(1)  COMMENT '是否可见'" )
	private boolean enabled;
	//创建时间
	@Column(columnDefinition ="datetime  COMMENT '创建时间'" )
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	//是否有新的采购计划下达
	@Column(columnDefinition ="TINYINT(1)  COMMENT '是否有采购计划下达'" )
	private boolean buyStatus;
	//备注
	@Column(columnDefinition ="varchar(255)  COMMENT '备注.'" )
	private  String notes;
	/**
	 * @return the id
	 */
	public String getMaterialName() {
		return materialName;
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
	 * @param materialName the materialName to set
	 */
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
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
	/**
	 * @return the supplier
	 */
	public boolean isEnabled() {
		return enabled;
	}
	/**
	 * @return the materialQuantity
	 */
	
	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
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
	/**
	 * @return the specs
	 */
	public String getSpecs() {
		return specs;
	}
	/**
	 * @param specs the specs to set
	 */
	public void setSpecs(String specs) {
		this.specs = specs;
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
	 * @return the clientId
	 */
//	public Long getClientId() {
//		return clientId;
//	}
//	/**
//	 * @param clientId the clientId to set
//	 */
//	public void setClientId(Long clientId) {
//		this.clientId = clientId;
//	}
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
	 * @return the moldType
	 */
	public String getMoldType() {
		return moldType;
	}
	/**
	 * @param moldType the moldType to set
	 */
	public void setMoldType(String moldType) {
		this.moldType = moldType;
	}
	/**
	 * @return the remarks
	 */
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
	 * @param storeStatue the storeStatue to set
	 */
	public void setStoreStatue(Integer storeStatue) {
		this.storeStatue = storeStatue;
	}
	public SysUnit getUnit() {
		return unit;
	}
	/**
	 * @param unit the unit to set
	 */
	public void setUnit(SysUnit unit) {
		this.unit = unit;
	}
	/**
	 * @return the storeStatue
	 */
	public float getStoreStatue() {
		return storeStatue;
	}
	/**
	 * @param storeStatue the storeStatue to set
	 */
	public void setStoreStatue(float storeStatue) {
		this.storeStatue = storeStatue;
	}
	public boolean isBuyStatus() {
		return buyStatus;
	}
	public void setBuyStatus(boolean buyStatus) {
		this.buyStatus = buyStatus;
	}
	
}
