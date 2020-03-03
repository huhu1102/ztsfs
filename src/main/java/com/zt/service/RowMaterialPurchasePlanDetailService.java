/**  
* 
*/  
 
package com.zt.service;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.po.RowMaterialPurchasePlanDetail;

/**
 * @author whl
 * @date 2019年6月26日 
 */
public interface RowMaterialPurchasePlanDetailService {

	/**
	 * @param materailId
	 * @param buyType
	 * @return
	 */
  public ResultObject<RowMaterialPurchasePlanDetail> getCurrentDetail(Long materailId)throws BusinessRuntimeException;

}
