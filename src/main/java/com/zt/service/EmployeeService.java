
package com.zt.service;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.Employee;

/**
* @author wl
* @version 2019年4月13日 
* 员工信息的业务层接口
*/
public interface EmployeeService {
    /*
     * 分页查询无条件
     */
    ResultPage<Employee> findbyPage(Integer page,Integer size)throws BusinessRuntimeException;
	/**
	 * @param username
	 * @return
	 */
	public Employee loadUserByUsername(String username)throws Exception;

	/**
	 * @param employee
	 * @return
	 */
	public  ResultObject<Employee> saveEmpoyee(String deptIds, String postIds, Employee employee)throws BusinessRuntimeException;
	/**
	 * @param id
	 * @return
	 */
	public ResultObject<Employee> deletEmp(long id)throws BusinessRuntimeException;
	/**
	 * @param queryName
	 * @param i
	 * @param size
	 * @return
	 */
	public ResultPage<Employee> findSearch(String queryName, int page, int size,String phone,String addr,String dept) throws BusinessRuntimeException;

	/**
	 * @return
	 */
	public ResultObject<Object> getBaseData();

	/**
	 * @return
	 */
	public  ResultObject<Object> uniqeTel(String telphone)throws BusinessRuntimeException;
	/**
	 * @return
	 */
	ResultObject<Object> currentNo()throws BusinessRuntimeException;

	ResultObject<Employee> updateData(String deptIds, String postIds, Employee employee)throws BusinessRuntimeException;

}
