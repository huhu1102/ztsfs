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
    *     半成品出 入库管理（未关联到入库单 纯粹为生产管理产生的入库单）
 */
@Entity
@Table(name="zt_midprogressrecieving")//
@org.hibernate.annotations.Table(appliesTo = "zt_midprogressrecieving",comment="生产过程的周转表")
public class MidProgressRecieving extends BasePo{
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

	//销售计划Id
	private long salesPlanId;

	//生产计划详情
	private long productionPlanId;

	//工序步骤code
	private String productProcessCode;

	//数量
	@Column(columnDefinition ="double  COMMENT '数量.'" )
	private float  quantity;
	//自建还是来源于采购单
	//是否可用
	@JsonIgnore
	@Column(columnDefinition ="TINYINT(1)  COMMENT '是否可用'" )
	private boolean enable;
	//备注
	@Column(columnDefinition ="varchar(255)  COMMENT '备注.'" )
	private   String  notes;
	//操作人
	@ManyToOne
	private Employee operator;
      //	操作人Id
	@Column(columnDefinition ="bigint(20)  COMMENT '操作人ID.'" )
	private Long operatorId;
	@Column(columnDefinition ="bigint(20)  COMMENT '生产管理详情ID.'" )
	private long productManageDetailsId;
	
	//入库时间
	@Column(columnDefinition="datetime  COMMENT'入库时间'")
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

	public long getSalesPlanId() {
		return salesPlanId;
	}

	public void setSalesPlanId(long salesPlanId) {
		this.salesPlanId = salesPlanId;
	}

	public long getProductionPlanId() {
		return productionPlanId;
	}

	public void setProductionPlanId(long productionPlanId) {
		this.productionPlanId = productionPlanId;
	}

	public String getProductProcessCode() {
		return productProcessCode;
	}

	public void setProductProcessCode(String productProcessCode) {
		this.productProcessCode = productProcessCode;
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

	public float getQuantity() {
		return quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
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

	public long getProductManageDetailsId() {
		return productManageDetailsId;
	}

	public void setProductManageDetailsId(long productManageDetailsId) {
		this.productManageDetailsId = productManageDetailsId;
	}
}
