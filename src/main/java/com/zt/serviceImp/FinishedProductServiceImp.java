package com.zt.serviceImp;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.zt.dao.ClientDao;
import com.zt.dao.FinishedProductDao;
import com.zt.dao.ProductDao;
import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.Client;
import com.zt.po.FinishedProduct;
import com.zt.po.Product;
import com.zt.po.Workstep;
import com.zt.service.FinishedProductService;

/**
 * @author wl
 * @date 2019年5月13日 
 */
@Service("finishedProductService")
@CacheConfig(cacheNames = "zt_caches_finishedproduct")
public class FinishedProductServiceImp implements FinishedProductService{
	
	@Autowired
	FinishedProductDao fpDao;
	@Autowired
	ProductDao productDao;
	@Autowired
	ClientDao clientDao;
	/*
	 * 分页查询
	 */
	@Override
	//@Cacheable(key="'fp_'+#queryName+'_'+#page+'_'+#size")
	public ResultPage<FinishedProduct> findSearch(String queryName, int page, int size)
			throws BusinessRuntimeException {
		ResultPage<FinishedProduct> ro=new ResultPage<FinishedProduct>();
		Pageable pageable = PageRequest.of(page-1,size);
		Page<FinishedProduct> pages = fpDao.findSearch(queryName,pageable);
		
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
	//@CacheEvict(allEntries = true)
	public ResultObject<FinishedProduct> deletDel(long id) throws BusinessRuntimeException {
		ResultObject<FinishedProduct> ro=new ResultObject<>(); 
		FinishedProduct pos=fpDao.findById(id);
		 if (pos!=null) {
			pos.setEnabled(false);
			fpDao.saveAndFlush(pos);
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
	 * 更新修改
	 */
	@Override
	public ResultObject<FinishedProduct> saveClient(FinishedProduct finishedProduct) throws BusinessRuntimeException {
		ResultObject<FinishedProduct> ro=new ResultObject<>();
		if(Long.valueOf(finishedProduct.getId())==null||finishedProduct.getId()==0) {
			finishedProduct.setCreateDate(new Date());
		}	
		finishedProduct.setEnabled(true);
		FinishedProduct pro = fpDao.saveAndFlush(finishedProduct);
		if (pro!=null) {
			ro.setSuccess(true);
			ro.setMsg("操作成功");
		}else {
			ro.setSuccess(false);
			ro.setMsg("操作失败");
		}
		return ro;
	}
	@Override
	public ResultObject<FinishedProduct> basedata() throws BusinessRuntimeException {
		ResultObject<FinishedProduct> ro=new ResultObject<>();
		Map<String, Object> map=new HashMap<String, Object>();
//		List<Department> departmentlist=  departDao.findAllDep();
//		List<Position> postlist = postDao.findallpost();
		 Set<Client> clients= clientDao.findClient();
		     Set<Product> productList=  productDao.findAlldatas();
		if (productList!=null) {
			map.put("clients", clients);
			map.put("pruducts", productList);
			ro.setRoot(map);
			ro.setSuccess(true);
		
		}else {
			ro.setSuccess(false);
			throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
		}
		
		return ro;
	}
   
}
