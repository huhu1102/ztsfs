package com.zt.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author wl
 * @date 2019年6月5日 
 * 发货单
 */
@Entity
@Table(name="zt_shippingbill")
@org.hibernate.annotations.Table(appliesTo = "zt_shippingbill",comment="发货单")
public class ShippingBill extends BasePo{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	//生产管理Id
	private long shippingRequestDetailsId;
	//客户Id
	@Column(columnDefinition ="bigint(20)  COMMENT '货运部.'" )
	private long clientId;
	//关联客户
	@ManyToOne
	private Client client;
	//单号
	@Column(columnDefinition ="varchar(255)  COMMENT '单号.'" )
	private String dispatchBillNo;
	//件数
	@Column(columnDefinition ="int(10)  COMMENT '件数.'" )
	private Integer boxs;
	//请求发货数量
	@Column(columnDefinition ="float(21,4) COMMENT  '请求发货数量'" )
	private float planNumber;
	//时间
	@JsonIgnore
	@Column(columnDefinition ="datetime  COMMENT '创建时间'" )
	@CreatedDate
	@JsonFormat(pattern="yyyy-MM-dd hh:mm:ss")
	private Date createDate;
	//收货人
	@Column(columnDefinition ="varchar(255)  COMMENT '收货人.'" )
	private String consignee;
	//收货人电话
	@Column(columnDefinition ="varchar(255)  COMMENT '收货人电话.'" )
	private String consigneePhone;
	//收货地址
	@Column(columnDefinition ="varchar(255)  COMMENT '收货地址.'" )
	private String address;
	//发货人
	@Column(columnDefinition ="varchar(255)  COMMENT '发货人.'" )
	private String consigner;
	//发货人电话
	@Column(columnDefinition ="varchar(255)  COMMENT '发货人电话.'" )
	private String consignerPhone;
	//备注
	@Column(columnDefinition ="varchar(255)  COMMENT '备注.'" )
	private String remarks;
	/**
     * 状态
     * 1.运输中
     * 2.已到达
     * 3.已签收
     */
    private Integer shipingStatus;

	/**产品详情
	 * 客户名
	 * 产品名
	 * 数量
	 * 规格
	 * 颜色
	 */
	@Column(columnDefinition ="varchar(255)  COMMENT '产品详情.'" )
	private String proDetails;
	//货名
	@Column(columnDefinition ="varchar(255)  COMMENT '货名.'" )
	private String cargoName;
	//运费
	@Column(columnDefinition ="decimal(19)  COMMENT '运费.'" )
	private BigDecimal freights;
	//上门费
	@Column(columnDefinition ="decimal(19)  COMMENT '上门费.'" )
	private BigDecimal shortHaulMoney;
	//运费详情
	@Column(columnDefinition ="varchar(255)  COMMENT '运费详情.'" )
	private String freightDetails;
	//代收货款
	@Column(columnDefinition ="decimal(19)  COMMENT '代收货款.'" )
	private BigDecimal collFreight;
	//代收货款大写
	@Column(columnDefinition ="varchar(255)  COMMENT '代收货款大写.'" )
	private String collFreightBig;
	//服务方式
	@Column(columnDefinition ="varchar(255)  COMMENT '服务方式.'" )
	private String  serviceCode;
	//回单信息(是否有回单)
	@Column(columnDefinition ="varchar(255)  COMMENT '回单信息'" )
	private String receipt;
	//到站总应收钱
	@Column(columnDefinition ="decimal(19)  COMMENT '到站总应收.'" )
	private BigDecimal totalMoney;
	//是否可用
	@Column(columnDefinition ="TINYINT(1)  COMMENT '是否可见'" )
	private boolean enabled;
	//户名
	@Column(columnDefinition ="varchar(255)  COMMENT '户名.'" )
	private String userName;
	//卡号
	@Column(columnDefinition ="varchar(255)  COMMENT '卡号.'" )
	private String cardNo;
	//付款类型 现金，刷卡 支付宝，微信。。。。
	@Column(columnDefinition ="varchar(255)  COMMENT '付款类型.'" )
	private String stype;
	@JsonIgnore
	@ManyToMany
    @JoinTable(name = "zt_productManager_shippingBill",
            joinColumns = @JoinColumn(name = "bill_id"),
            inverseJoinColumns = @JoinColumn(name = "manage_id"))
	private List<ProductManage> manageList;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getClientId() {
		return clientId;
	}

	public void setClientId(long clientId) {
		this.clientId = clientId;
	}

	public String getDispatchBillNo() {
		return dispatchBillNo;
	}
	public void setDispatchBillNo(String dispatchBillNo) {
		this.dispatchBillNo = dispatchBillNo;
	}
	public int getBoxs() {
		return boxs;
	}
	public void setBoxs(int boxs) {
		this.boxs = boxs;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getConsigneePhone() {
		return consigneePhone;
	}
	public void setConsigneePhone(String consigneePhone) {
		this.consigneePhone = consigneePhone;
	}
	public String getConsigner() {
		return consigner;
	}
	public void setConsigner(String consigner) {
		this.consigner = consigner;
	}
	public String getConsignerPhone() {
		return consignerPhone;
	}
	public void setConsignerPhone(String consignerPhone) {
		this.consignerPhone = consignerPhone;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getProDetails() {
		return proDetails;
	}
	public void setProDetails(String proDetails) {
		this.proDetails = proDetails;
	}
	public String getCargoName() {
		return cargoName;
	}
	public void setCargoName(String cargoName) {
		this.cargoName = cargoName;
	}
	public BigDecimal getFreights() {
		return freights;
	}
	public void setFreights(BigDecimal freights) {
		this.freights = freights;
	}
	public String getFreightDetails() {
		return freightDetails;
	}
	public void setFreightDetails(String freightDetails) {
		this.freightDetails = freightDetails;
	}

	public BigDecimal getCollFreight() {
		return collFreight;
	}

	public void setCollFreight(BigDecimal collFreight) {
		this.collFreight = collFreight;
	}

	public String getCollFreightBig() {
		return collFreightBig;
	}

	public void setCollFreightBig(String collFreightBig) {
		this.collFreightBig = collFreightBig;
	}


	public String getReceipt() {
		return receipt;
	}
	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}
	public BigDecimal getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getStype() {
		return stype;
	}
	public void setStype(String stype) {
		this.stype = stype;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public BigDecimal getShortHaulMoney() {
		return shortHaulMoney;
	}
	public void setShortHaulMoney(BigDecimal shortHaulMoney) {
		this.shortHaulMoney = shortHaulMoney;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public long getShippingRequestDetailsId() {
		return shippingRequestDetailsId;
	}

	public void setShippingRequestDetailsId(long shippingRequestDetailsId) {
		this.shippingRequestDetailsId = shippingRequestDetailsId;
	}

	public void setBoxs(Integer boxs) {
		this.boxs = boxs;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public Integer getShipingStatus() {
		return shipingStatus;
	}

	public void setShipingStatus(Integer shipingStatus) {
		this.shipingStatus = shipingStatus;
	}

	public float getPlanNumber() {
		return planNumber;
	}

	public void setPlanNumber(float planNumber) {
		this.planNumber = planNumber;
	}

    public List<ProductManage> getManageList() {
        return manageList;
    }

    public void setManageList(List<ProductManage> manageList) {
        this.manageList = manageList;
    }

    @Override
    public String toString() {
        return "ShippingBill{" +
                "id=" + id +
                ", shippingRequestDetailsId=" + shippingRequestDetailsId +
                ", clientId=" + clientId +
                ", client=" + client +
                ", dispatchBillNo='" + dispatchBillNo + '\'' +
                ", boxs=" + boxs +
                ", planNumber=" + planNumber +
                ", createDate=" + createDate +
                ", consignee='" + consignee + '\'' +
                ", consigneePhone='" + consigneePhone + '\'' +
                ", address='" + address + '\'' +
                ", consigner='" + consigner + '\'' +
                ", consignerPhone='" + consignerPhone + '\'' +
                ", remarks='" + remarks + '\'' +
                ", shipingStatus=" + shipingStatus +
                ", proDetails='" + proDetails + '\'' +
                ", cargoName='" + cargoName + '\'' +
                ", freights=" + freights +
                ", shortHaulMoney=" + shortHaulMoney +
                ", freightDetails='" + freightDetails + '\'' +
                ", collFreight=" + collFreight +
                ", collFreightBig='" + collFreightBig + '\'' +
                ", serviceCode='" + serviceCode + '\'' +
                ", receipt='" + receipt + '\'' +
                ", totalMoney=" + totalMoney +
                ", enabled=" + enabled +
                ", userName='" + userName + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", stype='" + stype + '\'' +
                '}';
    }
}
