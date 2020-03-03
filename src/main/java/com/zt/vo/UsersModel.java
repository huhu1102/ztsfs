/**  
* 
*/  
 
package com.zt.vo;

import com.zt.po.BasePo;

import java.util.List;

/**
 * @author whl
 * @date 2019年5月28日 
 */
public class UsersModel  extends   BasePo {

	private static final long serialVersionUID = 1L;
	private long id;
	private String username;
	//登录密码
	private String password;
    private String phone;
    private boolean enabled;
    //json字符串
    private List<Integer> roles;
    private String userface;
    private String empName;
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

	/**
	 * @return the userface
	 */
	public String getUserface() {
		return userface;
	}

	/**
	 * @return the roles
	 */
	public List<Integer> getRoles() {
		return roles;
	}
	/**
	 * @param roles the roles to set
	 */
	public void setRoles(List<Integer> roles) {
		this.roles = roles;
	}
	/**
	 * @param userface the userface to set
	 */
	public void setUserface(String userface) {
		this.userface = userface;
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
