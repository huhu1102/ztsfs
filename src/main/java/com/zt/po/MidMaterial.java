/**  
* 
*/  
 
package com.zt.po;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

/**
 * @author whl
 * @date 2019年6月22日 
   *   一个产品 所需要的半成品/
 */
@Entity
@Table(name="zt_midmateria")//
@org.hibernate.annotations.Table(appliesTo = "zt_midmateria",comment="产品所需原料")
public class MidMaterial extends BasePo{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	//关联的半成品 名称
	@Column(columnDefinition ="varchar(255)  COMMENT '材料名称.'" )
	private String name;

	//关联的半成品ID
	private Long midProductId;
	//关联的半成品数量 正常生产一件成品所需要的材料数量
	private Integer quantity;
	
	//创建记录时间
	@JsonIgnore
	@Column(columnDefinition ="datetime COMMENT '创建时间'" )
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
	 * @return the midProduct
	 */
//	public MiddleProduct getMidProduct() {
//		return midProduct;
//	}
//	/**
//	 * @param midProduct the midProduct to set
//	 */
//	public void setMidProduct(MiddleProduct midProduct) {
//		this.midProduct = midProduct;
//	}
	/**
	 * @return the midProductId
	 */
	public Long getMidProductId() {
		return midProductId;
	}
	/**
	 * @param midProductId the midProductId to set
	 */
	public void setMidProductId(Long midProductId) {
		this.midProductId = midProductId;
	}
	/**
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@Override
	public String toString() {
		return "MidMaterial [id=" + id + ", name=" + name  + ", midProductId="
				+ midProductId + ", quantity=" + quantity + ", createDate=" + createDate + "]";
	}

	
	
	
}
