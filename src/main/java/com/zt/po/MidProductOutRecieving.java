
package com.zt.po;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

/**
 * @author whl
 * @date 2019年6月22日
    *     半成品出库管理
 */
@Entity
@Table(name="zt_midproductoutrecieving")//
@org.hibernate.annotations.Table(appliesTo = "zt_midproductoutrecieving",comment="半成品出库管理")
public class MidProductOutRecieving extends BasePo{
	private static final long serialVersionUID = 1L;
	//
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	//入库材料类型   原材料   半成品  成品   同原材料对应起来
	//材料名称
	@Column(columnDefinition ="varchar(255)  COMMENT '材料名称.'" )
	private  String name;
	//计量单位
	@Column(columnDefinition ="varchar(255)  COMMENT '计量单位.'" )
	private  String  unitName;

	private Long unitId;
	//半成品库
	@ManyToOne
	@JoinColumn(name="midproductId")
	private MiddleProduct midproduct;

	//生产管理详情的Id
	private long productManageDetailsId;
	private Long midProcutId;
	//数量
	@Column(columnDefinition ="float(12,0)  COMMENT '数量.'" )
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
	
	//实际出库者；
	@ManyToOne
	private Employee operator;

     //	操作人姓名 
	@Column(columnDefinition ="varchar(255)  COMMENT '操作人ID.'" )
	private String operatorName;
      //操作人Id
	@Column(columnDefinition ="bigint(20)  COMMENT '操作人ID.'" )
	private Long operatorId;

	//出库时间
	@Column(columnDefinition="datetime  COMMENT'出库时间'")
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
	public float getQuantity() {
		return quantity;
	}
	
	/**
	 * @return the midproduct
	 */
	public MiddleProduct getMidproduct() {
		return midproduct;
	}
	/**
	 * @param midproduct the midproduct to set
	 */
	public void setMidproduct(MiddleProduct midproduct) {
		this.midproduct = midproduct;
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


	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}
	public Integer getVerifyStatus() {
		return verifyStatus;
	}
	public void setVerifyStatus(Integer verifyStatus) {
		this.verifyStatus = verifyStatus;
	}
	public Long getMidProcutId() {
		return midProcutId;
	}
	public void setMidProcutId(Long midProcutId) {
		this.midProcutId = midProcutId;
	}
	
		
}
