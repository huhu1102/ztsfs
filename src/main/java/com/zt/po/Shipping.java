/**
 * 
 */
package com.zt.po;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

/**
 * @author yh
 * @date 2019年5月27日
 */
 //出库管理
@Entity
@Table(name="zt_shipping")//
@org.hibernate.annotations.Table(appliesTo = "zt_shipping",comment="出库管理")
public class Shipping extends BasePo{
	private static final long serialVersionUID = 1L;
	//
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	//材料名称
	@Column(columnDefinition ="varchar(255)  COMMENT '材料名称.'" )
	private  String name;
	//计量单位
	@Column(columnDefinition ="varchar(255)  COMMENT '计量单位.'" )
	private  String  unit;
	//出库材料类型   原材料   半成品  成品
	@Column(columnDefinition ="varchar(255)  COMMENT '出库材料类型.'" )
	private  String  materialType;
	//数量
	@Column(columnDefinition ="float(10,4) COMMENT '数量.'" )
	private float  amount;
	//备注	
	@Column(columnDefinition ="varchar(255)  COMMENT '备注.'" )
	private   String  notes;

	//出库时间
	@Column(columnDefinition="datetime  COMMENT'出库时间'")
	 @Temporal(TemporalType.TIMESTAMP)
	private Date retrieval;	
	
	
	//操作人
	@Column(columnDefinition ="varchar(255)  COMMENT '操作者.'" )
	private String operator;
	//是否可用
	@JsonIgnore
	@Column(columnDefinition ="TINYINT(1)  COMMENT '是否可用'" )
	private boolean enable;

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id){
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
	 * @return the unit
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * @param unit the unit to set
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}



	

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
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
	 * @return the retrieval
	 */
	public Date getRetrieval() {
		return retrieval;
	}

	/**
	 * @param retrieval the retrieval to set
	 */
	public void setRetrieval(Date retrieval) {
		this.retrieval = retrieval;
	}

	/**
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * @param operator the operator to set
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getMaterialType() {
		return materialType;
	}

	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	
		
}
