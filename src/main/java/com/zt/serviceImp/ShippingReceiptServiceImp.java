package com.zt.serviceImp;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.zt.dao.ShippingReceiptDao;
import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.ShippingReceipt;
import com.zt.service.ShippingReceiptService;

/**
 * @author wl
 * @date 2019年4月28日 
 */
@Service("deliveryreceiptService")
@CacheConfig(cacheNames = "zt_caches_deliveryreceipt")
public class ShippingReceiptServiceImp implements ShippingReceiptService{
	
	@Autowired
	ShippingReceiptDao drecDao;
	/*
	 * 分页模糊条件查询
	 */
	@Override
	@Cacheable(key="'dre_'+#queryName+'_'+#page+'_'+#size")
	public ResultPage<ShippingReceipt> findSearch(String queryName, int page, int size)
			throws BusinessRuntimeException {
		ResultPage<ShippingReceipt> ro=new ResultPage<ShippingReceipt>();
		Pageable pageable = PageRequest.of(page-1,size);
		Page<ShippingReceipt> pages = drecDao.findSearch(queryName,pageable);
		
		 if (pages!=null) {
	        	int totals=(int) pages.getTotalElements();
	        	 ro.setData(pages.getContent());
	    	     ro.setTotal(totals);
	    	     ro.setTotalPages(pages.getTotalPages());
	    	     ro.setSuccess(true);
			} else {
				ro.setSuccess(false);
				throw new BusinessRuntimeException(ResultCode.UNKNOWN_ERROR);
			}
		return ro;
	}
	/*
	 * 信息的修改
	 */
	@Override
	@CacheEvict(allEntries = true)
	public ResultObject<ShippingReceipt> savePosition(ShippingReceipt shippingReceipt) throws BusinessRuntimeException {
		ResultObject<ShippingReceipt> ro=new ResultObject<>();
		if(Long.valueOf(shippingReceipt.getId())==null) {
			shippingReceipt.setCreateDate(new Date());
		}
		shippingReceipt.setEnabled(true);
		ShippingReceipt dere= drecDao.saveAndFlush(shippingReceipt);
		 if(dere!=null) {
			   ro.setSuccess(true);
			   ro.setMsg("操作成功");
		   }else {
			   ro.setSuccess(false);
			   ro.setMsg("操作失败");
			   throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
		   }
		
		return ro;
	}
	/*
	 * 信息的删除
	 */
	@Override
	@CacheEvict(allEntries = true)
	public ResultObject<ShippingReceipt> deletDel(long id) throws BusinessRuntimeException {
		ResultObject<ShippingReceipt> ro=new ResultObject<>(); 
		ShippingReceipt pos=drecDao.findById(id);
		 if (pos!=null) {
			pos.setEnabled(false);
			drecDao.saveAndFlush(pos);
			ro.setSuccess(true);
			ro.setMsg("操作成功");
		}else {
			ro.setSuccess(false);
			ro.setMsg("操作失败");
			throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
		}  
		return ro;
	}

}
