/**  
* 
*/  
 
package com.zt.service;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.po.MidProductPurchasePlanDetail;

/**
 * @author whl
 * @date 2019年6月26日 
 */
public interface MidProductPurchasePlanDetailService {

	/**
	 * @param materailId
	 * @param buyType
	 * @return
	 */
  public 	ResultObject<MidProductPurchasePlanDetail> getCurrentDetail(Long materailId)throws BusinessRuntimeException;

}
