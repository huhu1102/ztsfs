/**  
* 
*/  
 
package com.zt.serviceImp;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.zt.dao.DepartmentDao;
import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.Department;
import com.zt.po.Employee;
import com.zt.service.DepartmentService;

/**
 * @author whl
 * @date 2019年4月15日 
 */
@Service("departmentService")
@CacheConfig(cacheNames = "zt_caches_department")
public class DepartmentServiceImp  implements  DepartmentService{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	DepartmentDao  departmentDao;
	/*
	 * 分页模糊条件查询
	 */
	@Override
	@Cacheable(key="'dep_'+#page+'_'+#size+'_'+#queryName")
	public ResultPage<Department> findSearch(String queryName, int page, int size)  {

		ResultPage<Department> ro=new ResultPage<Department>();
		Pageable pageable = PageRequest.of(page-1,size);
		Page<Department> pages = departmentDao.findSearch(queryName,pageable);
		
		 if (pages!=null&&pages.getContent()!=null) {
	        	int totals=(int) pages.getTotalElements();
	        	 ro.setData(pages.getContent());
	    	     ro.setTotal(totals);
	    	     ro.setTotalPages(pages.getTotalPages());
	    	     ro.setSuccess(true);
			} else {
				ro.setSuccess(false);
				throw  new BusinessRuntimeException(ResultCode.UNKNOWN_ERROR);
			}
		return ro;
	}
	/*
	 * 部门信息的修改
	 */
	@Override
	@CacheEvict(allEntries = true)
	public ResultObject<Department> saveDepartment(Department department)  {
		ResultObject<Department> ro=new ResultObject<>();
		if(Long.valueOf(department.getId())==null) {
			department.setCreateDate(new Date());
		}
		department.setEnable(true);

		Department dep= departmentDao.saveAndFlush(department);
		 if(dep!=null&&dep.getId()>0) {
			   ro.setSuccess(true);
			   logger.info(" 保存部门成功");
		   }else {
			   ro.setSuccess(false);
			   ro.setMsg("操作失败");
			throw   new BusinessRuntimeException(ResultCode.OPER_FAILED);
		   }
		
		return ro;
	}
	/*
	 * 部门信息的删除
	 */
	@Override
	@CacheEvict(allEntries = true)
	public ResultObject<Department> deletDep(long id)  {
		
		ResultObject<Department> ro=new ResultObject<>(); 
		Department dep=departmentDao.findById(id);
		 if (dep!=null&&dep.getId()>0) {
			dep.setEnable(false);
			departmentDao.saveAndFlush(dep);
			ro.setSuccess(true);
			logger.info("删除部门成功");
		}else {
			ro.setSuccess(false);
			ro.setMsg("操作失败");
		throw	new BusinessRuntimeException(ResultCode.OPER_FAILED);
		}  
		return ro;
	}
	
}
