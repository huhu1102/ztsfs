/**
 * 
 */
package com.zt.po;

import javax.persistence.*;
import java.util.Date;

/**
 * @author yh
 * @date 2019年5月7日
 */
//认证资料
@Entity
@Table(name="zt_means")//
@org.hibernate.annotations.Table(appliesTo = "zt_means",comment="认证资料")
public class Means extends BasePo{
	private static final long serialVersionUID = 1L;
	//资料id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	//资料名称
	@Column(columnDefinition ="varchar(255)  COMMENT '资料名称.'" )
	 private String meansName;
	//资料描述
	@Column(columnDefinition ="varchar(255)  COMMENT '资料描述.'" )
	private  String  represent;
	//资料保存路径
	@Column(columnDefinition ="varchar(255)  COMMENT '资料保存路径.'" )
	private  String filePath;
	
	@Column(columnDefinition ="TINYINT(1)  COMMENT '是否可见'" )
	private boolean enabled;
	//创建时间
	@Column(columnDefinition ="datetime  COMMENT '创建时间'" )
	@Temporal(TemporalType.DATE)
	private Date createDate;
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return meansName;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.meansName = name;
	}
	/**
	 * @return the represent
	 */
	public String getRepresent() {
		return represent;
	}
	/**
	 * @param represent the represent to set
	 */
	public void setRepresent(String represent) {
		this.represent = represent;
	}
	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}
	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}
	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
