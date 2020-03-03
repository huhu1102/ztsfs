package com.zt.po;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

/**
规格
 */
@Entity
@Table(name="zt_specification")
@org.hibernate.annotations.Table(appliesTo = "zt_specification",comment="规格")
public class Specification extends BasePo{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //规格名称
    @Column(columnDefinition ="varchar(255)  COMMENT '规格名称.'" )
    private  String name;
    private String code;

    //是否可用
    @JsonIgnore
    @Column(columnDefinition ="TINYINT(1)  COMMENT '是否可用'" )
    private boolean enable;

    @Column(columnDefinition="datetime  COMMENT'出库时间'")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    public long getId() {
        return id;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
