/**  
* 
*/  
 
package com.zt.vo;


import com.zt.po.BasePo;
import com.zt.po.Client;

import java.util.Date;

/**
 * @author whl
 * @date 2019年6月12日 
 */
public class ClientModel extends   BasePo {
	private static final long serialVersionUID = 1L;
	private long id;
	//客户名称 --站名
	private String name;
	//简称
	private String abbreviation;
	//客户地址(县级)
	private String address;
	//客户详细地址(具体位置)
	private String addressDetails;
	//客户电话
	private String phone;
	//客户传真
	private String fax;
	//流派
	private  String types;
	//客户邮箱
	private String email;
	//客户状态(上游/下游)
	private String status;
	//创建时间
	private String child ;
	//附件名字
	private String uploadFiles;
	//客户等级
	private int grade;
	//父公司
	/**
	  * 父公司名称
	 */
	private String  parentName;
	private Date createDate;
	/**
	 * 父公司id
	 */
	private long  parentClientId;
	/**
	 * 公司简介
	 */
	private String infor;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAbbreviation() {
		return abbreviation;
	}
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getUploadFiles() {
		return uploadFiles;
	}

	public void setUploadFiles(String uploadFiles) {
		this.uploadFiles = uploadFiles;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the parentName
	 */
	public String getParentName() {
		return parentName;
	}
	/**
	 * @param parentName the parentName to set
	 */
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	/**
	 * @return the infor
	 */
	public String getInfor() {
		return infor;
	}
	/**
	 * @param infor the infor to set
	 */
	public void setInfor(String infor) {
		this.infor = infor;
	}
	/**
	 * @return the addressDetails
	 */
	public String getAddressDetails() {
		return addressDetails;
	}
	/**
	 * @param addressDetails the addressDetails to set
	 */
	public void setAddressDetails(String addressDetails) {
		this.addressDetails = addressDetails;
	}
	/**
	 * @return the child
	 */
	public String getChild() {
		return child;
	}
	/**
	 * @param child the child to set
	 */
	public void setChild(String child) {
		this.child = child;
	}
	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * @return the parentClientId
	 */
	public long getParentClientId() {
		return parentClientId;
	}
	/**
	 * @param parentClientId the parentClientId to set
	 */
	public void setParentClientId(long parentClientId) {
		this.parentClientId = parentClientId;
	}

	public Client v2p(ClientModel mo) {
		Client c=new Client();
		if (mo.getId()!=0) {
			c.setId(mo.getId());
		}
			c.setAbbreviation(mo.getAbbreviation());
			c.setAddress(mo.getAddress());
			c.setAddressDetails(mo.getAddressDetails());
//				c.setChild(mo.getChild());
			c.setEnabled(true);
			c.setEmail(mo.getEmail());
			c.setFax(mo.getFax());
			c.setInfor(mo.getInfor());
			c.setName(mo.getName());
			c.setParentClientId(mo.getParentClientId());
			c.setParentName(mo.getParentName());
			c.setStatus(mo.getStatus());
			c.setPhone(mo.getPhone());
			if(mo.getTypes()!=null){
			c.setTypes(mo.getTypes());
			}
			if(mo.getGrade()!=0){
				c.setGrade(mo.getGrade());
			}

//			c.setUploadFiles(mo.getUploadFiles());
			c.setCreateDate(mo.getCreateDate());
		return c;

	}


	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}
}
