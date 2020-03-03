/**
 * 
 */
package com.zt.po;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

/**
 * @author yh--whl
 * @date 2019年5月27日
    *      原材料入库管理
 */
@Entity
@Table(name="zt_rowmaterialinrecieving")//
@org.hibernate.annotations.Table(appliesTo = "zt_rowmaterialinrecieving",comment="原材料入库管理")
public class RowMaterialInRecieving extends BasePo{
	private static final long serialVersionUID = 1L;
	//
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	//入库时间
	@Column(columnDefinition="datetime  COMMENT'入库时间'")
	 @Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	//入库材料类型   原材料   半成品  成品   同原材料对应起来
	//材料名称
	@Column(columnDefinition ="varchar(255)  COMMENT '材料名称.'" )
	private  String name;
	//计量单位
	@Column(columnDefinition ="varchar(255)  COMMENT '计量单位.'" )
	private  String  unitName;

	private Long unitId;
	//关联原料表
	@ManyToOne
	@JoinColumn(name="rowmatersId")
	private RowMaterial materail;
	//原料Id；
	@Column(columnDefinition ="bigint(20)  COMMENT '原料Id'" )
	private Long materailId;
	//数量 出库为负，入库为正；
	@Column(columnDefinition ="float(12,4)  COMMENT '数量.'" )
	private float  quantity;
	
	//撤销状态 1--未撤销  2--撤销
	private Integer verifyStatus;

	//是否可用
	@JsonIgnore
	@Column(columnDefinition ="TINYINT(1)  COMMENT '是否可用'" )
	private boolean enable;
	
	//备注
	@Column(columnDefinition ="varchar(255)  COMMENT '备注.'" )
	private   String  notes;
	
	//实际入库者(操作人)确认入库人；
	@ManyToOne
	private Employee operator;

     //	操作人姓名 
	@Column(columnDefinition ="varchar(255)  COMMENT '操作人ID.'" )
	private String operatorName;
      //	操作人Id
	@Column(columnDefinition ="bigint(20)  COMMENT '操作人ID.'" )
	private Long operatorId;


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
	 * @return the materail
	 */
	public RowMaterial getMaterail() {
		return materail;
	}
	/**
	 * @param materail the materail to set
	 */
	public void setMaterail(RowMaterial materail) {
		this.materail = materail;
	}
	
	 
	/**
	 * @return the quantity
	 */
	public float getQuantity() {
		return quantity;
	}
	/**
	 * @return the enable
	 */
	public boolean isEnable() {
		return enable;
	}
	/**
	 * @param enable the enable to set
	 */
	public void setEnable(boolean enable) {
		this.enable = enable;
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
	 * @return the operator
	 */
	public Employee getOperator() {
		return operator;
	}
	/**
	 * @param operator the operator to set
	 */
	public void setOperator(Employee operator) {
		this.operator = operator;
	}
	/**
	 * @return the operatorName
	 */
	public String getOperatorName() {
		return operatorName;
	}
	/**
	 * @param operatorName the operatorName to set
	 */
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	/**
	 * @return the operatorId
	 */
	public Long getOperatorId() {
		return operatorId;
	}
	/**
	 * @param operatorId the operatorId to set
	 */
	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}
	/**
	 * @return the materailId
	 */
	public Long getMaterailId() {
		return materailId;
	}
	/**
	 * @param materailId the materailId to set
	 */
	public void setMaterailId(Long materailId) {
		this.materailId = materailId;
	}
	
	public Integer getVerifyStatus() {
		return verifyStatus;
	}
	public void setVerifyStatus(Integer verifyStatus) {
		this.verifyStatus = verifyStatus;
	}
	
		
}
