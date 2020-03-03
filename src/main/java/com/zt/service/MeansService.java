/**
 * 
 */
package com.zt.service;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;

import com.zt.po.Means;

/**
 * @author yh
 * @date 2019年5月9日
 */
public interface MeansService {
	//新增的方法
	   public ResultPage<Means> findMeans(String meansName, int page, int size) throws BusinessRuntimeException;
	   
	   
	   public  ResultObject<Means> addMeans(Means mans)throws BusinessRuntimeException;
		/**
		 * @param id
		 * @return
		 */
		public ResultObject<Means> deletMeans(long id)throws BusinessRuntimeException;
}
