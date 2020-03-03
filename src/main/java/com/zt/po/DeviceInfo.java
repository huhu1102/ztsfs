/**
 * 
 */
package com.zt.po;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author yh
 * @date 2019年4月17日
 */
//设备信息
@Entity
@Table(name="zt_deviceinfo")
@org.hibernate.annotations.Table(appliesTo = "zt_deviceinfo",comment="生产设备")
public class DeviceInfo extends BasePo {
	private static final long serialVersionUID = 1L;

 //设备信息序列号
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	//设备名称
	@Column(columnDefinition ="varchar(255)  COMMENT '设备名称.'" )
	private String  deviceName;		
	//进驻年份
	@Column(columnDefinition ="date  COMMENT '进驻年份'" )
	@Temporal(TemporalType.DATE)
	private Date garrison;
	//设备厂商
	@Column(columnDefinition ="varchar(255)  COMMENT '设备厂商.'" )
	private String equipmentManufacturer;
	//设备状态
	@Column(columnDefinition ="varchar(255)  COMMENT '设备状态.'" )
	private String deviceStatus;
	//设备维护记录
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "def")//惰性加载,维护端在多的一方
	private List<Eqmt> emt;
	@Column(columnDefinition ="TINYINT(1)  COMMENT '是否可见'" )
	private boolean enabled;
	//创建时间
	@Column(columnDefinition ="datetime  COMMENT '创建时间'" )
	@Temporal(TemporalType.DATE)
	private Date createDate;

	//归属部门
	private long departMentId;

	//归属车间
	private long workStopId;

	//设备编号
	@Column(columnDefinition ="varchar(255)  COMMENT '设备编号'" )
	private String workNum;
	//设备年份
	@Column(columnDefinition ="datetime  COMMENT '设备生产时间'" )
	@Temporal(TemporalType.DATE)
	private Date date;
	//设备联系人
	@Column(columnDefinition ="varchar(255)  COMMENT '设备编号'" )
	private String workName;
	//联系方式
	private String phone;
	//检修年份
	@Column(columnDefinition ="datetime  COMMENT '检修时间'" )
	@Temporal(TemporalType.DATE)
	private Date reconditionDate;
	//备注
	private String nodes;
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
	 * @return the deviceName
	 */
	public String getDeviceName() {
		return deviceName;
	}
	/**
	 * @param deviceName the deviceName to set
	 */
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	/**
	 * @return the garrison
	 */
	public Date getGarrison() {
		return garrison;
	}
	/**
	 * @param garrison the garrison to set
	 */
	public void setGarrison(Date garrison) {
		this.garrison = garrison;
	}
	/**
	 * @return the equipmentManufacturer
	 */
	public String getEquipmentManufacturer() {
		return equipmentManufacturer;
	}
	/**
	 * @param equipmentManufacturer the equipmentManufacturer to set
	 */
	public void setEquipmentManufacturer(String equipmentManufacturer) {
		this.equipmentManufacturer = equipmentManufacturer;
	}
	/**
	 * @return the deviceStatus
	 */
	public String getDeviceStatus() {
		return deviceStatus;
	}
	/**
	 * @param deviceStatus the deviceStatus to set
	 */
	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
	}
	/**
	 * @return the emt
	 */
	public List<Eqmt> getEmt() {
		return emt;
	}
	/**
	 * @param emt the emt to set
	 */
	public void setEmt(List<Eqmt> emt) {
		this.emt = emt;
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

	public long getDepartMentId() {
		return departMentId;
	}

	public void setDepartMentId(long departMentId) {
		this.departMentId = departMentId;
	}

	public long getWorkStopId() {
		return workStopId;
	}

	public void setWorkStopId(long workStopId) {
		this.workStopId = workStopId;
	}

	public String getWorkNum() {
		return workNum;
	}

	public void setWorkNum(String workNum) {
		this.workNum = workNum;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getWorkName() {
		return workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getReconditionDate() {
		return reconditionDate;
	}

	public void setReconditionDate(Date reconditionDate) {
		this.reconditionDate = reconditionDate;
	}

	public String getNodes() {
		return nodes;
	}

	public void setNodes(String nodes) {
		this.nodes = nodes;
	}
}
