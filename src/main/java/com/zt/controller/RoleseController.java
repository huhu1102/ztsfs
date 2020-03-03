package com.zt.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zt.model.ResultPage;
import com.zt.po.Rolese;
import com.zt.serviceImp.RoleseServiceImp;

/**
 * 角色
 * @author whl
 * @date 2019年5月5日
 */

@RestController
@RequestMapping(value="/role")
public class RoleseController {
  @Autowired
  private  RoleseServiceImp roleseve;
  
  @RequestMapping(value="/page",method=RequestMethod.GET)
  public ResultPage<Rolese> saveUser(int  page,int size){
	  ResultPage<Rolese> ro=new ResultPage<>();	  
	 String sort="DESC";
	 try {
		 ro = roleseve.findbyPage(page, size, sort);
		 ro.setSuccess(true);
	} catch (Exception e) {
         ro.setSuccess(false);
	}
      return ro;
  }
}
