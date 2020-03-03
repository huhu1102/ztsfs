package com.zt.po;



import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="zt_uploadfile")
@org.hibernate.annotations.Table(appliesTo = "zt_uploadfile",comment="附件信息")
public class UploadFile extends BasePo{

    /**
     *
     */

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(columnDefinition ="varchar(255)  COMMENT '文件名称.'")
    private String name; // 文件名
    @Column(columnDefinition ="varchar(255)  COMMENT 'URL.'")
    private String url;
    @Column(columnDefinition ="datetime COMMENT '上传时间'" )
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime; // 上传时间
    private String contentType; // 文件类型
    private long size; // 文件大小
    //是否可用
    @Column(columnDefinition ="TINYINT(1)  COMMENT '是否可用'" )
    private Boolean enabled;

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

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
