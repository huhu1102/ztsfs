/**  
* 
*/  
 
package com.zt.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.Department;
import com.zt.po.Employee;
import com.zt.service.DepartmentService;
import com.zt.serviceImp.DepartmentServiceImp;

/**
 * @author whl
 * @date 2019年4月15日 
 */
@RestController
@RequestMapping(value="/depart")
public class DepartmentController {
	@Autowired
	private DepartmentService departmentService;
	/*
	 * 分页模糊条件查询
	 */
	@RequestMapping(value="/findbypage",method=RequestMethod.GET)
	public ResultPage<Department> findbypage(HttpServletRequest request,int page,int size,String queryName) {
		return departmentService.findSearch(queryName, page,size);
	}
	/*
	 * 部门信息的保存和修改
	 */
	@RequestMapping(value="/depAdd",method=RequestMethod.POST)
	  public ResultObject<Department> saveEmp(Department department){
	     return departmentService.saveDepartment(department);
	  }
	/*
	 * 删除
	 */
	@RequestMapping(value="/deletDep",method=RequestMethod.GET)
	  public ResultObject<Department> deletEmp(long id){
	     return departmentService.deletDep(id);
	  }
}
