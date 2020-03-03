
package com.zt.po;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

/**
 * @author wl
 * @date 2019年4月15日 
 * 会议记录的实体类
 */
@Entity
@Table(name="zt_meeting")
@org.hibernate.annotations.Table(appliesTo = "zt_meeting",comment="公告")
public class Meeting extends BasePo{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	//会议主题
	@Column(columnDefinition ="varchar(255)  COMMENT '会议主题.'" )
	private String subject;
	//会议内容
	@Column(columnDefinition ="varchar(255)  COMMENT '会议内容.'" )
	private String content;
	//记录人
	@JsonIgnore
	@ManyToOne  //代表是一对多关联
    @JoinColumn(name="employeeId")  //关联的字段
	private Employee employee;
	//是否可用
	@Column(columnDefinition ="TINYINT(1)  COMMENT '是否可用'" )
	private boolean enabled;
	//会议时间
	@Column(columnDefinition ="datetime COMMENT '会议时间'" )
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
}
