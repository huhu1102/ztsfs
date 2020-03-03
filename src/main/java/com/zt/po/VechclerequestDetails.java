/**
 * 
 */
package com.zt.po;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

/**
 * @author yh
 * @date 2019年5月20日
 */
@Entity
@Table(name="zt_vechclerequestdetails")
@org.hibernate.annotations.Table(appliesTo = "zt_vechclerequestdetails",comment="申请详情表")
public class VechclerequestDetails extends BasePo{

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long  id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date requestTime;
	/**
	 *请求相关联Id
	 */
	@Column(columnDefinition ="bigint(20)  COMMENT '请求相关联Id.' ")
	private  Long requestId ;
	@Column(columnDefinition ="varchar(255)  COMMENT '车辆状态.' ")
	private String  requsetState;
	@JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Vechclerequest requestDetail;
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
	 * @return the requestTime
	 */
	public Date getRequestTime() {
		return requestTime;
	}
	/**
	 * @param requestTime the requestTime to set
	 */
	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}
	

	/**
	 * @return the requestId
	 */
	public Long getRequestId() {
		return requestId;
	}
	/**
	 * @param requestId the requestId to set
	 */
	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}
	/**
	 * @return the requsetState
	 */
	public String getRequsetState() {
		return requsetState;
	}
	/**
	 * @param requsetState the requsetState to set
	 */
	public void setRequsetState(String requsetState) {
		this.requsetState = requsetState;
	}
	@Override
	public String toString() {
		return "VechclerequestDetails [id=" + id + ", requestTime=" + requestTime + ",  requsetState="
				+ requsetState + "]";
	}
	/**
	 * @return the requestDetail
	 */
	public Vechclerequest getRequestDetail() {
		return requestDetail;
	}
	/**
	 * @param requestDetail the requestDetail to set
	 */
	public void setRequestDetail(Vechclerequest requestDetail) {
		this.requestDetail = requestDetail;
	}
	
	
}
