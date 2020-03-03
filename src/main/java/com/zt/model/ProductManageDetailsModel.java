package com.zt.model;

import java.util.Date;


/**
 * @author wl
 * @date 2019年5月17日 
 * 生产管理详情的模型
 */
public class ProductManageDetailsModel {
	//生产管理详情Id
	private long detailsId;
	//车间名称
	private String plant;
	//合格数
	private float qualifiedNo;
	//报废数
	private float junkedNo;
	//备注
	private String remarks;
	//检查员
	private String examineName;
	//车间负责人
	private String workshopManageName;
	//检查日期
	private Date inspectDate;
	//工序步骤id
	private long productProcessId;
	//工序步骤名称
	private String productProcessName;
	public long getDetailsId() {
		return detailsId;
	}
	public void setDetailsId(long detailsId) {
		this.detailsId = detailsId;
	}
	public String getPlant() {
		return plant;
	}
	public void setPlant(String plant) {
		this.plant = plant;
	}
	
	public float getQualifiedNo() {
		return qualifiedNo;
	}
	public void setQualifiedNo(float qualifiedNo) {
		this.qualifiedNo = qualifiedNo;
	}
	public float getJunkedNo() {
		return junkedNo;
	}
	public void setJunkedNo(float junkedNo) {
		this.junkedNo = junkedNo;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getExamineName() {
		return examineName;
	}
	public void setExamineName(String examineName) {
		this.examineName = examineName;
	}
	public String getWorkshopManageName() {
		return workshopManageName;
	}
	public void setWorkshopManageName(String workshopManageName) {
		this.workshopManageName = workshopManageName;
	}
	public Date getInspectDate() {
		return inspectDate;
	}
	public void setInspectDate(Date inspectDate) {
		this.inspectDate = inspectDate;
	}
	public long getProductProcessId() {
		return productProcessId;
	}
	public void setProductProcessId(long productPreProcessId) {
		this.productProcessId = productPreProcessId;
	}
	public String getProductProcessName() {
		return productProcessName;
	}
	public void setProductProcessName(String productPreProcessName) {
		this.productProcessName = productPreProcessName;
	}
	
}
