package com.zt.model;

/**
 * @author wl
 * @date 2019年6月3日 
 * 发货请求详情的模型
 */
public class ShippingRequestModel {
	//发货请求id
	private long shippingRequestId;
	//公司名称
	private String cliName;
	//发货请求详情
	private String shippingRequestDetails;
	
	
	public long getShippingRequestId() {
		return shippingRequestId;
	}
	public void setShippingRequestId(long shippingRequestId) {
		this.shippingRequestId = shippingRequestId;
	}
	public String getCliName() {
		return cliName;
	}
	public void setCliName(String cliName) {
		this.cliName = cliName;
	}
	public String getShippingRequestDetails() {
		return shippingRequestDetails;
	}
	public void setShippingRequestDetails(String shippingRequestDetails) {
		this.shippingRequestDetails = shippingRequestDetails;
	}
	
	
}
