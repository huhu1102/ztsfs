/**
 * 
 */
package com.zt.po;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wl
 * @date 2019年4月15日 
 * 客户信息实体类
 */
@Entity
@Table(name="zt_client")
@EntityListeners(AuditingEntityListener.class)
@org.hibernate.annotations.Table(appliesTo = "zt_client",comment="客户信息")
public class Client extends BasePo{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	//客户名称 --站名
	@Column(columnDefinition ="varchar(255)  COMMENT '客户名称.'" )
	private String name;
	//简称
	@Column(columnDefinition ="varchar(255)  COMMENT '简称.'" )
	private String abbreviation;
	//客户地址(县级)
	@Column(columnDefinition ="varchar(255)  COMMENT '客户地址.'" )
	private String address;
	//客户详细地址(具体位置)
	@Column(columnDefinition ="varchar(255)  COMMENT '客户地址.'" )
	private String addressDetails;
	//客户电话
	@Column(columnDefinition ="varchar(255)  COMMENT '客户电话.'" )
	private String phone;
	//客户传真
	@Column(columnDefinition ="varchar(255)  COMMENT '客户传真.'" )
	private String fax;
	//客户邮箱
	@Column(columnDefinition ="varchar(255)  COMMENT '客户邮箱.'" )
	private String email;
	/**
	 * 客户种类 唯一 流派
	 */
	private  String types;

	@ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "zt_client_uploadfiles",joinColumns = {@JoinColumn(name = "cli_id")},
            inverseJoinColumns ={@JoinColumn(name = "upload_id")})
	List<UploadFile> uploadFiles;

	//客户等级
	private int grade;

	//该客户定制过的产品
	private String productStr;

	//产品
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="zt_client_product",joinColumns = {@JoinColumn(name="c_id")}
				,inverseJoinColumns = {@JoinColumn(name="p_id")})
	private List<Product> products;
	//客户状态(上游/下游)
	@Column(columnDefinition ="varchar(255)  COMMENT '客户标识.'" )
	private String status;
	//创建时间	
	@Column(columnDefinition ="datetime  COMMENT '创建时间'" )
	@CreatedDate
	@JsonFormat(pattern="yyyy-MM-dd hh:mm:ss")
	private Date createDate;
	@JsonIgnore
	//是否可用	
	@Column(columnDefinition ="TINYINT(1)  COMMENT '是否可用'" )
	private Boolean enabled;
	//子公司
	@OneToMany(cascade = CascadeType.ALL)
	private List<Client> child ;
	//父公司
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "parent_id")
	private Client parent;
	/**
	  * 父公司名称
	 */
	@Column(columnDefinition ="varchar(255)  COMMENT '父公司名称.'" )
	private String  parentName;
	/**
	 * 父公司id
	 */
	@Column(columnDefinition ="bigint(20) COMMENT '父公司ID.'" )
	private long  parentClientId;
	/**
	 * 公司简介
	 */
	@Column(columnDefinition ="varchar(255) COMMENT '公司简介.'" )
	private String infor;
	//备注
	private String notes;
	public long getId() {
		return id;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAbbreviation() {
		return abbreviation;
	}
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public List<UploadFile> getUploadFiles() {
		return uploadFiles;
	}

	public void setUploadFiles(List<UploadFile> uploadFiles) {
		this.uploadFiles = uploadFiles;
	}

	/**
	 * @return the child
	 */
	public List<Client> getChild() {
		return child;
	}
	/**
	 * @param child the child to set
	 */
	public void setChild(List<Client> child) {
		this.child = child;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public boolean isEnabled() {
		return enabled;
	}
	
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the parentName
	 */
	public String getParentName() {
		return parentName;
	}
	/**
	 * @param parentName the parentName to set
	 */
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	/**
	 * @return the infor
	 */
	public String getInfor() {
		return infor;
	}
	/**
	 * @param infor the infor to set
	 */
	public void setInfor(String infor) {
		this.infor = infor;
	}
	
	/**
	 * 
	 */
	public Client() {
		super();
//		this.product=new ArrayList<Product>();
		this.child=new ArrayList<>();
		
	}
	/**
	 * @return the parentClientId
	 */
	public long getParentClientId() {
		return parentClientId;
	}
	/**
	 * @param parentClientId the parentClientId to set
	 */
	public void setParentClientId(long parentClientId) {
		this.parentClientId = parentClientId;
	}
	/**
	 * @return the parent
	 */
	public Client getParent() {
		return parent;
	}
	/**
	 * @param parent the parent to set
	 */
	public void setParent(Client parent) {
		this.parent = parent;
	}
	public String getAddressDetails() {
		return addressDetails;
	}
	public void setAddressDetails(String addressDetails) {
		this.addressDetails = addressDetails;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public String getProductStr() {
		return productStr;
	}

	public void setProductStr(String productStr) {
		this.productStr = productStr;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
}
