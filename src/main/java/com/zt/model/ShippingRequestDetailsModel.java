package com.zt.model;


/**
 * @author wl
 * @date 2019年6月3日 
 */
public class ShippingRequestDetailsModel {
	//目的地
	private String destination;
	//收货方式
	private String ReceivingWay;
	//收货人电话
	private String ReceivingPhone;
	//计划发货数量
	private float planNumber;
	//备注
	private String remarks;
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getReceivingWay() {
		return ReceivingWay;
	}
	public void setReceivingWay(String receivingWay) {
		ReceivingWay = receivingWay;
	}
	public String getReceivingPhone() {
		return ReceivingPhone;
	}
	public void setReceivingPhone(String receivingPhone) {
		ReceivingPhone = receivingPhone;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public float getPlanNumber() {
		return planNumber;
	}
	public void setPlanNumber(float planNumber) {
		this.planNumber = planNumber;
	}
	
}
