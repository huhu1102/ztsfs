package com.zt.po;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

/**
 * 生产任务单编号（）
 * @author  WHL
 * @date 2019-07-09
 */
@Entity
@Table(name="zt_productionplanserialnumber")
@org.hibernate.annotations.Table(appliesTo = "zt_productionplanserialnumber",comment="生产计划详情用的编号")
public class ProductionPlanSerialNumber extends   BasePo{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    /**
     * 起始编号
     */
    private String startNo;
    /**
     * 结束编号
     */
    private String endNo;
    private Long clientId;
    private  Long productId;
    //创建时间
    @Column(columnDefinition ="datetime  COMMENT '创建时间'" )
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @JsonIgnore
    @ManyToOne
    private ProductionPlanDetails  produtionPlan;
    /**
     * 备注
     */
    private String note;
    //是否可见
    private boolean enabled;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStartNo() {
        return startNo;
    }

    public void setStartNo(String startNo) {
        this.startNo = startNo;
    }

    public String getEndNo() {
        return endNo;
    }

    public void setEndNo(String endNo) {
        this.endNo = endNo;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }


    public ProductionPlanDetails getProdutionPlan() {
        return produtionPlan;
    }

    public void setProdutionPlan(ProductionPlanDetails produtionPlan) {
        this.produtionPlan = produtionPlan;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
