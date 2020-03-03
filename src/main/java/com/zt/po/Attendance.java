/**
 * 
 */
package com.zt.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author yh
 * @date 2019年5月6日
 */
//考勤表
@Entity
@Table(name="zt_attendance")
@org.hibernate.annotations.Table(appliesTo = "zt_attendance",comment="考勤表")
public class Attendance extends BasePo{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	//月份
	@Column(columnDefinition ="varchar(255)  COMMENT '月份.'" )
	private  int month;
	//姓名
	@Column(columnDefinition ="varchar(255)  COMMENT '姓名.'" )
	private String  fullname;
	//部门
	@Column(columnDefinition ="varchar(255)  COMMENT '部门.'" )
	private String  employeeofdept;
	//出勤天数
	@Column(columnDefinition ="varchar(255)  COMMENT '出勤天数.'" )
	private int  attendancedays ;
	//是否加班
	//是否调休
	//职位
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
	 * @return the month
	 */
	public int getMonth() {
		return month;
	}
	/**
	 * @param month the month to set
	 */
	public void setMonth(int month) {
		this.month = month;
	}
	/**
	 * @return the fullname
	 */
	public String getFullname() {
		return fullname;
	}
	/**
	 * @param fullname the fullname to set
	 */
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	/**
	 * @return the employeeofdept
	 */
	public String getEmployeeofdept() {
		return employeeofdept;
	}
	/**
	 * @param employeeofdept the employeeofdept to set
	 */
	public void setEmployeeofdept(String employeeofdept) {
		this.employeeofdept = employeeofdept;
	}
	/**
	 * @return the attendancedays
	 */
	public int getAttendancedays() {
		return attendancedays;
	}
	/**
	 * @param attendancedays the attendancedays to set
	 */
	public void setAttendancedays(int attendancedays) {
		this.attendancedays = attendancedays;
	}
	
	
}
