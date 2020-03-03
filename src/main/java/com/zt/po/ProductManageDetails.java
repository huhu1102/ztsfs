package com.zt.po;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

/**
 * @author wl
 * @date 2019年5月17日 
 * 生产管理详情
 */
@Entity
@Table(name = "zt_productmanagedetails")
@org.hibernate.annotations.Table(appliesTo = "zt_productmanagedetails",comment="生产管理详情")
public class ProductManageDetails extends BasePo{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	//车间名称
	@Column(columnDefinition ="varchar(255)  COMMENT '车间名称'" )
	private String plant;
	//合格数
	@Column(columnDefinition ="float(10,4)  COMMENT '合格数'" )
	private float qualifiedNo;
	//报废数
	@Column(columnDefinition ="float(10,4)  COMMENT '报废数'" )
	private float junkedNo;

	//计量单位ID
	@Column(columnDefinition ="varchar(10)COMMENT  '计量单位'" )
	private long unitsId ;
	//计量单位
	@ManyToOne
	private SysUnit sysUnit;
	//备注
	@Column(columnDefinition ="varchar(255)  COMMENT '备注'" )
	private String notes;
	//检查员
	private String examineName;
	//车间负责人
	private String workshopManageName;
	//检查日期
	@Column(columnDefinition ="datetime  COMMENT '创建时间'" )
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	//工序步骤id
	@Column(columnDefinition ="bigint(20)  COMMENT '工序id'" )
	private long productProcessId;
	//工序步骤
	@ManyToOne
	private ProductProcess productProcess;
	//关联生产管理
	@JsonIgnore
	@ManyToOne
	private ProductManage productManage;
	//是否可用
	@Column(columnDefinition ="TINYINT(1)  COMMENT '是否可用'" )
	private boolean enabled;

	@Column(columnDefinition ="datetime  COMMENT '更新时间'" )
	@Temporal(TemporalType.TIMESTAMP)
	private Date upsDate;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPlant() {
		return plant;
	}
	public void setPlant(String plant) {
		this.plant = plant;
	}
	
	public float getQualifiedNo() {
		return qualifiedNo;
	}
	public void setQualifiedNo(float qualifiedNo) {
		this.qualifiedNo = qualifiedNo;
	}
	public float getJunkedNo() {
		return junkedNo;
	}
	public void setJunkedNo(float junkedNo) {
		this.junkedNo = junkedNo;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getExamineName() {
		return examineName;
	}
	public void setExamineName(String examineName) {
		this.examineName = examineName;
	}
	public String getWorkshopManageName() {
		return workshopManageName;
	}
	public void setWorkshopManageName(String workshopManageName) {
		this.workshopManageName = workshopManageName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpsDate() {
		return upsDate;
	}

	public void setUpsDate(Date upsDate) {
		this.upsDate = upsDate;
	}

	public ProductProcess getProductProcess() {
		return productProcess;
	}

	public void setProductProcess(ProductProcess productProcess) {
		this.productProcess = productProcess;
	}

	public ProductManage getProductManage() {
		return productManage;
	}
	public void setProductManage(ProductManage productManage) {
		this.productManage = productManage;
	}
	public long getProductProcessId() {
		return productProcessId;
	}
	public void setProductProcessId(long productProcessId) {
		this.productProcessId = productProcessId;
	}

	public long getUnitsId() {
		return unitsId;
	}

	public void setUnitsId(long unitsId) {
		this.unitsId = unitsId;
	}

	public SysUnit getSysUnit() {
		return sysUnit;
	}

	public void setSysUnit(SysUnit sysUnit) {
		this.sysUnit = sysUnit;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
