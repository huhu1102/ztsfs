/**
 * 
 */
package com.zt.po;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

/**
 * @author yh
 * @date 2019年4月17日
 */
//车辆保养
@Entity
@Table(name="zt_vehiclemaintenance")
@org.hibernate.annotations.Table(appliesTo = "zt_vehiclemaintenance",comment="车辆保养记录")
public class VehicleMaintenance  extends BasePo{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@JsonIgnore
	@ManyToOne
//	@JoinColumn(name="vechId")//解决多出的中间表
	private VehicleInfo vech;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	
	//单向多对一   ，维护记录表肯定有employeee   某些employee 却不一定拥有  维护记录表
	//@ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)//可选属性optional=false,表示Employee emplyer 不能W为空不能为空。删除多的一方，不影响一的一方  以此处为基准
	//@JoinColumn(name="emplyerId")
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="employer_ID")//此处名字还是有区别？
	private Employee emplyer;
	//保养人
	@Column(columnDefinition ="varchar(255)  COMMENT '保养人'" )
	private  String username;
	@Column(columnDefinition ="varchar(255)  COMMENT '车牌'" )
	private String carNo;
	
	//当前已经行驶里程
//	@Column(columnDefinition ="INT(30) COMMENT'当前已经行驶里程数'" )
//	private int  drivingkilometres;
	//维护地址
	@Column(columnDefinition ="varchar(255)  COMMENT '维护地址.'" )
	private String address;
	//下一次预约里程
	@Column(columnDefinition ="float(10,4) COMMENT '下一次预约里程'" )
	 private float maintenanceMile;
	//维护时间
	@Column(columnDefinition ="datetime COMMENT'保养时间'" )
	@Temporal(TemporalType.TIMESTAMP)
	private Date maintainDate;
	//当次维护里程数
	@Column(columnDefinition ="float(10,4) COMMENT'当次维护里程数'" )
	private float nowMileage;
	//备注
	@Column(columnDefinition ="varchar(255)  COMMENT '备注.'" )
	private String mks;
	/**
	 * 是否删除；
	 */
	@Column(columnDefinition ="TINYINT(1)  COMMENT '是否可见'" )
	private boolean enabled;
	
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
	 * @return the vech
	 */
	public VehicleInfo getVech() {
		return vech;
	}
	/**
	 * @param vech the vech to set
	 */
	public void setVech(VehicleInfo vech) {
		this.vech = vech;
	}
	
	
	/**
	 * @return the bookingMileage
	 */
	
	/**
	 * @return the maintainDate
	 */
	public Date getMaintainDate() {
		return maintainDate;
	}
	/**
	 * @param maintainDate the maintainDate to set
	 */
	public void setMaintainDate(Date maintainDate) {
		this.maintainDate = maintainDate;
	}
	/**
	 * @return the mks
	 */
	public String getMks() {
		return mks;
	}
	/**
	 * @param mks the mks to set
	 */
	public void setMks(String mks) {
		this.mks = mks;
	}
	
	public VehicleMaintenance() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * @return the emplyer
	 */
	public Employee getEmplyer() {
		return emplyer;
	}
	/**
	 * @param emplyer the emplyer to set
	 */
	public void setEmplyer(Employee emplyer) {
		this.emplyer = emplyer;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public float getNowMileage() {
		return nowMileage;
	}
	public void setNowMileage(int nowMileage) {
		this.nowMileage = nowMileage;
	}
	//测试类 根据备注测试  简单的测试对象
		public VehicleMaintenance(String mks) {
			super();
			this.mks = mks;
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
		 * @return the maintenanceMile
		 */
		public float getMaintenanceMile() {
			return maintenanceMile;
		}
		/**
		 * @param maintenanceMile the maintenanceMile to set
		 */
		public void setMaintenanceMile(float maintenanceMile) {
			this.maintenanceMile = maintenanceMile;
		}
		/**
		 * @param nowMileage the nowMileage to set
		 */
		public void setNowMileage(float nowMileage) {
			this.nowMileage = nowMileage;
		}
		@Override
		public String toString() {
			return "VehicleMaintenance [Id=" + id + ", vech=" + vech + ", createDate=" + createDate + ", emplyer="
					+ emplyer + ", carNo=" + carNo + ", address=" + address + ", maintenanceMile=" + maintenanceMile
					+ ", maintainDate=" + maintainDate + ", nowMileage=" + nowMileage + ", mks=" + mks + ", enabled="
					+ enabled + "]";
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
		 * @return the username
		 */
		public String getUsername() {
			return username;
		}
		/**
		 * @param username the username to set
		 */
		public void setUsername(String username) {
			this.username = username;
		}
		
}
