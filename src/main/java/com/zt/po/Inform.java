package com.zt.po;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

/**
 * @author wl
 * @date 2019年4月15日 
 * 通知实体类
 */
@Entity
@Table(name="zt_inform",
indexes = {@Index(name = "id",  columnList="id", unique = true)})	
					
@org.hibernate.annotations.Table(appliesTo = "zt_inform",comment="通知")
public class Inform extends BasePo{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	//通知主题
	@Column(columnDefinition ="varchar(255)  COMMENT '通知主题.'" )
	private String subject;
	//通知内容
	@Column(columnDefinition ="varchar(255)  COMMENT '通知内容.'" )
	private String content;
	//记录人
	@JsonIgnore
	@ManyToOne  //代表是一对多关联
    @JoinColumn(name="employeeId")  //关联的字段
	private Employee employee;
	//是否可用
	@Column(columnDefinition ="TINYINT(1)  COMMENT '是否可用'" )
	private boolean enabled;
	//通知时间
	@Column(columnDefinition ="datetime  COMMENT '通知时间'" )
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
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	
}
