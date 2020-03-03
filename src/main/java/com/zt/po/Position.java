/**  
* 
*/  
 
package com.zt.po;

import javax.persistence.*;
import java.util.Date;

/**
 * @author whl
 * @date 2019年4月16日
 *  职位实体类 
 */
@Entity
@Table(name="zt_position")
@org.hibernate.annotations.Table(appliesTo = "zt_position",comment="职位实体类")
public class Position  extends BasePo {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	 /**
	  * 职位名称
	  */
	@Column(columnDefinition ="varchar(255)  COMMENT'职位名称'" )
    private String name;
    /**
     * 职位描述
     */
	@Column(columnDefinition ="varchar(255) COMMENT '职位描述'" )
    private String specification;
	//职位编号
	@Column(columnDefinition ="varchar(255) COMMENT '职位描述'" )
    private int posNumber;
	//是否可用
	@Column(columnDefinition ="TINYINT(1)  COMMENT '是否可用'" )
	 private boolean enabled;
    /**
     * 是否有子集
     */
//	@Column(columnDefinition ="TINYINT(1)  COMMENT '是否有子菜单'" )
//    private boolean hasChildren;
    /**
	 * 创建时间
	 */
	@Column(columnDefinition ="date  COMMENT '创建时间'" )
	@Temporal(TemporalType.TIMESTAMP)
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
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the specification
	 */
	public String getSpecification() {
		return specification;
	}
	/**
	 * @param specification the specification to set
	 */
	public void setSpecification(String specification) {
		this.specification = specification;
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
	public int getPosNumber() {
		return posNumber;
	}
	public void setPosNumber(int posNumber) {
		this.posNumber = posNumber;
	}
	
	
	
	
	
    
}
