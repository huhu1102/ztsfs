package com.zt.po;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wl
 * @date 2019年4月24日 
 * 生产计划
 */
@Entity
@Table(name = "zt_productionplan")
@org.hibernate.annotations.Table(appliesTo = "zt_productionplan",comment="生产计划")
public class ProductionPlan extends BasePo{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	//关联销售计划详情
//	@OneToMany(cascade =CascadeType.ALL, mappedBy = "productionPlan")
//	private List<ProductionPlanDetails> details;
	/**状态
	 * 1--新建
	 * 2--进行中
	 * 3--完成
	 * 4--撤销
	 */
	@Column(columnDefinition ="varchar(255)COMMENT  '状态'" )
	private Integer status;
	//单号
	@Column(columnDefinition ="varchar(255)COMMENT  '单号'" )
	private String planNo;

//	/**
//	 * 该生产计划的生产状态
//	 * 1-未开始生产，新建
//	 * 2--生产中
//	 * 3--生产完成
//	 */
//	private Integer produceStatus;
	//备注(整单
	@Column(columnDefinition ="varchar(255)COMMENT  '备注'" )
	private String notes;

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
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
//
//	public List<ProductionPlanDetails> getDetails() {
//		return details;
//	}
//
//	public void setDetails(List<ProductionPlanDetails> details) {
//		this.details = details;
//	}


	public String getPlanNo() {
		return planNo;
	}

	public void setPlanNo(String planNo) {
		this.planNo = planNo;
	}

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	public ProductionPlan() {

		super();
//		this.details=new ArrayList<>();
		// TODO Auto-generated constructor stub
	}

}
