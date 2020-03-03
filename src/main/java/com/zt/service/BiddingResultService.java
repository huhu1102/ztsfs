/**
 * 
 */
package com.zt.service;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;

import com.zt.po.BiddingResult;

/**
 * @author yh
 * @date 2019年5月9日
 */
public interface BiddingResultService {
public ResultPage<BiddingResult> findBiddingOutCome(String completeStatus, int page, int size) throws BusinessRuntimeException;
    
    
    public  ResultObject<BiddingResult> addBiddingOutCome(BiddingResult bdgcm)throws BusinessRuntimeException;
	/**
	 * @param id
	 * @return
	 */
	public ResultObject<BiddingResult> deletBiddingOutCome(long id)throws BusinessRuntimeException;
}
