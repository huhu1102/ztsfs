package com.zt.serviceImp;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.zt.dao.ProductProcessDao;
import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.Position;
import com.zt.po.Product;
import com.zt.po.ProductProcess;
import com.zt.service.ProductProcessService;

/**
 * @author wl
 * @date 2019年4月17日 
 */
@Service("productProcessService")
@CacheConfig(cacheNames = "zt_caches_productprocess")
public class ProductProcessServiceImp implements ProductProcessService {
	@Autowired
	ProductProcessDao productProcessDao;
	/*
	 * 根据产品查询工序
	 */
	@Override
	@Cacheable(key="'ppro_'+#productName")
	public ResultObject<ProductProcess> findProductProcess(String productName) {
		ResultObject<ProductProcess> ropp = new ResultObject<>();
//		List<ProductProcess> lpp = productProcessDao.findByProduct(productName);
//		ropp.setData(lpp);
		return ropp;
	}
	/*
	 * 修改工序
	 */
	@Override
	@CacheEvict(allEntries = true)
	public ResultObject<ProductProcess> saveProductProcess(ProductProcess productProcess) {
		ResultObject<ProductProcess> ro=new ResultObject<>();
		if(Long.valueOf(productProcess.getId())==null||productProcess.getId()==0) {
			productProcess.setCreateDate(new Date());
		}	
		productProcess.setEnabled(true);
		ProductProcess pro = productProcessDao.saveAndFlush(productProcess);
		if (pro!=null) {
			ro.setSuccess(true);
			ro.setMsg("操作成功");
		}else {
			ro.setSuccess(false);
			ro.setMsg("操作失败");
		}
		return ro;
	}
	/*
	 * 分页模糊查询
	 */
	@Override
	@Cacheable(key="'ppro_'+#productName")
	public ResultPage<ProductProcess> findSearch(String queryName, int page, int size) throws BusinessRuntimeException {
		ResultPage<ProductProcess> ro=new ResultPage<ProductProcess>();
		Pageable pageable = PageRequest.of(page-1,size);
		Page<ProductProcess> pages = productProcessDao.findSearch(queryName, pageable);
		
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
	 * 删除
	 */
	@Override
	@CacheEvict(allEntries = true)
	public ResultObject<ProductProcess> deletDep(long id) throws BusinessRuntimeException {
		ResultObject<ProductProcess> ro=new ResultObject<>(); 
		ProductProcess pro=productProcessDao.findById(id);
		 if (pro!=null) {
			pro.setEnabled(false);
			productProcessDao.saveAndFlush(pro);
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
