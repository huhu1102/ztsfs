package com.zt.po;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;


//车辆基础信息
/**
 * @author yh
 * @date 2019年4月12日
 */
@Entity
@Table(name="zt_vehicleInfo")//
@org.hibernate.annotations.Table(appliesTo = "zt_vehicleInfo",comment="车辆基本信息")
public class VehicleInfo extends BasePo{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//车辆id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	//车牌号
//	@NotEmpty(message="车牌号不能为空")
	@Column(columnDefinition ="varchar(255)  COMMENT '车牌号.'" )
	private String plateNumber;

	@Column(columnDefinition = "varchar(255) COMMENT '车辆品牌'")
	private String name;


	/**车辆状态
	 * 1.空闲
	 * 2.已分配
	 * 3.二次分配
	 * 4.使用中
	 * 5.二次使用归还
	 * 6.临近保养
	 */
	@Column(columnDefinition ="varchar(255)  COMMENT '车辆状态.'" )
	private Integer carStatus;
	//购车时间
	@Column(columnDefinition ="datetime COMMENT'购车年份'" )
	@Temporal(TemporalType.TIMESTAMP)
	private Date buyedDate;
	/**
	 * 行驶里程   
	 */
	//private float driveMile;
	
	//极限使用里程数标准（ 维护公里数)
	@Column(nullable=false,columnDefinition ="float(10,4) COMMENT '极限使用里程数'")
	private float repairMile;
	/**
	 * 是否删除；
	 */
	@Column(columnDefinition ="TINYINT(1)  COMMENT '是否可见'" )
	private boolean enabled;
	//车辆承载人数限制
	//@Column(columnDefinition ="integer(4)  COMMENT '承载人数.'" )
	//private int carryingNumber;
	@Column(columnDefinition ="varchar(255)  COMMENT '车型号.'" )
	//车型号
	private String vehicleType;
	@Column(columnDefinition ="varchar(255)  COMMENT '创建人.'" )
	//创建人
	private String  noteMaker;
	//创建时间
	@Column(columnDefinition ="datetime COMMENT '创建时间'" )
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	//上次保养时间
	@Column(columnDefinition ="datetime COMMENT '上次保养时间'" )
	@Temporal(TemporalType.TIMESTAMP)
	private Date oldDate;

	//下次保养时间
	@Column(columnDefinition ="datetime COMMENT '下次保养时间'" )
	@Temporal(TemporalType.TIMESTAMP)
	private Date newDate;



	//行车记录  暂不注解  有可能多出来的字段

//	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "vehich")// 惰性加载,维护端在多的一方 不需要加载出来
//	private List<VehicleRecord> veRecord=new ArrayList<>();
//	
//	
//	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "vech")// 惰性加载,维护端在多的一方 不需要加载出来
//	private List<VehicleMaintenance> Vehiclemte=new ArrayList<>();
//				
//	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "vcif")// 惰性加载,维护端在多的一方 不需要加载出来
//	private List<Asked> Vehicleask=new ArrayList<>();


	
	/**
	 * @param plateNumber
	 */
	//构成测试对象
	public VehicleInfo(String plateNumber) {
		super();
		this.plateNumber = plateNumber;
	}

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
		this.id= id;
	}

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCarStatus() {
		return carStatus;
	}

	public void setCarStatus(Integer carStatus) {
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
	 * 
	 */
	public VehicleInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the repairMile
	 */
	public float getRepairMile() {
		return repairMile;
	}

	/**
	 * @param repairMile the repairMile to set
	 */
	public void setRepairMile(float repairMile) {
		this.repairMile = repairMile;
	}

	public Date getOldDate() {
		return oldDate;
	}

	public void setOldDate(Date oldDate) {
		this.oldDate = oldDate;
	}

	public Date getNewDate() {
		return newDate;
	}

	public void setNewDate(Date newDate) {
		this.newDate = newDate;
	}
}
