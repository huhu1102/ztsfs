package com.zt.po;



import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

/**
 * @author wl
 * @date 2019年4月17日 
 * 工序步骤实体类
 */
@Entity
@Table(name= "zt_productprocess")
@org.hibernate.annotations.Table(appliesTo = "zt_productprocess",comment="工序实体类")
public class ProductProcess extends BasePo{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	//工序步骤名称
	@Column(columnDefinition ="varchar(255) COMMENT '工序步骤名称'" )
	private String name;
	//工序步骤编号
	@Column(columnDefinition ="varchar(255) COMMENT '工序步骤名称'" )
	private String proProNumber;
	//公司标识
	//步骤顺序号
	@Column(columnDefinition ="varchar(255) COMMENT '工步顺序号'" )
	private long stepNumber;
	//是否可用
	@JsonIgnore
	@Column(columnDefinition ="TINYINT(1)  COMMENT '是否可用'" )
	private boolean enabled;
	
	//工序步骤名称的唯一标识   first   END 等等  对应worksetp的code值 相等；
	@Column(columnDefinition ="varchar(255) COMMENT '工序步骤名称的唯一标识'" )
	private String productProcessCode;
	//工序
	 @JsonIgnore
	@ManyToOne
	private ProductPreProcess preProcess;
	//描述工序步骤
	@ManyToOne
	private Workstep workstep;
	//创建记录时间
	@JsonIgnore
	@Column(columnDefinition ="datetime COMMENT '创建时间'" )
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	public Date getCreateDate() {
		return createDate;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public Workstep getWorkstep() {
		return workstep;
	}
	public void setWorkstep(Workstep workstep) {
		this.workstep = workstep;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the preProcess
	 */
	public ProductPreProcess getPreProcess() {
		return preProcess;
	}
	/**
	 * @param preProcess the preProcess to set
	 */
	public void setPreProcess(ProductPreProcess preProcess) {
		this.preProcess = preProcess;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public long getStepNumber() {
		return stepNumber;
	}
	public void setStepNumber(long stepNumber) {
		this.stepNumber = stepNumber;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public String getProductProcessCode() {
		return productProcessCode;
	}
	public void setProductProcessCode(String productProcessCode) {
		this.productProcessCode = productProcessCode;
	}
	public String getProProNumber() {
		return proProNumber;
	}
	public void setProProNumber(String proProNumber) {
		this.proProNumber = proProNumber;
	}
	
	
}
