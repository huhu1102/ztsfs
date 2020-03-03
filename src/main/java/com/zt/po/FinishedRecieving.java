/**
 * 
 */
package com.zt.po;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

/**
 * @author whl
 * @date 2019年6月22日
    *     成品出 入库管理
 */
@Entity
@Table(name="zt_finishedrecieving")//
@org.hibernate.annotations.Table(appliesTo = "zt_finishedrecieving",comment="成品出入库管理")
public class FinishedRecieving extends BasePo{
	private static final long serialVersionUID = 1L;
	//
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	//入库材料类型   原材料   半成品  成品   同原材料对应起来
	//材料名称
	@Column(columnDefinition ="varchar(255)  COMMENT '材料名称.'" )
	private  String name;
	//计量单位
	@Column(columnDefinition ="varchar(255)  COMMENT '计量单位.'" )
	private  String  unitName;

	private Long unitId;
	
	private Long finishProcutId;
	@ManyToOne
	private FinishedProduct finishedProduct;

	//生产管理详情的Id
	private long productManageDetailsId;
	//数量
	@Column(columnDefinition ="float(20,4)  COMMENT '数量.'" )
	private float  quantity;
	
	/**
     *   。  是出库还是入库  1--出库，2--入库   
 */

	private Integer storeType;
	
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
	
	//入库时间
	@Column(columnDefinition="datetime  COMMENT'入库时间'")
	@Temporal(TemporalType.DATE)
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

	public long getProductManageDetailsId() {
		return productManageDetailsId;
	}

	public void setProductManageDetailsId(long productManageDetailsId) {
		this.productManageDetailsId = productManageDetailsId;
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
	 * @return the quantity
	 */
	public double getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}
	/**
	 * @return the finishedProduct
	 */
	public FinishedProduct getFinishedProduct() {
		return finishedProduct;
	}
	public void setFinishedProduct(FinishedProduct finishedProduct) {
		this.finishedProduct = finishedProduct;
	}
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
	public Long getFinishProcutId() {
		return finishProcutId;
	}
	public void setFinishProcutId(Long finishProcutId) {
		this.finishProcutId = finishProcutId;
	}
	public Integer getStoreType() {
		return storeType;
	}
	public void setStoreType(Integer storeType) {
		this.storeType = storeType;
	}
	@Override
	public String toString() {
		return "FinishedRecieving [id=" + id + ", name=" + name + ", unitName=" + unitName + ", unitId=" + unitId
				+ ", finishProcutId=" + finishProcutId + ", quantity=" + quantity + ", storeType=" + storeType
				+ ", enable=" + enable + ", notes=" + notes + ", operatorName=" + operatorName + ", operatorId="
				+ operatorId + ", createDate=" + createDate + "]";
	}

	
		
}
