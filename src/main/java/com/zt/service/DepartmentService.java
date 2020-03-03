/**  
* 
*/  
 
package com.zt.service;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.Department;

/**
 * @author whl
 * @date 2019年4月15日 
 */
public interface DepartmentService {
	/*
	 * 分页模糊条件查询
	 */
	public ResultPage<Department> findSearch(String queryName, int page, int size) throws BusinessRuntimeException;
	/*
	 * 部门信息的修改
	 */
	public  ResultObject<Department> saveDepartment(Department department)throws BusinessRuntimeException;
	/*
	 * 部门信息的删除
	 */
	public ResultObject<Department> deletDep(long id)throws BusinessRuntimeException;
}
