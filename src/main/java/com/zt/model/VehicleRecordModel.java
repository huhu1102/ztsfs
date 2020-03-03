/**
 * 
 */
package com.zt.model;

import java.util.Date;

import com.zt.po.Employee;
import com.zt.po.Vechclerequest;

import com.zt.po.VehicleRecord;

/**
 * @author yh
 * @date 2019年6月3日
 */
public class VehicleRecordModel {
	
	//private Vechclerequest carRequest;
	//  VehicleMaintenance carMaintenance;
	/**
	 * @param carRecord
	 * @param carRequest
	 * @param carMaintenance
	 */
	/*public VehicleRecordModel(VehicleRecord carRecord, Vechclerequest carRequest, VehicleMaintenance carMaintenance) {
		super();
		this.carRecord = carRecord;
		this.carRequest = carRequest;
		this.carMaintenance = carMaintenance;
	}
	/**
	 * 
	 */

	/**
	 * @param carRecord
	 */

	/**
	 * @param carRequest
	 */
	/*public VehicleRecordModel(Vechclerequest carRequest) {
		super();
		this.carRequest = carRequest;
	}
	/**
	 * @param carMaintenance
	 */
	/*public VehicleRecordModel(VehicleMaintenance carMaintenance) {
		super();
		this.carMaintenance = carMaintenance;
	}
	/**
	 * @param carRecord
	 * @param carRequest
	 */
	/*public VehicleRecordModel(VehicleRecord carRecord, Vechclerequest carRequest) {
		super();
		this.carRecord = carRecord;
		this.carRequest = carRequest;
	}
	/**
	 * @param carRecord
	 * @param carMaintenance
	 */
	/*public VehicleRecordModel(VehicleRecord carRecord, VehicleMaintenance carMaintenance) {
		super();
		this.carRecord = carRecord;
		this.carMaintenance = carMaintenance;
	}
	/**
	 * @param carRequest
	 * @param carMaintenance
	 */
	/* VehicleRecordModel(Vechclerequest carRequest, VehicleMaintenance carMaintenance) {
		super();
		this.carRequest = carRequest;
		this.carMaintenance = carMaintenance;
	}
	*/
	/**
	 * @param carRecord
	 */
	/**
	 * @param carRecord
	 */

	/**
	 * 
	 */
	
     private long  id;
	private String carNo;
	
	//使用者 出行人 获得分配后出行人姓名

	private  String  travellingPeople;

	// 分配者
	 
	private Employee user;
	//分配时间 由综合部分配的时间
	
	private Date distributeDate;
	//出发事由
	
	private String startCause;
	//初始行程数
	
	private float startMileage;
	//出发日期
	private Date outDate;
	//终点行程数  此处和车辆维护表中下一次预约里程做算法
	
	private  float  endMileage;
	//实际事由

	private String endCause;

	//结束日期
	private Date outendDate;

	private Date endDate;
	//申请记录成功提交时间
	
	private Date createDate;
	//备注1
	
	private String note;
	//关联的分配单 已经分配过的
	//单向一对多  不指定外键生成策略
	
	private Vechclerequest  carApplication;
	//是否审核通过 (综合或者主管审核通过后该记录不能更改)  
	
	private boolean verification; 
	/**
	 * 该字段用于删除
	 */
	
	private boolean  enabled;
	/**
	 * @param carNo
	 * @param travellingPeople
	 * @param user
	 * @param distributeDate
	 * @param startCause
	 * @param startMileage
	 * @param startDate
	 * @param endMileage
	 * @param endCause
	 * @param endDate
	 * @param createDate
	 * @param note
	 * @param carApplication
	 * @param verification
	 * @param enabled
	 */
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
		
	/**
	 * @return the carNo
	 */
	public String getCarNo() {
		return carNo;
	}
	/**
	 * @param carNo the carNo to set
	 */
	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}
	/**
	 * @return the travellingPeople
	 */
	public String getTravellingPeople() {
		return travellingPeople;
	}
	/**
	 * @param travellingPeople the travellingPeople to set
	 */
	public void setTravellingPeople(String travellingPeople) {
		this.travellingPeople = travellingPeople;
	}
	/**
	 * @return the user
	 */
	public Employee getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(Employee user) {
		this.user = user;
	}
	/**
	 * @return the distributeDate
	 */
	public Date getDistributeDate() {
		return distributeDate;
	}
	/**
	 * @param distributeDate the distributeDate to set
	 */
	public void setDistributeDate(Date distributeDate) {
		this.distributeDate = distributeDate;
	}
	/**
	 * @return the startCause
	 */
	public String getStartCause() {
		return startCause;
	}
	/**
	 * @param startCause the startCause to set
	 */
	public void setStartCause(String startCause) {
		this.startCause = startCause;
	}
	/**
	 * @return the startMileage
	 */
	public float getStartMileage() {
		return startMileage;
	}
	/**
	 * @param startMileage the startMileage to set
	 */
	public void setStartMileage(float startMileage) {
		this.startMileage = startMileage;
	}
	
	/**
	 * @return the endMileage
	 */
	public float getEndMileage() {
		return endMileage;
	}
	/**
	 * @param endMileage the endMileage to set
	 */
	public void setEndMileage(float endMileage) {
		this.endMileage = endMileage;
	}
	/**
	 * @return the endCause
	 */
	public String getEndCause() {
		return endCause;
	}
	/**
	 * @param endCause the endCause to set
	 */
	public void setEndCause(String endCause) {
		this.endCause = endCause;
	}
	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
	 * @return the note
	 */
	public String getNote() {
		return note;
	}
	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}
	/**
	 * @return the carApplication
	 */
	public Vechclerequest getCarApplication() {
		return carApplication;
	}
	/**
	 * @param carApplication the carApplication to set
	 */
	public void setCarApplication(Vechclerequest carApplication) {
		this.carApplication = carApplication;
	}
	/**
	 * @return the verification
	 */
	public boolean isVerification() {
		return verification;
	}
	/**
	 * @param verification the verification to set
	 */
	public void setVerification(boolean verification) {
		this.verification = verification;
	}
	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}
	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	/**
	 * 
	 */
	public VehicleRecordModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the outDate
	 */
	public Date getOutDate() {
		return outDate;
	}

	/**
	 * @param outDate the outDate to set
	 */
	public void setOutDate(Date outDate) {
		this.outDate = outDate;
	}

	/**
	 * @return the outendDate
	 */
	public Date getOutendDate() {
		return outendDate;
	}

	/**
	 * @param outendDate the outendDate to set
	 */
	public void setOutendDate(Date outendDate) {
		this.outendDate = outendDate;
	}

	
	
	
}
