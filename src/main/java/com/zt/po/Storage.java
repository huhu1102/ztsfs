/**
 * 
 */
package com.zt.po;

import javax.persistence.*;
import java.util.Date;


/**
 * @author yh
 * @date 2019年5月7日
 */
//出入库确认单
@Entity
@Table(name="zt_storage")//
@org.hibernate.annotations.Table(appliesTo = "zt_storage",comment="出入库确认单")
public class Storage extends BasePo{
	private static final long serialVersionUID = 1L;
	//
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	//入库时间
	@Column(columnDefinition="datetime  COMMENT'入库时间'")
	 @Temporal(TemporalType.DATE)
	private Date receiveDate;
	
	//入库数量
	@Column(columnDefinition ="float(10,4) COMMENT '入库数量'" )
	private float counts;
	
	//入库规格
	
	private  String unitName;
	
	//操作者
	@Column(columnDefinition ="varchar(255)  COMMENT '操作者.'" )
	private String operator;
	
	//材料名称
	@Column(columnDefinition ="varchar(255)  COMMENT '材料名称.'" )
	private String designation;
	
	@Column(columnDefinition ="TINYINT(1)  COMMENT '是否可见'" )
	private boolean enabled;
	//创建时间
	@Column(columnDefinition ="datetime  COMMENT '创建时间'" )
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
	 * @return the receiveDate
	 */
	public Date getReceiveDate() {
		return receiveDate;
	}

	/**
	 * @param receiveDate the receiveDate to set
	 */
	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	/**
	 * @return the counts
	 */
	public float getCounts() {
		return counts;
	}

	/**
	 * @param counts the counts to set
	 */
	public void setCounts(float counts) {
		this.counts = counts;
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

	/**
	 * @return the designation
	 */
	public String getDesignation() {
		return designation;
	}

	/**
	 * @param designation the designation to set
	 */
	public void setDesignation(String designation) {
		this.designation = designation;
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
	

}
