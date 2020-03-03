/**  
* 
*/  
 
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

import com.zt.dao.PositionDao;
import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.Position;
import com.zt.service.PositionService;

/**
 * @author whl
 * @date 2019年4月15日
 * 职位接口实现 
 */
@Service("postService")
@CacheConfig(cacheNames = "zt_caches_post")
public class PositionServiceImp  implements  PositionService{

	@Autowired
	PositionDao  postDao;
	
	/*
	 * 分页模糊条件查询
	 */
	@Override
	@Cacheable(key="'dep_'+#queryName+'_'+#page+'_'+#size")
	public ResultPage<Position> findSearch(String queryName, int page, int size) throws BusinessRuntimeException {

		ResultPage<Position> ro=new ResultPage<Position>();
		Pageable pageable = PageRequest.of(page-1,size);
		Page<Position> pages = postDao.findSearch(queryName,pageable);
		
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
	 * 部门信息的修改
	 */
	@Override
	@CacheEvict(allEntries = true)
	public ResultObject<Position> savePosition(Position position) throws BusinessRuntimeException {
		ResultObject<Position> ro=new ResultObject<>();
		if(Long.valueOf(position.getId())==null) {
			position.setCreateDate(new Date());
		}
		position.setEnabled(true);
		Position pos= postDao.saveAndFlush(position);
		 if(pos!=null) {
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
	 * 部门信息的删除
	 */
	@Override
	@CacheEvict(allEntries = true)
	public ResultObject<Position> deletDep(long id) throws BusinessRuntimeException {
		
		ResultObject<Position> ro=new ResultObject<>(); 
		Position pos=postDao.findById(id);
		 if (pos!=null) {
			pos.setEnabled(false);
			postDao.saveAndFlush(pos);
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
