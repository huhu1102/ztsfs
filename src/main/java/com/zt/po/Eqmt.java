/**
 * 
 */
package com.zt.po;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

/**
 * @author yh
 * @date 2019年4月17日
 */
//设备维护实体类
@Entity
@Table(name="zt_eqmt")
@org.hibernate.annotations.Table(appliesTo = "zt_eqmt",comment="设备维护实体类")
public class Eqmt extends BasePo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//维护记录序列号
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
   //维护时间
	@Column(columnDefinition ="datetime  COMMENT '维护时间'" )
	@Temporal(TemporalType.TIME)
	private Date maintenance;
	//维护人
	@Column(columnDefinition ="varchar(255)  COMMENT '维护人.'" )
	private String  repairman;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="defId")//解决多出的中间表
	private DeviceInfo def;
	//维护时长
	@Column(columnDefinition ="varchar(255)  COMMENT '维护时长.'" )
	private String maintenanceDuration;
	//备注
	@Column(columnDefinition ="varchar(255)  COMMENT '备注.'" )
	private String marks;
	//维护结果
	@Column(columnDefinition ="varchar(255)  COMMENT '维护结果.'" )
	private String repairResult;

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
	 * @return the maintenance
	 */
	public Date getMaintenance() {
		return maintenance;
	}
	/**
	 * @param maintenance the maintenance to set
	 */
	public void setMaintenance(Date maintenance) {
		this.maintenance = maintenance;
	}
	/**
	 * @return the repairman
	 */
	public String getRepairman() {
		return repairman;
	}
	/**
	 * @param repairman the repairman to set
	 */
	public void setRepairman(String repairman) {
		this.repairman = repairman;
	}
	/**
	 * @return the def
	 */
	public DeviceInfo getDef() {
		return def;
	}
	/**
	 * @param def the def to set
	 */
	public void setDef(DeviceInfo def) {
		this.def = def;
	}
	/**
	 * @return the maintenanceDuration
	 */
	public String getMaintenanceDuration() {
		return maintenanceDuration;
	}
	/**
	 * @param maintenanceDuration the maintenanceDuration to set
	 */
	public void setMaintenanceDuration(String maintenanceDuration) {
		this.maintenanceDuration = maintenanceDuration;
	}
	/**
	 * @return the marks
	 */
	public String getMarks() {
		return marks;
	}
	/**
	 * @param marks the marks to set
	 */
	public void setMarks(String marks) {
		this.marks = marks;
	}
	/**
	 * @return the repairResult
	 */
	public String getRepairResult() {
		return repairResult;
	}
	/**
	 * @param repairResult the repairResult to set
	 */
	public void setRepairResult(String repairResult) {
		this.repairResult = repairResult;
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
