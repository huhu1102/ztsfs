package com.zt.po;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="zt_color")
@org.hibernate.annotations.Table(appliesTo = "zt_color",comment="颜色")
public class Color extends BasePo{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //名称
    @Column(columnDefinition ="varchar(255)  COMMENT '颜色名称.'" )
    private String name;

    private String code;

    //是否可用
    @Column(columnDefinition ="TINYINT(1)  COMMENT '是否可用'" )
    private Boolean enabled;
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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
