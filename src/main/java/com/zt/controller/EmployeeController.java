/**
 * 
 */
package com.zt.controller;


import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.Employee;
import com.zt.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author wl
 * @date 2019年4月12日 
 * 员工的控制器层
 */

@RestController
@RequestMapping("/emp")
public class EmployeeController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private EmployeeService employeeService;
	/*
	 * 员工注册
	 */
	@RequestMapping(value="/insert",method=RequestMethod.POST)
	public void insert(@RequestBody Employee employee) {
		
	}
	/*
	 * 分页查询
	 */
	//
	@RequestMapping(value="/page",method=RequestMethod.GET)
	  public ResultPage<Employee> saveEmployee(int  page,int size){
		
	      return  employeeService.findbyPage(page, size);
	  }

	/**
	 *  分页模糊查询
	 * @param page
	 * @param size
	 * @param queryName 姓名
	 * @param phone 电话
	 * @param addr 地址
	 * @param dept 部门
	 * @return
	 */
	@RequestMapping(value="/findbypage",method=RequestMethod.GET)
	public ResultPage<Employee> findbypage(int page,
										   int size,
										   String queryName,
										   String phone,
										   String addr,
										   String dept
	                                       ) {
		logger.info("当前第"+page+"页");
		return employeeService.findSearch(queryName, page,size,phone,addr,dept);
	}
	

	/**
	 * @param employee
	 * @return	
	 *  员工保存与更新
	 */
	//此方法要写
	@RequestMapping(value="/empAdd",method=RequestMethod.POST)
	  public ResultObject<Employee> saveEmp(String deptIds,String postIds, Employee employee){
             logger.info("deptIds"+deptIds);
	     return employeeService.saveEmpoyee(deptIds,postIds,employee);
	  }
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public ResultObject<Employee> update(String deptIds,String postIds,Employee employee){
		return employeeService.updateData(deptIds,postIds,employee);
	}

	/**
	 * @param id
	 * @return
	 * 删除员工方法
	 */
	//此方法要写
	@RequestMapping(value="/deletEmp",method=RequestMethod.GET)
	  public ResultObject<Employee> deletEmp(long id){
	     return employeeService.deletEmp(id);
	  }
	/**
	 * @param
	 * @return
	 *  查询钱前台页面在员工修改页面需要的数据
	 */
	@RequestMapping(value="/basicdata",method=RequestMethod.GET)
	public ResultObject<Object> basicdata(){

		return employeeService.getBaseData();
	}
	/**
	 * @param telphone
	 * @return
	 * 查询电话号码是否唯一
	 */
	@RequestMapping(value="/uniqetel",method=RequestMethod.GET)
	  public ResultObject<Object> uniqetel(String telphone){
		return employeeService.uniqeTel(telphone);
	  }
	
	@RequestMapping(value="/currentNo",method=RequestMethod.GET)
	public ResultObject<Object> currentNo(){
		return employeeService.currentNo();
	}
}
