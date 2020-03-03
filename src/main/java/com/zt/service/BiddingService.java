/**
 * 
 */
package com.zt.service;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;

import com.zt.po.Bidding;

/**
 * @author yh
 * @date 2019年5月9日
 */
public interface BiddingService {
	public ResultPage<Bidding> findBidding(String queryName, int page, int size) throws BusinessRuntimeException;
    
    
    public  ResultObject<Bidding> addBidding(Bidding big)throws BusinessRuntimeException;
	/**
	 * @param id
	 * @return
	 */
	public ResultObject<Bidding> deletBidding(long id)throws BusinessRuntimeException;
	
	
}
