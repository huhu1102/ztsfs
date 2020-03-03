package com.zt.service;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.SysUnit;

/**
 * @author wl
 * @date 2019年5月20日 
 */
public interface SysUnitService {

	/**
	 * @param queryName
	 * @param page
	 * @param size
	 * @return
	 */
	ResultPage<SysUnit> findbypages(String queryName, int page, int size)throws BusinessRuntimeException;

	/**
	 * @param unit
	 * @return
	 */
	ResultObject<SysUnit> save(SysUnit unit)throws BusinessRuntimeException;

	/**
	 * @param id
	 * @return
	 */
	ResultObject<SysUnit> delete(long id)throws BusinessRuntimeException;
	
}
