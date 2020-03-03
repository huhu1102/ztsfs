package com.zt.po;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="zt_productmanageprogress")
@org.hibernate.annotations.Table(appliesTo = "zt_productmanageprogress",comment="生产管理详情汇总")

public class ProductManageProgress {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    //数量
    private  float quantity;
    //时间
    @Column(columnDefinition ="datetime  COMMENT '更新时间'" )
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    //工序步骤ID
    private Long productProcessId;
    //工序步骤对象
    @ManyToOne
    private ProductProcess productProcess;

    private long productManageId;
    @JsonIgnore
    @ManyToOne
    private ProductManage productManage;
   /**
   单位*/
   @ManyToOne
    private SysUnit  unit;
    private  Long  unitId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getProductProcessId() {
        return productProcessId;
    }

    public void setProductProcessId(Long productProcessId) {
        this.productProcessId = productProcessId;
    }

    public ProductProcess getProductProcess() {
        return productProcess;
    }

    public void setProductProcess(ProductProcess productProcess) {
        this.productProcess = productProcess;
    }

    public SysUnit getUnit() {
        return unit;
    }

    public void setUnit(SysUnit unit) {
        this.unit = unit;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public ProductManage getProductManage() {
        return productManage;
    }

    public void setProductManage(ProductManage productManage) {
        this.productManage = productManage;
    }

    public long getProductManageId() {
        return productManageId;
    }

    public void setProductManageId(long productManageId) {
        this.productManageId = productManageId;
    }
}
