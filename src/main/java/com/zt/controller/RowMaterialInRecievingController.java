package com.zt.controller;

import javax.servlet.http.HttpServletRequest;

import com.zt.po.MidProductInRecieving;
import com.zt.po.RowMaterialInRecieving;
import com.zt.service.RowMaterialInRecievingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zt.model.ResultObject;
import com.zt.model.ResultPage;

/**
 * @author whl
 * @date 2019年6月28日
 * 
 */
@RestController
@RequestMapping(value="/materialInReceiveRecord")
public class RowMaterialInRecievingController {
	@Autowired
	RowMaterialInRecievingService receiveService;
	
	/*
	 * 分页模糊条件查询
	 */
	@RequestMapping(value="/findbypage",method=RequestMethod.GET)
	public ResultPage<RowMaterialInRecieving> findbypage(HttpServletRequest request, int page, int size, String queryName) {
		return receiveService.findSearch(queryName, page,size);
	}
	/*
	 * 撤回
	 */
	@RequestMapping(value="/withdraw",method=RequestMethod.GET)
	public ResultObject<RowMaterialInRecieving> withdraw(long id){
		return receiveService.withdraw(id);
	}
	
	/*
	 * 新增
	 */
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public ResultObject<RowMaterialInRecieving> addNew(long id, float quaity, String notes){
		return receiveService.addNew(id, quaity, notes);
	}
}
