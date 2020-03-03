/**
 * 
 */
package com.zt.service;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.RowMaterial;


/**
 * @author yh
 * @date 2019年4月28日
 */
public interface RowMaterialService {
	 ResultPage<RowMaterial> findRowMaterial(String queryName, int page, int size) throws BusinessRuntimeException;
     
     
     ResultObject<RowMaterial> addRowMaterial(RowMaterial acrs)throws BusinessRuntimeException;
 	/**
 	 * @param id
 	 * @return
 	 */
 	ResultObject<RowMaterial> deletRowMaterial(long id)throws BusinessRuntimeException;


	/**
	 * @return
	 */
	ResultObject<RowMaterial> basedata()throws BusinessRuntimeException;


	/**
	 * @param accs
	 * @return
	 */
	ResultObject<RowMaterial> updateRowMaterial(RowMaterial material)throws BusinessRuntimeException;


	ResultObject<RowMaterial> receive(long id, float quantity)throws BusinessRuntimeException;
}
