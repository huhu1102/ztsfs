package com.zt.po;


import javax.persistence.*;
import java.util.Date;
import java.util.List;

//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;

/**
* @author whl
* @version 2019年10月10日
* 客户联系人 表
*/
@Entity
@Table(name="zt_clientContact")
@org.hibernate.annotations.Table(appliesTo = "zt_clientContact",comment="客户联系人")
public class ClientContact extends BasePo{

	private static final long serialVersionUID = 1L;
	//主键id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	//联系人名字
	@Column(columnDefinition ="varchar(255)  COMMENT '联系人名字'" )
	private String name;		
	//联系人岗位
	@Column(columnDefinition ="varchar(255)  COMMENT '联系人岗位.'" )
	private String post;
	//电话
	@Column(columnDefinition ="varchar(255)  COMMENT '电话.'" )
	private String phone;
	//备注
	@Column(columnDefinition ="varchar(255)  COMMENT '备注.'" )
	private String note;
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

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
