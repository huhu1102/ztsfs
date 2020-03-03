package com.zt.serviceImp;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.zt.dao.InformDao;
import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.Client;
import com.zt.po.Inform;
import com.zt.po.Position;
import com.zt.po.Product;
import com.zt.service.InformService;

/**
 * @author wl
 * @date 2019年4月16日
 */
@Service("informService")
@CacheConfig(cacheNames = "zt_caches_inform")
public class InformServiceImp implements InformService {

	@Autowired
	private InformDao informDao;

	/*
	 * 根据主题查询通知记录
	 * 
	 * @Override public ResultPage<Inform> findBySubject(String subject) {
	 * ResultPage<Inform> mtrg = new ResultPage<>(); Page<Inform> pm =
	 * informDao.findBySubject(subject); mtrg.setData(pm.getContent()); return mtrg;
	 * }
	 */
	/*
	 * 分页模糊条件查询
	 */
	@Override
	@Cacheable(key="'inf_'+#name+'_'+#pageable")
	public ResultPage<Inform> findSearch(String queryName, int page, int size)
			throws BusinessRuntimeException {
		ResultPage<Inform> ro=new ResultPage<Inform>();
		Pageable pageable = PageRequest.of(page-1,size);
		Page<Inform> pages = informDao.findSearch(queryName,pageable);
		
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
	 *修改通告
	 */
	@Override
	@CacheEvict(allEntries = true)
	public ResultObject<Inform> saveInform(Inform inform) {
		ResultObject<Inform> ro=new ResultObject<>();
		if(Long.valueOf(inform.getId())==null||inform.getId()==0) {
			inform.setCreateDate(new Date());
		}	
		inform.setEnabled(true);
		Inform pro = informDao.saveAndFlush(inform);
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
	 * 删除
	 */
	@Override
	public ResultObject<Inform> deletIn(long id) throws BusinessRuntimeException {
		ResultObject<Inform> ro=new ResultObject<>(); 
		Inform pos=informDao.findById(id);
		 if (pos!=null) {
			pos.setEnabled(false);
			informDao.saveAndFlush(pos);
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
