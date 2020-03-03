package com.zt.model;

/**
 * @author wl
 * @date 2019年5月17日 
 * 生产管理的模型
 */
public class ProductManageModel {
	//生产管理的Id
	private long productManageId;
	//关联详情的字段
	private String proManageDetails;
	//开票人名字
	private String empName;
	//开始编码
	private long startEncoding;
	//结束编码
	private long endEncoding;
	public String getProManageDetails() {
		return proManageDetails;
	}
	public void setProManageDetails(String proManageDetails) {
		this.proManageDetails = proManageDetails;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public long getProductManageId() {
		return productManageId;
	}
	public void setProductManageId(long productManageId) {
		this.productManageId = productManageId;
	}
	public long getStartEncoding() {
		return startEncoding;
	}
	public void setStartEncoding(long startEncoding) {
		this.startEncoding = startEncoding;
	}
	public long getEndEncoding() {
		return endEncoding;
	}
	public void setEndEncoding(long endEncoding) {
		this.endEncoding = endEncoding;
	}
	
	
	
}
