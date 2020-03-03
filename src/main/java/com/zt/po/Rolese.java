package com.zt.po;

import javax.persistence.*;
import java.util.Date;

/**
 * @author whl
 * 2019年4月12日 
   *    岗位 ： 主要用于厂部车间 工人职能管理，如检测工
 */
@Entity
@Table(name="zt_rolese")
@org.hibernate.annotations.Table(appliesTo = "zt_rolese",comment="岗位")
public class Rolese extends BasePo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	/**
	 * 岗位名称
	 */
	@Column(columnDefinition ="varchar(255) COMMENT'岗位名称'" )
	private String name;
	/**
	 * 岗位描述：主要职责
	 */
	@Column(columnDefinition ="varchar(255) COMMENT'主要职责'" )
	private String descripte;
	/**
	 * 创建时间
	 */
	@Column(columnDefinition ="datetime COMMENT'创建时间'" )
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	/**
	 * 所属公司Id
	 */
	@Column(columnDefinition ="BIGINT(20) COMMENT'所属公司id'" )
	private long compayId;
	/**
	 *  是否可用  暂时搁置
	 */
	@Column(columnDefinition ="TINYINT(1)  COMMENT '是否可用'" )
	private Boolean validite;
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
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the descripte
	 */
	public String getDescripte() {
		return descripte;
	}
	/**
	 * @param descripte the descripte to set
	 */
	public void setDescripte(String descripte) {
		this.descripte = descripte;
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
	/**
	 * @return the compayId
	 */
	public long getCompayId() {
		return compayId;
	}
	/**
	 * @param compayId the compayId to set
	 */
	public void setCompayId(long compayId) {
		this.compayId = compayId;
	}
	/**
	 * @return the validite
	 */
	public Boolean getValidite() {
		return validite;
	}
	/**
	 * @param validite the validite to set
	 */
	public void setValidite(Boolean validite) {
		this.validite = validite;
	}
	
	
	
}
