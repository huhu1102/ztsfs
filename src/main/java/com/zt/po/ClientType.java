package com.zt.po;


import javax.persistence.*;
import java.util.Date;

//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;

/**
* @author whl
* @version 2019年10月10日
* 客户联系人 表
*/
@Entity
@Table(name="zt_clientType")
@org.hibernate.annotations.Table(appliesTo = "zt_clientType",comment="客户种类")
public class ClientType extends BasePo{

	private static final long serialVersionUID = 1L;
	//主键id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	//种类名称 ~~  中石化 中石油~~
	@Column(columnDefinition ="varchar(255)  COMMENT '种类名称'" )
	private String name;		
	//种类代号
	@Column(columnDefinition ="varchar(255)  COMMENT '种类代号.'" )
	private String code;
	//电话

	//创建记录时间
	@Column(columnDefinition ="date COMMENT '创建时间'" )
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	@Column(columnDefinition ="TINYINT(1)  COMMENT '是否可用'" )
	 private boolean enabled;

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
