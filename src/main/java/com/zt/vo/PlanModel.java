/**  
* 
*/  
 
package com.zt.vo;

import com.zt.po.*;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author whl
 * @date 2019年6月18日 
 */
public class PlanModel  extends   BasePo  {

	
	private static final long serialVersionUID = 1L;
	//采购序列号
	private long id;
	
	/**
	 *    采购单名称
	 *    采购单名称    为  生成的采购单名称 == 物料名称+购买数量+单位+购买单下单时间戳
	 */
	private String planName;
	//采购计划单编号
	private String  serialNumber;
	private Long clientId;
	private String clientName;
	//备注
	private String notes;
	//采购计划详情
	private String detail;
	//下单时间
	private Date createDate;
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
	 * @return the planName
	 */
	public String getPlanName() {
		return planName;
	}
	/**
	 * @param planName the planName to set
	 */
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	/**
	 * @return the serialNumber
	 */
	public String getSerialNumber() {
		return serialNumber;
	}
	/**
	 * @param serialNumber the serialNumber to set
	 */
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	/**
	 * @return the clientId
	 */
	public Long getClientId() {
		return clientId;
	}
	/**
	 * @param clientId the clientId to set
	 */
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	/**
	 * @return the clientName
	 */
	public String getClientName() {
		return clientName;
	}
	/**
	 * @param clientName the clientName to set
	 */
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}
	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}
	/**
	 * @return the detail
	 */
	public String getDetail() {
		return detail;
	}
	/**
	 * @param detail the detail to set
	 */
	public void setDetail(String detail) {
		this.detail = detail;
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
	@Override
	public String toString() {
		return "PlanModel [id=" + id + ", planName=" + planName + ", serialNumber=" + serialNumber +  ", clientId=" + clientId + ", clientName=" + clientName + ", notes=" + notes
				+ ", detail=" + detail + ", createDate=" + createDate + "]";
	}
	
	public RowMaterialPurchasePlan   v1p(PlanModel m) {
		RowMaterialPurchasePlan plan =new RowMaterialPurchasePlan();
		if (m.getClientId()!=0) {
			plan.setClientId(m.getClientId());
			plan.setClientName(m.getClientName());
		}
		plan.setId(m.getId());
		plan.setCreateDate(m.getCreateDate());
		plan.setConfirmStatus(1);
		plan.setSerialNumber(m.getSerialNumber());
		//materialType
		plan.setEnabled(true);
		plan.setNotes(m.getNotes());
		plan.setDetail(new ArrayList<RowMaterialPurchasePlanDetail>());
		plan.setCreateDate(new Date());
	  return plan;
	}
	public MidProductPurchasePlan   v2p(PlanModel m) {
		MidProductPurchasePlan plan =new MidProductPurchasePlan();
		if (m.getClientId()!=0) {
			plan.setClientId(m.getClientId());
			plan.setClientName(m.getClientName());
		}
		plan.setId(m.getId());
		plan.setCreateDate(m.getCreateDate());
		plan.setConfirmStatus(1);
		plan.setEnabled(true);
		plan.setNotes(m.getNotes());
		plan.setDetail(new ArrayList<MidProductPurchasePlanDetail>());
		plan.setCreateDate(new Date());
		return plan;
	}
	
	
    
	

}
