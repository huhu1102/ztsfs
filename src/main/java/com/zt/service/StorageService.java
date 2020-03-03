/**
 * 
 */
package com.zt.service;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;

import com.zt.po.Storage;

/**
 * @author yh
 * @date 2019年5月10日
 */
public interface StorageService {
public ResultPage<Storage> findStorage(String designation, int page, int size) throws BusinessRuntimeException;
    
    
    public  ResultObject<Storage> addStorage(Storage big)throws BusinessRuntimeException;
	/**
	 * @param id
	 * @return
	 */
	public ResultObject<Storage> deletStorage(long id)throws BusinessRuntimeException;
}
