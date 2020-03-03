package com.zt.po;



import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

/**
 * @author whl
 * @date 2019年10月8日
 * 产品用途
 */
@Entity
@Table(name= "zt_productuse")
@org.hibernate.annotations.Table(appliesTo = "zt_productuse",comment="产品用途")
public class ProductUse extends BasePo{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	//工序步骤名称
	@Column(columnDefinition ="varchar(255) COMMENT '用途名称中文'" )
	private String name;
	//工序步骤编号
	@Column(columnDefinition ="varchar(255) COMMENT '用途编号'" )
	private String code;
	@JsonIgnore
	@Column(columnDefinition ="TINYINT(1)  COMMENT '是否可用'" )
	private boolean enabled;
	//公司标识
	//创建记录时间
	@JsonIgnore
	@Column(columnDefinition ="datetime COMMENT '创建时间'" )
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	public Date getCreateDate() {
		return createDate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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
