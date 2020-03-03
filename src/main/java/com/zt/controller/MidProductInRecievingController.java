package com.zt.controller;

import javax.servlet.http.HttpServletRequest;

import com.zt.po.MidProductInRecieving;
import com.zt.service.MidProductInRecievingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zt.model.ResultObject;
import com.zt.model.ResultPage;

/**
 * @author wl
 * @date 2019年7月1日 
 */
@RestController
@RequestMapping("/midProductInRecieving")
public class MidProductInRecievingController {

	@Autowired
	MidProductInRecievingService mprservice;
	/*
	 * 撤回
	 */
	@RequestMapping(value="/withdraw",method=RequestMethod.GET)
	public ResultObject<MidProductInRecieving> withdraw(long id){
		return mprservice.withdraw(id);
	}
	/*
	 * 分页查询
	 */
	@RequestMapping(value="/findbypage",method=RequestMethod.GET)
	public ResultPage<MidProductInRecieving> findbypage(HttpServletRequest request,int page,int size,String queryName) {
		return mprservice.findSearch(queryName, page,size);
	}
	/*
	 * 新增
	 */
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public ResultObject<MidProductInRecieving> addNew(long id, float quaity, String notes){
		return mprservice.addNew(id, quaity, notes);
	}
}
