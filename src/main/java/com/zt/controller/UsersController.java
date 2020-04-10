/**  
* 
*/  
 
package com.zt.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.SysRole;
import com.zt.po.Users;
import com.zt.service.UsersService;
import com.zt.vo.UsersModel;

/**
 * @author whl
 * @date 2019年5月5日 
 */
@RestController
@RequestMapping("/users")
public class UsersController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	UsersService userService;
	
	/**
	 *    登录方法
	 *
	 * @param password
	 * @return
	 */
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	  public ResultObject<Users> saveEmployee(@RequestParam(value="username") String  username,String  password){
		logger.info(username+"请求登录");
	    return userService.Login(username, password);
	  }


	/*
	 * 分页模糊条件查询
	 */
	@RequestMapping(value = "/findbypage", method = RequestMethod.GET)
	public ResultPage<Users> findbypage(HttpServletRequest request,int page,int size,String queryName) {
		return userService.findbypages(queryName, page,size);
	}
	/*
	 * 更新与修改
	 */
	@RequestMapping(value="/addAndUpdate",method=RequestMethod.POST)
	  public ResultObject<Users> save(UsersModel user){
	     return userService.save(user);
	  }
	@RequestMapping(value="/update",method=RequestMethod.POST)
	  public ResultObject<Users> update(UsersModel user){
	     return userService.update(user);
	  }
	
	/*
	 * 删除
	 */
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	  public ResultObject<Users> delete(Integer id){
	     return userService.delete(id);
	  }
	
	/**
	 * @param
	 * @return
	 */
	@RequestMapping(value="/basicdata",method=RequestMethod.GET)
	public ResultObject<Users> getbaseData(){
		return userService.getBaseData();
	}
	
	
}
