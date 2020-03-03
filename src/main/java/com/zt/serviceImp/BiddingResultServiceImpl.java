/**
 * 
 */
package com.zt.serviceImp;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.zt.common.PageUtil;

import com.zt.dao.BiddingResultDao;
import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;

import com.zt.po.BiddingResult;
import com.zt.service.BiddingResultService;

/**
 * @author yh
 * @date 2019年5月9日
 */
@Service("biddingResultService")
@CacheConfig(cacheNames = "zt_caches_biddingOutCome")
public class BiddingResultServiceImpl implements BiddingResultService {
	@Autowired
	private BiddingResultDao bdcd;
	
	@Cacheable(key="'bidgtc_'+#Status")	
	@Override
	public ResultPage<BiddingResult> findBiddingOutCome(String completeStatus, int page, int size)
			throws BusinessRuntimeException {
		ResultPage<BiddingResult> ro=new ResultPage<>();
		Pageable pageable = PageRequest.of(page-1,size);
//		Page<VehicleInfo> pages =vid.findSearch(queryName, pageable);
		List<BiddingResult> pagelist=bdcd.findAllByPage(completeStatus, (page-1)*size, size);
		int count=bdcd.countAllData(completeStatus);
		 if (pagelist!=null) {
//	        	int totals=(int) pages.getTotalElements();
	        	 ro.setData(pagelist);
	    	     ro.setTotal(count);
	    	     ro.setTotalPages(PageUtil.getTotalPages(count, size));
	    	     ro.setSuccess(true);
			} else {
				ro.setSuccess(false);
				throw  new BusinessRuntimeException(ResultCode.UNKNOWN_ERROR);
			}
		
		return ro;
	}

	@Override
	@CacheEvict(allEntries=true)
	public ResultObject<BiddingResult> addBiddingOutCome(BiddingResult bdgcm) throws BusinessRuntimeException {
		ResultObject<BiddingResult> ro=new ResultObject<>();
		if(Long.valueOf(bdgcm.getId())==null||bdgcm.getId()==0) {
			bdgcm.setCreateDate(new Date());
			
		}				
		bdgcm.setEnabled(true);;
		BiddingResult ars=bdcd.saveAndFlush(bdgcm);
		 if(ars!=null) {
			   ro.setSuccess(true);
				ro.setMsg("操作成功");;
		   }else {
			   ro.setSuccess(false);
			   ro.setMsg("操作失败");
			throw   new BusinessRuntimeException(ResultCode.OPER_FAILED);
		   }
		
		return ro;
	}

	@Override
	@CacheEvict(allEntries=true)
	public ResultObject<BiddingResult> deletBiddingOutCome(long id) throws BusinessRuntimeException {
		ResultObject<BiddingResult> ro=new ResultObject<>(); 
		BiddingResult acris=bdcd.findById(id);
		 if (acris!=null) {
			 acris.setEnabled(false);
			 bdcd.saveAndFlush(acris);
			ro.setSuccess(true);
			ro.setMsg("操作成功");
		}else {
			ro.setSuccess(false);
			ro.setMsg("操作失败");
		throw	new BusinessRuntimeException(ResultCode.OPER_FAILED);
		}  
		return ro;
	}
	
}
