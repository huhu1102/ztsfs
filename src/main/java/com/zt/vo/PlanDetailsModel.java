/**  
* 
*/  
 
package com.zt.vo;

import com.zt.po.BasePo;
import com.zt.po.MidProductPurchasePlanDetail;
import com.zt.po.RowMaterialPurchasePlanDetail;

import java.util.Date;

/**
 * @author whl
 * @date 2019年6月18日 
 */
public class PlanDetailsModel extends  BasePo  {
	
	private static final long serialVersionUID = 1L;
	//采购序列号
	private Long id;
	private Integer    sid;
    private String  name;
    private float   quantity;
    private long   unitId;
    private String  notes;
    private String  unitName;
    private String specifications;
    private Long  materialId;
    /**
     * delete
     * update
     * new
     */
    private String  type;
	/**
	 * @return the id
	 */

	/**
	 * @return the sid
	 */
	public Integer getSid() {
		return sid;
	}
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @param sid the sid to set
	 */
	public void setSid(Integer sid) {
		this.sid = sid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getQuantity() {
		return quantity;
	}
	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}
	/**
	 * @return the unitId
	 */
	public long getUnitId() {
		return unitId;
	}
	/**
	 * @param unitId the unitId to set
	 */
	public void setUnitId(long unitId) {
		this.unitId = unitId;
	}
	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}
	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}
	/**
	 * @return the unitName
	 */
	public String getUnitName() {
		return unitName;
	}
	/**
	 * @param unitName the unitName to set
	 */
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}
	
	public String getSpecifications() {
		return specifications;
	}
	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}
	public Long getMaterialId() {
		return materialId;
	}
	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}
	public RowMaterialPurchasePlanDetail   v1p(PlanDetailsModel m) {
		RowMaterialPurchasePlanDetail detail =new RowMaterialPurchasePlanDetail();
		if (m.getId()!=null&&m.getId()>0) {
			detail.setId(m.getId());
		}
		detail.setUnitId(m.getUnitId());
		detail.setName(m.getName());
		detail.setQuantity(m.getQuantity());
		detail.setNotes(m.getNotes());
		detail.setCreateDate(new Date());
		detail.setUnitName(m.getUnitName());
		detail.setEnabled(true);
		detail.setMaterialId(m.getMaterialId());
		detail.setSpecifications(m.getSpecifications());
	  return detail;
	}
	public MidProductPurchasePlanDetail   v2p(PlanDetailsModel m) {
		MidProductPurchasePlanDetail detail =new MidProductPurchasePlanDetail();
		if (m.getId()!=null&&m.getId()>0) {
			detail.setId(m.getId());
		}
		detail.setUnitId(m.getUnitId());
		detail.setName(m.getName());
		detail.setQuantity(m.getQuantity());
		detail.setNotes(m.getNotes());
		detail.setCreateDate(new Date());
		detail.setUnitName(m.getUnitName());
		detail.setEnabled(true);
		detail.setMaterialId(m.getMaterialId());
		detail.setSpecifications(m.getSpecifications());
		return detail;
	}
	
}
