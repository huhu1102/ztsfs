package com.zt.service;

import java.util.List;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.SysRole;
import com.zt.po.Users;
import com.zt.vo.UsersModel;

/**
 * @author wl
 * @date 2019年4月23日
 *  
 */
public interface UsersService {
	ResultObject<Users> Login( String username, String password)throws BusinessRuntimeException;

	/**
	 * @param userName
	 * @return
	 */
	public ResultObject<Users> getMenu()throws BusinessRuntimeException;
	public Users loadUserByUsername(String username)throws Exception;

	/**
	 * @param userId 
	 * @return
	 */
	List<Users> getAllUsersExceptAdmin(long userId)throws BusinessRuntimeException;

	/**
	 * @param queryName
	 * @param page
	 * @param size
	 * @return
	 */
	ResultPage<Users> findbypages(String queryName, int page, int size)throws BusinessRuntimeException;

	/**
	 * @param product
	 * @return
	 */
	ResultObject<Users> save(UsersModel user)throws BusinessRuntimeException;

	/**
	 * @param id
	 * @return
	 */
	ResultObject<Users> delete(long id)throws BusinessRuntimeException;

	/**
	 * @return
	 */
	ResultObject<Users> getBaseData()throws BusinessRuntimeException;

	/**
	 * @param user
	 * @return
	 */
	ResultObject<Users> update(UsersModel user)throws BusinessRuntimeException;
}
