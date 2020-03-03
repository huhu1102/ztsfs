/**
 * 
 */
package com.zt.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.zt.po.Employee;

/**
 * @author yh
 * @date 2019年6月3日
 */
public class VehicleAllotModel {
	
	/**
	 * 分配时间
	 */
	private Date createDate;
	/**
	 * 申请时间
	 */
	
	private Date aplyDate;
	/**
	 * 是否删除；
	 */
	
	private boolean enabled;
	/**
	 * 备注
	 */
	
	private String note;
	/**
	 * 分配车辆的人
	 */
	
	private Employee  user;
	/**
	 * @param createDate
	 * @param aplyDate
	 * @param enabled
	 * @param note
	 * @param user
	 */
	public VehicleAllotModel(Date createDate, Date aplyDate, boolean enabled, String note, Employee user) {
		super();
		this.createDate = createDate;
		this.aplyDate = aplyDate;
		this.enabled = enabled;
		this.note = note;
		this.user = user;
	}
	/**
	 * 
	 */
	public VehicleAllotModel() {
		super();
		// TODO Auto-generated constructor stub
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
	 * @return the aplyDate
	 */
	public Date getAplyDate() {
		return aplyDate;
	}
	/**
	 * @param aplyDate the aplyDate to set
	 */
	public void setAplyDate(Date aplyDate) {
		this.aplyDate = aplyDate;
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
	 * @return the note
	 */
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
	 * @return the user
	 */
	public Employee getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(Employee user) {
		this.user = user;
	}
	
	
	
}
