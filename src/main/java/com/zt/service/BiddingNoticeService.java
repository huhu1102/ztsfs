/**
 * 
 */
package com.zt.service;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.BiddingNotice;

/**
 * @author yh
 * @date 2019年5月10日
 */
public interface BiddingNoticeService {
public ResultPage<BiddingNotice> findTenderNotice(String tdInstitution, int page, int size) throws BusinessRuntimeException;
    
    
    public  ResultObject<BiddingNotice> addTenderNotice(BiddingNotice big)throws BusinessRuntimeException;
	/**
	 * @param id
	 * @return
	 */
	public ResultObject<BiddingNotice> deletTenderNotice(long id)throws BusinessRuntimeException;
}
