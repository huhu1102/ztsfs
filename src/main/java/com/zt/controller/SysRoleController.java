package com.zt.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.Menu;
import com.zt.po.SysRole;
import com.zt.service.SysRoleService;

/**
 * @author wl
 * @date 2019年4月18日
 */
@RestController
@RequestMapping("/sysrole")
public class SysRoleController {
	@Autowired
	SysRoleService roleService;

	/*
	 * 分页模糊条件查询
	 */
	@RequestMapping(value = "/findbypage", method = RequestMethod.GET)
	public ResultPage<SysRole> findbypage(HttpServletRequest request) {

		return roleService.findbypages();
	}
	/*
	 * 更新与修改
	 */
	@RequestMapping(value="/addAndUpdate",method=RequestMethod.POST)
	  public ResultObject<SysRole> saves(SysRole role){
	     return roleService.save(role);
	  }
	
	/*
	 * 删除
	 */
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	  public ResultObject<SysRole> deletEmp(Integer id){
	     return roleService.delete(id);
	  }

	 /**
	   * 查找所有组件
	   *
	  */
	  @RequestMapping(value="/treeRole",method=RequestMethod.GET)
	  public ResultObject<Menu> treeRole(long roleid){
	     return roleService.findMenus(roleid);
	  }

	/**
	 *  更新权限
	 * @param rid 角色Id
	 * mids 组件Ids
	 * @return
	 */
	@RequestMapping(value="/updateMenuRole",method=RequestMethod.POST)
	public ResultObject<Menu> updateMenuRole(Integer rid ,Long[] mids){
		return roleService.updateMenuRole(rid,mids);
	}
	
}
