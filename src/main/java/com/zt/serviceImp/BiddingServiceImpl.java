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
import com.zt.dao.BiddingDao;
import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.Bidding;
import com.zt.service.BiddingService;

/**
 * @author yh
 * @date 2019年5月9日
 */
@Service("biddingService")
@CacheConfig(cacheNames = "zt_caches_bidding")
public class BiddingServiceImpl implements BiddingService{
	@Autowired
	private BiddingDao bido;
	
	@Cacheable(key="'bidding_'+#Status")	
	@Override
	public ResultPage<Bidding> findBidding(String queryName, int page, int size) throws BusinessRuntimeException {
		ResultPage<Bidding> ro=new ResultPage<Bidding>();
		Pageable pageable = PageRequest.of(page-1,size);
//		Page<VehicleInfo> pages =vid.findSearch(queryName, pageable);
		List<Bidding> pagelist=bido.findAllByPage(queryName, (page-1)*size, size);
		int count=bido.countAllData(queryName);
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
	@CacheEvict(allEntries=true)//清除缓存
	public ResultObject<Bidding> addBidding(Bidding acrs) throws BusinessRuntimeException {
		ResultObject<Bidding> ro=new ResultObject<>();
		if(Long.valueOf(acrs.getId())==null||acrs.getId()==0) {
			acrs.setCreateDate(new Date());
			
		}				
		acrs.setEnabled(true);;
		Bidding ars=bido.saveAndFlush(acrs);
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
	@CacheEvict(allEntries=true)//清除缓存
	public ResultObject<Bidding> deletBidding(long id) throws BusinessRuntimeException {
		ResultObject<Bidding> ro=new ResultObject<>(); 
		Bidding acris=bido.findById(id);
		 if (acris!=null) {
			 acris.setEnabled(false);
			 bido.saveAndFlush(acris);
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
