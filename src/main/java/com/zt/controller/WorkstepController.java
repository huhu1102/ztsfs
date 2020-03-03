package com.zt.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.Workstep;
import com.zt.service.WorkstepService;

/**
 * @author wl
 * @date 2019年4月27日 
 */
@RestController
@RequestMapping("/workStep")
public class WorkstepController {
	
	@Autowired
	WorkstepService ws;
	

	/*
	 * 分页模糊条件查询
	 */
	//此方法要写
	@RequestMapping(value="/findbypage",method=RequestMethod.GET)
	public ResultPage<Workstep> findbypage(HttpServletRequest request,int page,int size,String queryName) {
		return ws.findSearch(queryName, page,size);
	}
	/*
	 * 查询所有
	 */
	@RequestMapping(value="/findAll",method=RequestMethod.POST)
	public ResultObject<Workstep> findAll(){
		return ws.findAll();
	}
	/*
	 * 更新与修改
	 */
	@RequestMapping(value="/stepAdd",method=RequestMethod.POST)
	  public ResultObject<Workstep> saveWor(Workstep workstep){
	     return ws.saveWorkstep(workstep);
	  }
	
	/*
	 * 删除
	 */
	@RequestMapping(value="/delet",method=RequestMethod.GET)
	  public ResultObject<Workstep> deletWor(long id){
	     return ws.deletWor(id);
	  }
}
