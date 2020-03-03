package com.zt.po;

import javax.persistence.*;
import java.util.Date;


/**
 * @author wl
 * @date 2019年5月6日 
 * 质保金的实体类
 */
@Entity
@Table(name="zt_qualitydeposit")
public class QualityDeposit extends BasePo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	//客户名称
	private String clieName;
	//合同编号
	private String orderNumber;
	//是否有质保金
	private boolean deposit;
	
	//质保金的状态
	private String qStatus;
	
	//质保金的截止时间
	@Column(columnDefinition ="datetime  COMMENT '质保金的截止时间'" )
	@Temporal(TemporalType.TIMESTAMP)
	private Date qualityDate;
	
	//备注
	@Column(columnDefinition ="varchar(255)COMMENT  '备注'" )
	private String remarks;
	
	//是否可用
	@Column(columnDefinition ="TINYINT(1)  COMMENT '是否可用'" )
	private boolean enabled;
	
	//创建时间
	@Column(columnDefinition ="datetime  COMMENT '创建时间'" )
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isDeposit() {
		return deposit;
	}

	public void setDeposit(boolean deposit) {
		this.deposit = deposit;
	}

	public String getqStatus() {
		return qStatus;
	}

	public void setqStatus(String qStatus) {
		this.qStatus = qStatus;
	}

	public Date getQualityDate() {
		return qualityDate;
	}

	public void setQualityDate(Date qualityDate) {
		this.qualityDate = qualityDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getClieName() {
		return clieName;
	}

	public void setClieName(String clieName) {
		this.clieName = clieName;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	
}
