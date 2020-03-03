/**
 * 
 */
package com.zt.po;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;


/**
 * @author yh
 * @date 2019年4月28日
    *        招标结果
 */
@Entity
@Table(name="zt_biddingresult")
@EntityListeners(AuditingEntityListener.class)        		
@org.hibernate.annotations.Table(appliesTo = "zt_biddingresult",comment="招标结果")
public class BiddingResult extends BasePo{
	private static final long serialVersionUID = 1L;
	//主键id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	//参加人Id
	@Column(columnDefinition ="bigint(20)  COMMENT '参加人Id'" )
	private  Long  employeeId;
	
	//关联员工
	@ManyToOne
	private Employee employee;
	
	//完结时间
	@Column(columnDefinition="datetime  COMMENT'完成时间'")
	@Temporal(TemporalType.DATE)
    private Date finishDate;
	//参标地点
	@Column(columnDefinition ="varchar(255)  COMMENT '参标地点'" )
	private String address;
	//中标结果
	@Column(columnDefinition ="varchar(255)  COMMENT '中标结果'" )
	private String status;
	
	//备注
	@Column(columnDefinition ="varchar(255)  COMMENT '备注'" )
	private String note;
	
	
	//客户Id
	@Column(columnDefinition ="bigint(20)  COMMENT '客户Id.'" )
	private Long clientId;
	
	//关联客户
	@ManyToOne
	private Client client;
	
	
	//是否可删除
	@Column(columnDefinition ="TINYINT(1)  COMMENT '是否可见'" )
	private boolean enabled;
	//创建时间
	@Column(columnDefinition ="datetime  COMMENT '创建时间'" )
	@Temporal(TemporalType.DATE)
	private Date createDate;
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
	 * @return the updateTime
	 */
	
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
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
	public Long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public Date getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Long getClientId() {
		return clientId;
	}
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	
	
	
	
}
