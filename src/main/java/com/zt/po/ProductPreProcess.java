package com.zt.po;



import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author wl
 * @date 2019年4月17日 
 * 工序实体类
 */
@Entity
@Table(name= "zt_productpreprocess")
@org.hibernate.annotations.Table(appliesTo = "zt_productpreprocess",comment="工序实体类")
public class ProductPreProcess extends BasePo{
	

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	//工序名称
	@Column(columnDefinition ="varchar(255) COMMENT '工序名称'" )
	private String name;
	//是否可用
	@JsonIgnore
	@Column(columnDefinition ="TINYINT(1)  COMMENT '是否可用'" )
	private boolean enabled;
	//工序步骤描述
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy="preProcess")
	private List<ProductProcess> process;

	private String code;
	//创建记录时间
	@Column(columnDefinition ="datetime COMMENT '创建时间'" )
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getCreateDate() {
		return createDate;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	/**
	 * @return the process
	 */
	public List<ProductProcess> getProcess() {
		return process;
	}
	/**
	 * @param process the process to set
	 */
	public void setProcess(List<ProductProcess> process) {
		this.process = process;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	
}
