package com.zt.po;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author wl
 * @date 2019年4月18日
 * 销售订单
 */
@Entity
@Table(name="zt_salesorder")
@org.hibernate.annotations.Table(appliesTo = "zt_salesorder",comment="销售订单")
public class SalesOrder extends BasePo {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	//订单编号
	@Column(columnDefinition ="varchar(255)  COMMENT '订单编号.'" )
	private String orderNo;

	//负责人
	@ManyToOne
	@JoinColumn(name="employeeId")
	private Employee employee;
	
	//负责人
	@Column(columnDefinition ="bigint(20)  COMMENT '负责人.'" )
	private Long empId;
	//客户
	@ManyToOne
	@JoinColumn(name="clienteleid")
	private Client cliente;
	//客户Id
	@Column(columnDefinition ="bigint(20)  COMMENT '客户Id.'" )
	private Long cliId;
    //前台展示使用；
	private String clientArr;
	//总金额
	@Column(columnDefinition ="decimal(19)  COMMENT '总金额.'" )
	private  BigDecimal totalMoney;
	//签约时间
	@Column(columnDefinition ="datetime COMMENT '签约时间'" )
	@Temporal(TemporalType.TIMESTAMP)
	private Date signContractDate;

	private boolean hasContract;//是否有合同

	//关联合同
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="zt_salesorder_contract",joinColumns={@JoinColumn(name="s_id")}
			,inverseJoinColumns={@JoinColumn(name="c_id")})
	private List<Contract> contracts;

	//合同名称
	private String contractName;
	//合同存贮路径：：
	private String contractUrl;
	//订单状态
	/**
	 1.新建
	 2.完成
	 3.撤销
	 */
	@Column(columnDefinition ="varchar(255)  COMMENT '订单状态.'" )
	private Integer orderStatus;
	@Column(columnDefinition ="varchar(255)  COMMENT '税收.'" )
	private  Float  tax;


	
	//关联订单详情
	@OneToMany(mappedBy = "salesOrder",cascade = CascadeType.ALL)
	private List<SalesOrderDetails> salesOrderDetails;
	//备注
	private String notes;
	//是否可用
	@Column(columnDefinition ="TINYINT(1)  COMMENT '是否可用'" )
	private boolean enabled;
	//创建时间
	@Column(columnDefinition ="datetime COMMENT '创建时间'" )
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}



	public Client getCliente() {
		return cliente;
	}
	public void setCliente(Client cliente) {
		this.cliente = cliente;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public List<Contract> getContracts() {
		return contracts;
	}

	public void setContracts(List<Contract> contracts) {
		this.contracts = contracts;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Long getEmpId() {
		return empId;
	}
	public void setEmpId(Long empId) {
		this.empId = empId;
	}
	public Long getCliId() {
		return cliId;
	}
	public void setCliId(Long cliId) {
		this.cliId = cliId;
	}

	public BigDecimal getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}
	public Date getSignContractDate() {
		return signContractDate;
	}
	public void setSignContractDate(Date signContractDate) {
		this.signContractDate = signContractDate;
	}


	public List<SalesOrderDetails> getSalesOrderDetails() {
		return salesOrderDetails;
	}

	public void setSalesOrderDetails(List<SalesOrderDetails> salesOrderDetails) {
		this.salesOrderDetails = salesOrderDetails;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getClientArr() {
		return clientArr;
	}

	public void setClientArr(String clientArr) {
		this.clientArr = clientArr;
	}

    public boolean isHasContract() {
        return hasContract;
    }

    public void setHasContract(boolean hasContract) {
        this.hasContract = hasContract;
    }

	public Float getTax() {
		return tax;
	}

	public void setTax(Float tax) {
		this.tax = tax;
	}

	public String getContractUrl() {
		return contractUrl;
	}

	public void setContractUrl(String contractUrl) {
		this.contractUrl = contractUrl;
	}
}
