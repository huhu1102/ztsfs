/**  
* 
*/  
 
package com.zt.vo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author whl
 * @date 2019年6月20日 
 */
@Entity
@Table(name="zt_vrowmaterial")
public class RowMaterialViewModel {
	@Id
  private	long  id;
  private String materialName;
  private Long unitId;
  
public String getMaterialName() {
	return materialName;
}
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
 * @param materialName the materialName to set
 */
public void setMaterialName(String materialName) {
	this.materialName = materialName;
}
/**
 * @return the unitId
 */
public Long getUnitId() {
	return unitId;
}
/**
 * @param unitId the unitId to set
 */
public void setUnitId(Long unitId) {
	this.unitId = unitId;
}
  
}
