package com.zt.po;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "zt_salesplan")
@org.hibernate.annotations.Table(appliesTo = "zt_salesplan", comment = "销售计划")
public class SalesPlan extends BasePo {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    //客户名称
    @ManyToOne
    private Client client;

    @Column(columnDefinition = "varchar(255)COMMENT  '客户名称'")
    private String clientName;

    //客户id
    @Column(columnDefinition = "bigint(20)COMMENT  '客户Id'")
    private long clientId;
    //产品名称
    @Column(columnDefinition = "varchar(255)COMMENT  '产品名称'")
    private String productName;
    //产品型号
    @Column(columnDefinition = "varchar(255)COMMENT  '产品型号'")
    private String productNo;
    //简称
    @Column(columnDefinition = "varchar(255)COMMENT  '简称'")
    private String abbr;
    //规格
    @ManyToOne
    private Specification specification;
    //规格Id
    private Long specsId;
    @ManyToOne
    private Color color;

    private Long colorId;
    //关联产品表
    @ManyToOne  //代表是一对多关联
    @JoinColumn(name = "productId")  //关联的字段
    private Product product;
    //产品Id
    private long proId;

    //期望发货数量
    @Column(columnDefinition = "float(20,4)COMMENT  '期望发货数量'")
    private float quantity;

    //生产完成数量
    @Column(columnDefinition = "float(20,4)COMMENT  '生产完成数量'")
    private float endQuantity;


    //已下发到生产计划的数量
    @Column(columnDefinition = "float(20,4)COMMENT  '已下发到生产计划数量'")
    private float nextQuantity;

    //发货数量
    @Column(columnDefinition = "float(20,4)COMMENT  '已发货数量'")
    private float shippingQuantity;

    //发货请求Id
    private Long resendId;
    /**
     * 最近一次请求发货数量
     * */
    @Column(columnDefinition = "float(20,4)COMMENT  '最近一次请求发货数量'")
    private float resendNo;
    /**
     * 发货请求描述
     * */
    @Column(columnDefinition = "varchar(255)COMMENT  '发货请求描述文字'")
    private String resendStr;
    //发货记录
    @Column(columnDefinition = "varchar(255)COMMENT  '发货记录'")
    private String shippingStr;
    //完成记录
    @Column(columnDefinition = "varchar(255)COMMENT  '完成记录'")
    private String produtRecordStr;
    //编号
    private String serialNumber;

    //操作者
    @ManyToOne  //代表是一对多关联
    private Employee editer;
    //记录人Id
    private Long editerId;
    /**
     * 96+
     * 发货等级
     * 1--紧急
     * 2--加急
     * 3--普通
     */
    private Integer expectLevel;

    //备注
    @Column(columnDefinition = "varchar(255)COMMENT  '备注'")
    private String notes;

    /**
     * 关联的生产计划状态
     * 下发状态
     * 1 未下发                     *
     * 2 下发未完成                 *
     * 3 下发完成
     * 4 撤销
     */
    private Integer planStatus;

    /**
     * 下发记录
     */
    @Column(columnDefinition = "varchar(255)COMMENT  '下发记录'")
    private String planString;

    /**
     * 销售计划状态
     * 1.新建
     * 2.进行中
     * 3.完成（整个流程完成  即订单完成或者合同完成）
     * 4.撤销
     */
    private Integer status;

    /**
     * 关联到生产管理
     * 生产进度状态
     * 1.生产中
     * 2.生产完成
     * 4.撤销
     */
    private Integer manageStatus;

    /**
     * 发货状态
     * 1.未发货状态
     * 2.有发货状态未完成
     * 3.发货完成
     * 4.撤销
     */
    private Integer shippingStatus;

    //是否可用
    @Column(columnDefinition = "TINYINT(1)  COMMENT '是否可用'")
    private boolean enabled;

    //发货请求的时间
    @Column(columnDefinition = "datetime  COMMENT '发货请求时间'")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sendDate;

    //更新时间 该时】
    // 间用于显示先后顺序 当该销售订单由变动时更新该数据，以便其他人第一时间能看到该销售计划的变动
    @Column(columnDefinition = "datetime  COMMENT '更新时间'")
    @Temporal(TemporalType.TIMESTAMP)
    private Date upsDate;

    //创建时间
    @Column(columnDefinition = "datetime  COMMENT '创建时间'")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    /**
     * 客户的  父子编号 e.g‘1,2,3’
     */
    private String pcCode;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public String getProductName() {
        return productName;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Long getColorId() {
        return colorId;
    }

    public void setColorId(Long colorId) {
        this.colorId = colorId;
    }


    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public Employee getEditer() {
        return editer;
    }

    public void setEditer(Employee editer) {
        this.editer = editer;
    }

    public Long getEditerId() {
        return editerId;
    }

    public void setEditerId(Long editerId) {
        this.editerId = editerId;
    }


    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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

    public long getProId() {
        return proId;
    }

    public void setProId(long proId) {
        this.proId = proId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getSpecsId() {
        return specsId;
    }

    public void setSpecsId(Long specsId) {
        this.specsId = specsId;
    }

    public Specification getSpecification() {
        return specification;
    }

    public void setSpecification(Specification specification) {
        this.specification = specification;
    }

    public Integer getExpectLevel() {
        return expectLevel;
    }

    public void setExpectLevel(Integer expectLevel) {
        this.expectLevel = expectLevel;
    }

    public float getEndQuantity() {
        return endQuantity;
    }

    public void setEndQuantity(float endQuantity) {
        this.endQuantity = endQuantity;
    }

    public float getNextQuantity() {
        return nextQuantity;
    }

    public void setNextQuantity(float nextQuantity) {
        this.nextQuantity = nextQuantity;
    }

    public Integer getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(Integer planStatus) {
        this.planStatus = planStatus;
    }

    public String getPlanString() {
        return planString;
    }

    public void setPlanString(String planString) {
        this.planString = planString;
    }

    public String getPcCode() {
        return pcCode;
    }

    public void setPcCode(String pcCode) {
        this.pcCode = pcCode;
    }

    public Integer getManageStatus() {
        return manageStatus;
    }

    public void setManageStatus(Integer manageStatus) {
        this.manageStatus = manageStatus;
    }

    public Integer getShippingStatus() {
        return shippingStatus;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public void setShippingStatus(Integer shippingStatus) {
        this.shippingStatus = shippingStatus;
    }

    public float getShippingQuantity() {
        return shippingQuantity;
    }

    public void setShippingQuantity(float shippingQuantity) {
        this.shippingQuantity = shippingQuantity;
    }

    public String getShippingStr() {
        return shippingStr;
    }

    public void setShippingStr(String shippingStr) {
        this.shippingStr = shippingStr;
    }
    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }


    public float getResendNo() {
        return resendNo;
    }

    public void setResendNo(float resendNo) {
        this.resendNo = resendNo;
    }

    public String getResendStr() {
        return resendStr;
    }

    public void setResendStr(String resendStr) {
        this.resendStr = resendStr;
    }

    public Long getResendId() {
        return resendId;
    }

    public void setResendId(Long resendId) {
        this.resendId = resendId;
    }


    public Date getUpsDate() {
        return upsDate;
    }

    public void setUpsDate(Date upsDate) {
        this.upsDate = upsDate;
    }

    public String getProdutRecordStr() {
        return produtRecordStr;
    }

    public void setProdutRecordStr(String produtRecordStr) {
        this.produtRecordStr = produtRecordStr;
    }
}
