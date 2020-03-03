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
import com.zt.dao.StorageDao;
import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.Bidding;
import com.zt.po.Storage;
import com.zt.service.StorageService;

/**
 * @author yh
 * @date 2019年5月10日
 */
@Service("storageService")
@CacheConfig(cacheNames = "zt_caches_storage")
public class StorageServiceImp implements StorageService {
	@Autowired
	private StorageDao bido;
	@Override
	@Cacheable(key="'storage_'+#Status")	
	public ResultPage<Storage> findStorage(String designation, int page, int size) throws BusinessRuntimeException {
		ResultPage<Storage> ro=new ResultPage<Storage>();
		Pageable pageable = PageRequest.of(page-1,size);
//		Page<VehicleInfo> pages =vid.findSearch(queryName, pageable);
		List<Storage> pagelist=bido.findAllByPage(designation, (page-1)*size, size);
		int count=bido.countAllData(designation);
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
	public ResultObject<Storage> addStorage(Storage big) throws BusinessRuntimeException {
		ResultObject<Storage> ro=new ResultObject<>();
		if(Long.valueOf(big.getId())==null||big.getId()==0) {
			big.setCreateDate(new Date());
			
		}				
		big.setEnabled(true);;
		Storage ars=bido.saveAndFlush(big);
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
	public ResultObject<Storage> deletStorage(long id) throws BusinessRuntimeException {
		ResultObject<Storage> ro=new ResultObject<>(); 
		Storage acris=bido.findById(id);
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
