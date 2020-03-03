package com.zt.po;



import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

/**
 * @author wl
 * @date 2019年4月17日 
    *   具体工步的实体类
 */
@Entity
@Table(name="zt_workstep")
@org.hibernate.annotations.Table(appliesTo = "zt_workstep",comment="工步实体类")
public class Workstep extends BasePo{
	/**
	 *
	 */

	private static final long serialVersionUID = 1L;
	//主键ID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long Id;
	//工步名称
	@Column(columnDefinition ="varchar(255)  COMMENT '工步名称.'" )
	private String workstepName;
	//工步简介
	@Column(columnDefinition ="varchar(255)  COMMENT '公布简介.'" )
	private String intro;
	//工步code值(相当于单独的标识)
	 @JsonIgnore
	@Column(columnDefinition ="varchar(255)  COMMENT '工步code值(相当于单独的标识).'" )
	private String code;
	//是否可用
	 @JsonIgnore
	@Column(columnDefinition ="TINYINT(1)  COMMENT '是否可用'" )
	private boolean enabled;
	//创建记录时间
	 @JsonIgnore
	@Column(columnDefinition ="date COMMENT '创建时间'" )
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public long getId() {
		return Id;
	}
	public void setId(long id) {
		Id = id;
	}
	
	public String getWorkstepName() {
		return workstepName;
	}
	public void setWorkstepName(String workstepName) {
		this.workstepName = workstepName;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
}
