/**
 * 
 */
package com.zt.model;

import java.util.Date;


/**
 * @author yh
 * @date 2019年5月31日
 */  
//必须知道前台传递的参数
public class VechcleInfoModel {
	//车牌号
    private  Long  id;
	private String plateNumber;

	//车辆状态
	
	private String carStatus;
	//购车时间

	private Date buyedDate;
	//生产厂家

	private String  manufacturer;

	/**
	 * 是否删除；
	 */
	
	private boolean enabled;
	
	//车型号
	private String vehicleType;
	
	//创建人
	private String  noteMaker;

	/**
	 * @return the plateNumber
	 */
	public String getPlateNumber() {
		return plateNumber;
	}

	/**
	 * @param plateNumber the plateNumber to set
	 */
	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	/**
	 * @return the carStatus
	 */
	public String getCarStatus() {
		return carStatus;
	}

	/**
	 * @param carStatus the carStatus to set
	 */
	public void setCarStatus(String carStatus) {
		this.carStatus = carStatus;
	}

	/**
	 * @return the buyedDate
	 */
	public Date getBuyedDate() {
		return buyedDate;
	}

	/**
	 * @param buyedDate the buyedDate to set
	 */
	public void setBuyedDate(Date buyedDate) {
		this.buyedDate = buyedDate;
	}

	/**
	 * @return the manufacturer
	 */
	public String getManufacturer() {
		return manufacturer;
	}

	/**
	 * @param manufacturer the manufacturer to set
	 */
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
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
	 * @return the vehicleType
	 */
	public String getVehicleType() {
		return vehicleType;
	}

	/**
	 * @param vehicleType the vehicleType to set
	 */
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	/**
	 * @return the noteMaker
	 */
	public String getNoteMaker() {
		return noteMaker;
	}

	/**
	 * @param noteMaker the noteMaker to set
	 */
	public void setNoteMaker(String noteMaker) {
		this.noteMaker = noteMaker;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 * @param plateNumber
	 * @param carStatus
	 * @param buyedDate
	 * @param manufacturer
	 * @param enabled
	 * @param vehicleType
	 * @param noteMaker
	 */
	public VechcleInfoModel(Long id, String plateNumber, String carStatus, Date buyedDate, String manufacturer,
			boolean enabled, String vehicleType, String noteMaker) {
		super();
		this.id = id;
		this.plateNumber = plateNumber;
		this.carStatus = carStatus;
		this.buyedDate = buyedDate;
		this.manufacturer = manufacturer;
		this.enabled = enabled;
		this.vehicleType = vehicleType;
		this.noteMaker = noteMaker;
	}

	/**
	 * 
	 */
	public VechcleInfoModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param id
	 * @param plateNumber
	 * @param buyedDate
	 * @param manufacturer
	 * @param enabled
	 * @param vehicleType
	 */
	public VechcleInfoModel(Long id, String plateNumber, Date buyedDate, String manufacturer, boolean enabled,
			String vehicleType) {
		super();
		this.id = id;
		this.plateNumber = plateNumber;
		this.buyedDate = buyedDate;
		this.manufacturer = manufacturer;
		this.enabled = enabled;
		this.vehicleType = vehicleType;
	}

	
	
	
}
