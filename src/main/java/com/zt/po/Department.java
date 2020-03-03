/**  
* 
*/  
 
package com.zt.po;

import javax.persistence.*;
import java.util.Date;

/**
 * @author whl
 * @date 2019年4月15日 
   *  部门类
 */
@Entity
@Table(name="zt_department")
@org.hibernate.annotations.Table(appliesTo = "zt_department",comment="部门")
public class Department extends BasePo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long Id;
	/**
	 * 部门名称
	 */
	@Column(columnDefinition ="varchar(255)  COMMENT '是否可用'" )
	private String name;
	/**
	 * 是否可用
	 */
	@Column(columnDefinition ="TINYINT(1)  COMMENT '是否可用'" )
	private boolean enable;
	/**
	 * 部门描述
	 */
	@Column(columnDefinition ="varchar(255)  COMMENT '部门描述.'" )
	private String describes;
	/**
	 * 部门编号
	*/
	@Column(columnDefinition ="varchar(255)  COMMENT '部门编号.'" )
	private String deNumber;
	
	/**
	 * 创建时间
	 */
	@Column(columnDefinition ="datetime  COMMENT '创建时间'" )
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	/**
	 * @return the id
	 */
	public long getId() {
		return Id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		Id = id;
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
	 * @return the describe
	 */

	/**
	 * @return the deNumber
	 */
	public String getDeNumber() {
		return deNumber;
	}
	/**
	 * @return the describes
	 */
	public String getDescribes() {
		return describes;
	}
	/**
	 * @param describes the describes to set
	 */
	public void setDescribes(String describes) {
		this.describes = describes;
	}
	/**
	 * @param deNumber the deNumber to set
	 */
	public void setDeNumber(String deNumber) {
		this.deNumber = deNumber;
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
