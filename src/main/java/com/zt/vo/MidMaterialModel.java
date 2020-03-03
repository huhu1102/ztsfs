package com.zt.vo;


import com.zt.po.BasePo;
import com.zt.po.MidMaterial;

import java.util.Date;

/**
 * @author wl
 * @date 2019年6月25日 
 */
public class MidMaterialModel extends BasePo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long id;
	//关联的半成品 名称
	private String name;
	//关联的半成品ID
	private Long midProductId;
	//关联的半成品数量 正常生产一件成品所需要的材料数量
	private Integer quantity;
	//数据类型  delete  new  update
	private String mtype;
	//创建时间
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
	public Long getMidProductId() {
		return midProductId;
	}
	public void setMidProductId(Long midProductId) {
		this.midProductId = midProductId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public String getMtype() {
		return mtype;
	}
	public void setMtype(String mtype) {
		this.mtype = mtype;
	}
	public MidMaterial v2p(MidMaterialModel mo) {
		MidMaterial m = new MidMaterial();
		if(mo.getId()!=0) {
			m.setId(mo.getId());
		}
		if (mo.getCreateDate()!=null&&!mo.getCreateDate().equals("")) {
			
			 m.setCreateDate(mo.getCreateDate());
		}else {
			m.setCreateDate(new Date());
		}
		
		m.setMidProductId(mo.getMidProductId());
		m.setName(mo.getName());
		m.setQuantity(mo.getQuantity());
		
		return m;
	}
	
}
