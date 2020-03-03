/**
 * 
 */
package com.zt.po;

import javax.persistence.*;
import java.util.Date;

/**
 * @author yh
 * @date 2019年4月20日
 * 半成品
 */
@Entity
@Table(name="zt_middleproduct")
@org.hibernate.annotations.Table(appliesTo = "zt_middleproduct",comment="半成品信息")
public class MiddleProduct extends BasePo{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	//名称（名称唯一， 不可重复；）
	@Column(columnDefinition ="varchar(255)  COMMENT '半成品名称.'" )
	private String name;
	
	//规格 长度尺寸
	@Column(columnDefinition ="varchar(255)  COMMENT '规格.'" )
	private String standard;
	//数量
	@Column(columnDefinition ="float(12,4)  COMMENT '数量.'" )
	private float  productNo;

	@Column(columnDefinition ="varchar(255)  COMMENT '计量单位.'" )
	private String unitName;
	@Column(columnDefinition ="bigint(20)  COMMENT '计量单位Id.'" )
	private Long unitId;
	@ManyToOne
	private SysUnit unit;
	
	//半成品种类  1--常备，2 客户定制的//
	private Integer productStyle;
	/**
	 * 所属车间
	 * 1--注塑
	 * 2--装配
	 * 3--激光
	 */
	private String plant;

	/**
	 *  仓库数量状态状态
	 *     在低于一定数量后该状态改变，
	 *
	 */
	@Column(columnDefinition ="float(12,4)  COMMENT '采购计划基准值.'" )
	private float storeStatue;
	//备注
	private String note;
	//创建时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	
	//是否有新的采购计划下达
	@Column(columnDefinition ="TINYINT(1)  COMMENT '是否有采购计划下达'" )
	private boolean buyStatus;
	
	
	
	/**
	 * 是否删除；
	 */
	@Column(columnDefinition ="TINYINT(1)  COMMENT '是否可见'" )
	private boolean enabled;
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
	 * @return the standard
	 */
	public String getStandard() {
		return standard;
	}
	/**
	 * @param standard the standard to set
	 */
	public void setStandard(String standard) {
		this.standard = standard;
	}
	/**
	 * @return the productNo
	 */
	public float getProductNo() {
		return productNo;
	}
	/**
	 * @param productNo the productNo to set
	 */
	public void setProductNo(float productNo) {
		this.productNo = productNo;
	}

	/**
	 * @return the productStyle
	 */
	public Integer getProductStyle() {
		return productStyle;
	}
	/**
	 * @param productStyle the productStyle to set
	 */
	public void setProductStyle(Integer productStyle) {
		this.productStyle = productStyle;
	}
	/**
	 * @return the plant
	 */
	public String getPlant() {
		return plant;
	}
	/**
	 * @param plant the plant to set
	 */
	public void setPlant(String plant) {
		this.plant = plant;
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
	 * @return the unit
	 */
	public SysUnit getUnit() {
		return unit;
	}
	/**
	 * @param unit the unit to set
	 */
	public void setUnit(SysUnit unit) {
		this.unit = unit;
	}
	public boolean isBuyStatus() {
		return buyStatus;
	}
	public void setBuyStatus(boolean buyStatus) {
		this.buyStatus = buyStatus;
	}


	public float getStoreStatue() {
		return storeStatue;
	}

	public void setStoreStatue(float storeStatue) {
		this.storeStatue = storeStatue;
	}
}
