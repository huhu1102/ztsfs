package com.zt.vo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author wl
 * @date 2019年4月26日 
 */
@Entity
@Table(name="vemployee")
public class VEmployee {

	@Id
	String name;
	
	String email;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @param name
	 * @param email
	 */
	public VEmployee(String name, String email) {
		super();
		this.name = name;
		this.email = email;
	}

	/**
	 * 
	 */
	public VEmployee() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "VEmployee [name=" + name + ", email=" + email + "]";
	}
	
}
