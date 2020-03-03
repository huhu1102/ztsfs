/**  
* 
*/  
 
package com.zt.service;



import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.*;

/**
 * @author whl
 * @date 2019年5月20日 
 */
public interface SysRoleService {

	/**
	 * @return
	 */
	ResultPage<SysRole> findbypages()throws BusinessRuntimeException;

	/**
	 * @param product
	 * @return
	 */
	ResultObject<SysRole> save(SysRole product)throws BusinessRuntimeException;

	/**
	 * @param id
	 * @return
	 */
	ResultObject<SysRole> delete(Integer id)throws BusinessRuntimeException;


	ResultObject<Menu> findMenus (long roleId)throws BusinessRuntimeException;


	ResultObject<Menu> updateMenuRole(Integer rid, Long[] mids);
}
