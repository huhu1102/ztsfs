/**
 * 
 */
package com.zt.po;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;



/**
 * @author yh
 * @date 2019年4月16日
 */
//车辆使用记录表
@Entity
@Table(name="zt_vehiclerecord")
@org.hibernate.annotations.Table(appliesTo = "zt_vehiclerecord",comment="车辆使用记录")
public class VehicleRecord extends BasePo {
	private static final long serialVersionUID = 1L;
	//记录表id
	//记录表id
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private long id;
		//车辆信息 获得分配后的车牌号
		@JsonIgnore
		@ManyToOne
		private VehicleInfo vehicleInfo;
		@Column(columnDefinition ="varchar(255)  COMMENT '车牌'" )
		private String carNo;
	
		//使用者 出行人 获得分配后出行人姓名  可以填写多个
		@Column(columnDefinition ="varchar(255)  COMMENT '出行者'" )
		private  String  userName;
		//@ManyToOne
		//@JoinColumn(name="eeId")
		//  单向多对1  emplyee 不一定含有这个属性   这个属性肯定含有employee 
		@JsonIgnore
		@ManyToOne(fetch=FetchType.LAZY)
		 @JoinColumn(name="Employee_ID")//此处名字还是有区别？
		private Employee user;
		//分配时间 由综合部分配的时间
		@Column(columnDefinition ="datetime COMMENT '分配时间'" )
		@Temporal(TemporalType.TIMESTAMP)
		private Date distributeDate;
		//出发事由
		@Column(columnDefinition ="varchar(255)  COMMENT '出发事由.'" )
		private String startCause;
		//初始行程数
		@Column(nullable=false,columnDefinition ="float(10,4) COMMENT'初始里程数'")//非空
		private float startMileage;
		//开始日期*******************************
		@Column(columnDefinition ="datetime  COMMENT '开始日期.'" )
		@Temporal(TemporalType.TIMESTAMP)
		private Date startDate;
		//终点行程数  此处和车辆维护表中下一次预约里程做算法
		@Column(nullable=false,columnDefinition ="float(10,4) COMMENT'终点行程里数'")
		private  float  endMileage;
		//实际事由
		@Column(columnDefinition ="varchar(255)  COMMENT '出发事由.'" )
		private String endCause;
		//结束日期*********************************
		@Column(columnDefinition ="datetime  COMMENT '获取系统时间'" )
		@Temporal(TemporalType.TIMESTAMP)
		private Date endDate;
		//提交信息时间
		@Column(nullable=false,columnDefinition =" datetime COMMENT '提交时间'")
		@Temporal(TemporalType.TIMESTAMP)
		private Date createDate;
		//备注1
		@Column(columnDefinition ="varchar(255)  COMMENT '备注1.'" )
		private String note;
		//关联的分配单 已经分配过的
		//单向一对多  不指定外键生成策略
		@Column(columnDefinition ="bigint(20)  COMMENT '相关联用车申请'" )
		private Long  vechclerequestId;
		//是否审核通过 (综合或者主管审核通过后该记录不能更改)  
		@Column(columnDefinition ="TINYINT(1)  COMMENT '审核是否通过'" )
		private boolean confirm; 
		/**
		 * 该字段用于删除
		 */
		@Column(columnDefinition ="TINYINT(1)  COMMENT '是否删除'" )
		private boolean  enabled;
		
		
		
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
	
	
		public VehicleRecord() {
			super();
			// TODO Auto-generated constructor stub
		}
		/**
		 * @return the serialversionuid
		 */
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
		 * @return the verification
		 */
	
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
		 * @return the startDate
		 */
		public Date getStartDate() {
			return startDate;
		}
		/**
		 * @param startDate the startDate to set
		 */
		public void setStartDate(Date startDate) {
			this.startDate = startDate;
		}
		/**
		 * @return the createDate
		 */
		public Date getCreateDate() {
			return createDate;
		}
	
		public void setCreateDate(Date createDate) {
			this.createDate = createDate;
		}
	
		
		
}
