package com.zt.po;



import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author wl
 * @date 2019年4月17日 
 * 产品的实体类
 */
@Entity
@Table(name="zt_products")
@org.hibernate.annotations.Table(appliesTo = "zt_products",comment="成品实体类")
public class Product extends BasePo{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	//产品名称
	@Column(columnDefinition ="varchar(255) COMMENT '产品名称'" )
	private String producteName;
	//产品编号
	@Column(columnDefinition ="varchar(255) COMMENT '产品编号'" )
	private String serialNumber;
	//产品规格
	@Column(columnDefinition ="varchar(255)COMMENT  '产品规格'" )
	private String specification;
	@Column(columnDefinition ="varchar(255)COMMENT  '产品规格名称'" )
	private String specName;
	//产品用途
	@Column(columnDefinition ="varchar(255)COMMENT  '产品用途'" )
	private String proType;

	private String imageUrl;

	private String uploadName;
	//颜色
	@Column(columnDefinition ="varchar(25)  COMMENT'颜色'" )
	private String color;
	//颜色名字
	@Column(columnDefinition ="varchar(25)  COMMENT'颜色名字'" )
	private String colorName;
	//备注
	@Column(columnDefinition ="varchar(25)  COMMENT'备注'" )
	private String note;

	//是否可用
	@JsonIgnore
	@Column(columnDefinition ="TINYINT(1)  COMMENT '是否可用'" )
	private boolean enabled;
	//单位Id
	private long sysUnitId;
	//关联单位
	@ManyToOne
	private SysUnit sysUnit;
	//颜色
	//JoinTable的name是中间表的名字
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="zt_produt_color",joinColumns={@JoinColumn(name="p_id")}
			,inverseJoinColumns={@JoinColumn(name="c_id")})
	private List<Color> colors;
	//规格
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="zt_produt_specs",joinColumns={@JoinColumn(name="p_id")}
			,inverseJoinColumns={@JoinColumn(name="s_id")})
	private List<Specification> specs;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="zt_produt_use",joinColumns={@JoinColumn(name="p_id")}
			,inverseJoinColumns={@JoinColumn(name="u_id")})
	private List<ProductUse> uses;
	//半成品
	@ManyToMany(cascade = CascadeType.ALL,fetch =FetchType.LAZY )
    @JoinTable(name="zt_produt_mid",joinColumns = {@JoinColumn(name="p_id")}
    ,inverseJoinColumns = {@JoinColumn(name="m_id")})
	private List<MidMaterial> midMaterials;
	//创建记录时间
	 @JsonIgnore
	@Column(columnDefinition ="datetime COMMENT '创建时间'" )
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	//关联工序
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="zt_produt_progress",joinColumns = {@JoinColumn(name="p_id")}
			,inverseJoinColumns = {@JoinColumn(name="progress_id")})
	private List<ProductPreProcess>  preprosess;

	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public String getUploadName() {
		return uploadName;
	}

	public void setUploadName(String uploadName) {
		this.uploadName = uploadName;
	}

	public String getProducteName() {
		return producteName;
	}
	public void setProducteName(String producteName) {
		this.producteName = producteName;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getSpecification() {
		return specification;
	}
	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getProType() {
		return proType;
	}
	public void setProType(String proType) {
		this.proType = proType;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	/**
	 * @return the midMaterials
	 */
	public List<MidMaterial> getMidMaterials() {
		return midMaterials;
	}
	/**
	 * @param midMaterials the midMaterials to set
	 */
	public void setMidMaterials(List<MidMaterial> midMaterials) {
		this.midMaterials = midMaterials;
	}




	public String getSpecName() {
		return specName;
	}

	public void setSpecName(String specName) {
		this.specName = specName;
	}

	public long getSysUnitId() {
		return sysUnitId;
	}

	public void setSysUnitId(long sysUnitId) {
		this.sysUnitId = sysUnitId;
	}

	public SysUnit getSysUnit() {
		return sysUnit;
	}

	public void setSysUnit(SysUnit sysUnit) {
		this.sysUnit = sysUnit;
	}

	public List<Specification> getSpecs() {
		return specs;
	}

	public void setSpecs(List<Specification> specs) {
		this.specs = specs;
	}

	public List<Color> getColors() {
		return colors;
	}

	public void setColors(List<Color> colors) {
		this.colors = colors;
	}

	public List<ProductPreProcess> getPreprosess() {
		return preprosess;
	}

	public void setPreprosess(List<ProductPreProcess> preprosess) {
		this.preprosess = preprosess;
	}

	public String getColorName() {
		return colorName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public List<ProductUse> getUses() {
		return uses;
	}

	public void setUses(List<ProductUse> uses) {
		this.uses = uses;
	}
}
