package com.zt.model;

import com.zt.po.BasePo;
import com.zt.po.SalesOrderDetails;
import net.sf.json.JSONObject;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wl
 * @date 2019年6月15日 
 */
public class SalesOrderDetailsModel extends BasePo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	private long id;
	
	//物资编码
	private String resourcesNumber;

	//生产计划详情
	private String productionPlanDetailsIds;
	
	//不含税单价
	private BigDecimal unitPrice;
	
	//不含税总价
	private BigDecimal totalMoney;
	
	//交货期限
	private Date endDate;
	
	//交货地点
	private String address;
	//产品数量
    private float productNo;
	//备注
	private String remarks;

	//数据分类
	private String tap;

	public String getResourcesNumber() {
		return resourcesNumber;
	}

	public void setResourcesNumber(String resourcesNumber) {
		this.resourcesNumber = resourcesNumber;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTap() {
		return tap;
	}

	public void setTap(String tap) {
		this.tap = tap;
	}

	public String getProductionPlanDetailsIds() {
		return productionPlanDetailsIds;
	}

	public void setProductionPlanDetailsIds(String productionPlanDetailsIds) {
		this.productionPlanDetailsIds = productionPlanDetailsIds;
	}

	public  SalesOrderDetailsModel j2m(JSONObject object)throws  Exception{
		SalesOrderDetailsModel  detail=new SalesOrderDetailsModel();
		String idstr=String.valueOf(object.get("id"));
		if(!idstr.equals("null")&&!idstr.equals("0")){
			Long  detailId=Long.parseLong(idstr);
			detail.setId(detailId);
		}
		detail.setResourcesNumber(String.valueOf(object.get("resourcesNumber")));
		detail.setUnitPrice(new BigDecimal(String.valueOf(object.get("unitPrice"))));
		detail.setProductNo(Float.parseFloat(String.valueOf(object.get("productNo"))));
		detail.setTotalMoney(new BigDecimal(String.valueOf(object.get("totalMoney"))));
		String date=String.valueOf(object.get("endDate"));
		if(!date.equals("null")){
			detail.setEndDate(new SimpleDateFormat("y-M-d H:m:s").parse(date));
		}
		detail.setAddress(String.valueOf(object.get("address")));
		detail.setTap(String.valueOf(object.get("tap")));
		detail.setRemarks(String.valueOf(object.get("remarks")));
		detail.setProductionPlanDetailsIds(String.valueOf(object.get("productionPlanDetailsIds")));
		return detail;
	}

	public SalesOrderDetails v2p(SalesOrderDetailsModel mo){
		SalesOrderDetails c = new SalesOrderDetails();
		if(mo.getId()!=0){
			c.setId(mo.getId());
		}
		c.setAddress(mo.getAddress());
//		c.setCreateDate(new Date());
		c.setEnabled(true);
		c.setEndDate(mo.getEndDate());
		c.setRemarks(mo.getRemarks());
		c.setProductNo(mo.getProductNo());
		c.setUnitPrice(mo.getUnitPrice());
		c.setTotalMoney(mo.getTotalMoney());
		c.setResourcesNumber(mo.getResourcesNumber());
		return c;
	}

    public float getProductNo() {
        return productNo;
    }

    public void setProductNo(float productNo) {
        this.productNo = productNo;
    }
}
