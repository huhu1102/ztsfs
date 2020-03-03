package com.zt.controller;

import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.MidProductInRecieving;
import com.zt.po.MidProductOutRecieving;
import com.zt.service.MidProductInRecievingService;
import com.zt.service.MidProductOutRecievingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wl
 * @date 2019年7月1日 
 */
@RestController
@RequestMapping("/midProductOutRecieving")
public class MidProductOutRecievingController {

	@Autowired
	MidProductOutRecievingService mprservice;
	/*
	 * 撤回
	 */
	@RequestMapping(value="/withdraw",method=RequestMethod.GET)
	public ResultObject<MidProductOutRecieving> withdraw(long id){
		return mprservice.withdraw(id);
	}
	/*
	 * 分页查询
	 */
	@RequestMapping(value="/findbypage",method=RequestMethod.GET)
	public ResultPage<MidProductOutRecieving> findbypage(HttpServletRequest request,int page,int size,String queryName) {
		return mprservice.findSearch(queryName, page,size);
	}
	/*
	 * 新建
	 */
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public ResultObject<MidProductOutRecieving> addNew(long id, float quaity, String notes) {
		return mprservice.addNew(id, quaity,notes);
	}
	
}
