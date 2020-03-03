package com.zt.controller;

import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.RowMaterialOutRecieving;
import com.zt.service.RowMaterialOutRecievingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author whl
 * @date 2019年6月28日
 * 
 */
@RestController
@RequestMapping(value="/materialOutReceiveRecord")
public class RowMaterialOutRecievingController {
	@Autowired
	RowMaterialOutRecievingService receiveService;
	
	/*
	 * 分页模糊条件查询
	 */
	@RequestMapping(value="/findbypage",method=RequestMethod.GET)
	public ResultPage<RowMaterialOutRecieving> findbypage(HttpServletRequest request, int page, int size, String queryName) {
		return receiveService.findSearch(queryName, page,size);
	}
	/*
	 * 撤回
	 */
	@RequestMapping(value="/withdraw",method=RequestMethod.GET)
	public ResultObject<RowMaterialOutRecieving> withdraw(long id){
		return receiveService.withdraw(id);
	}
	/*
	 * 新建
	 */
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public ResultObject<RowMaterialOutRecieving> addNew(long id, float quaity, String notes){
		return receiveService.addNew(id,quaity,notes);
	}
	
	
	
}
