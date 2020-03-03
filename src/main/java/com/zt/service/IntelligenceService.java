/**
 * 
 */
package com.zt.service;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;

import com.zt.po.Intelligence;

/**
 * @author yh
 * @date 2019年5月9日
 */
public interface IntelligenceService {
	//新增的方法
	   public ResultPage<Intelligence> findIntelligence(String type, int page, int size) throws BusinessRuntimeException;
	   
	   
	   public  ResultObject<Intelligence> addIntelligence(Intelligence itlgc)throws BusinessRuntimeException;
		/**
		 * @param id
		 * @return
		 */
		public ResultObject<Intelligence> deletIntelligence(long id)throws BusinessRuntimeException;
}
