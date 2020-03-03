package com.zt.vo;


import com.zt.po.BasePo;


/**
 * @author wl
 * @date 2019年6月28日 
 */
public class PurchaseVerifyDetailModel extends BasePo{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long id;
	//采购单详情的Id
	private Long purchasePlanId;
	//购买种类 1半成品，2 ，原料
	private Integer buyType;
	//数量
	private float  quantity;
	//备注
	private String notes;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Long getPurchasePlanId() {
		return purchasePlanId;
	}
	public void setPurchasePlanId(Long purchasePlanId) {
		this.purchasePlanId = purchasePlanId;
	}
	public Integer getBuyType() {
		return buyType;
	}
	public void setBuyType(Integer buyType) {
		this.buyType = buyType;
	}
	public float getQuantity() {
		return quantity;
	}
	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
}
