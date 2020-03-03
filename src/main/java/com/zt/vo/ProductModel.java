/**  
* 
*/  
 
package com.zt.vo;

import com.zt.po.BasePo;
import com.zt.po.Product;

import java.util.Date;


/**
 * @author whl
 * @date 2019年6月25日 
 * 产品MODEL
 */

public class ProductModel extends BasePo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	//产品名称
	private String producteName;
	//产品编号
	private String serialNumber;
	//产品规格
	private String specification;
	//产品型号
	private String proType;
	//产品标记
	private String mark;
	//附件名字
	private String uploadName;
	//附件url
	private String imageUrl;
	//颜色
	private String color;
	//是否可用
	private Long clientId;
	//原材料 json字符串
	private String midMaterials;
	//创建记录时间
	private Date createDate;
//	//工序
//	private String processModel;
	//工序步骤Id
//	private Long  preprosessId;
	//工序步骤Id
	private Long  sysUnitId;
	private String note;
	//工序名
//	private String prosessName;
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
	 * @return the producteName
	 */
	public String getProducteName() {
		return producteName;
	}
	/**
	 * @param producteName the producteName to set
	 */
	public void setProducteName(String producteName) {
		this.producteName = producteName;
	}
	/**
	 * @return the serialNumber
	 */
	public String getSerialNumber() {
		return serialNumber;
	}
	/**
	 * @param serialNumber the serialNumber to set
	 */
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
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
	/**
	 * @return the proType
	 */
	public String getProType() {
		return proType;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * @param proType the proType to set
	 */
	public void setProType(String proType) {
		this.proType = proType;
	}
	/**
	 * @return the mark
	 */
	public String getMark() {
		return mark;
	}
	/**
	 * @param mark the mark to set
	 */
	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getUploadName() {
		return uploadName;
	}

	public void setUploadName(String uploadName) {
		this.uploadName = uploadName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}
	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}
	/**
	 * @return the clientId
	 */
	public Long getClientId() {
		return clientId;
	}
	/**
	 * @param clientId the clientId to set
	 */
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	/**
	 * @return the midMaterials
	 */
	public String getMidMaterials() {
		return midMaterials;
	}
	/**
	 * @param midMaterials the midMaterials to set
	 */
	public void setMidMaterials(String midMaterials) {
		this.midMaterials = midMaterials;
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
	 public Product v2p(ProductModel mo) {
		 Product p=new Product();
		 if (mo.getId()!=0) {
			p.setId(mo.getId());
		}
		 p.setColor(mo.getColor());
		 if (mo.getCreateDate()==null||mo.getCreateDate().equals("")) {
			 p.setCreateDate(new Date());
		}else {
			p.setCreateDate(mo.getCreateDate());
		}
		  p.setSysUnitId(mo.getSysUnitId());
		  p.setEnabled(true);
		  p.setUploadName(mo.getUploadName());
		  p.setImageUrl(mo.getImageUrl());
		  p.setColor(mo.getColor());
		  p.setSpecification(mo.getSpecification());
		  p.setProducteName(mo.getProducteName());
		  p.setProType(mo.getProType());
		  p.setSerialNumber(mo.getSerialNumber());
		  p.setNote(mo.getNote());
		 return p;
	 }
	public Long getSysUnitId() {
		return sysUnitId;
	}
	public void setSysUnitId(Long sysUnitId) {
		this.sysUnitId = sysUnitId;
	}
}
