/**  
* 
*/  
 
package com.zt.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * @author whl
 * @date 2019年5月5日
 * 管理系统使用人 
 */
@Entity
@Table(name="zt_users")
@org.hibernate.annotations.Table(appliesTo = "zt_users",comment="管理系统使用人")
public class Users implements  UserDetails{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(columnDefinition ="varchar(25)  COMMENT '登录姓名'" )
	private String username;
	//登录密码
	@JsonIgnore
	@Column(columnDefinition ="varchar(255)  COMMENT '登录密码.'" )
	private String password;
	@Column(columnDefinition ="varchar(255)  COMMENT '联系电话.'" )
    private String phone;
	@Column(columnDefinition ="varchar(255)  COMMENT '是否可用.'" )
    private boolean enabled;
    @ManyToMany(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    private List<SysRole> roles;
	@Column(columnDefinition ="varchar(255)  COMMENT '头像.'" )
    private String userface;	
	@Column(columnDefinition ="varchar(50)  COMMENT '员工姓名.'" )
    private String empName;
	@Column(columnDefinition ="bigint(20)  COMMENT '关联员工Id.'" )
    private long empId;
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
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
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
	 * @return the roles
	 */
	public List<SysRole> getRoles() {
		return roles;
	}
	/**
	 * @param roles the roles to set
	 */
	public void setRoles(List<SysRole> roles) {
		this.roles = roles;
	}
	/**
	 * @return the userface
	 */
	public String getUserface() {
		return userface;
	}
	/**
	 * @param userface the userface to set
	 */
	public void setUserface(String userface) {
		this.userface = userface;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		  List<GrantedAuthority> authorities = new ArrayList<>();
	        for (SysRole role : roles) {
	            authorities.add(new SimpleGrantedAuthority(role.getName()));
	        }
	        return authorities;
	}
	 
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String toString() {
		return "Users [id=" + id + ", username=" + username + ", password=" + password + ", phone=" + phone
				+ ", enabled=" + enabled + ", roles=" + roles + ", userface=" + userface + ", empName=" + empName
				+ ", empId=" + empId + "]";
	}
	
	public Users(long id, String username, String password, String phone, boolean enabled, List<SysRole> roles,
			String userface, String empName, long empId) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.phone = phone;
		this.enabled = enabled;
		this.roles = roles;
		this.userface = userface;
		this.empName = empName;
		this.empId = empId;
	}
	/**
	 * 
	 */
	public Users() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return the empName
	 */
	public String getEmpName() {
		return empName;
	}
	/**
	 * @param empName the empName to set
	 */
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	/**
	 * @return the empId
	 */
	public long getEmpId() {
		return empId;
	}
	/**
	 * @param empId the empId to set
	 */
	public void setEmpId(long empId) {
		this.empId = empId;
	}
}
