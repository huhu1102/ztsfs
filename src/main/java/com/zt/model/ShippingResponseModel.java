package com.zt.model;

/**
 * @author wl
 * @date 2019年6月3日 
 * 发货请求详情的模型
 */
public class ShippingResponseModel {
	//发货请求id
	private long shippingResponseId;
	//公司名称
	private String cliName;
	//发货请求详情
	private String shippingResponseDetails;
	
	
	
	public long getShippingResponseId() {
		return shippingResponseId;
	}
	public void setShippingResponseId(long shippingResponseId) {
		this.shippingResponseId = shippingResponseId;
	}
	public String getShippingResponseDetails() {
		return shippingResponseDetails;
	}
	public void setShippingResponseDetails(String shippingResponseDetails) {
		this.shippingResponseDetails = shippingResponseDetails;
	}
	public String getCliName() {
		return cliName;
	}
	public void setCliName(String cliName) {
		this.cliName = cliName;
	}
	
	
	
}
