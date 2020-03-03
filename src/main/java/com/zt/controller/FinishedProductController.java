package com.zt.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.Client;
import com.zt.po.FinishedProduct;
import com.zt.service.FinishedProductService;

/**
 * @author wl
 * @date 2019年5月13日 
 */
@RestController
@RequestMapping("/finishedProduct")
public class FinishedProductController {
	@Autowired
	FinishedProductService fpService;
	
	/*
	 * 分页模糊条件查询
	 */
	@RequestMapping(value="/findbypage",method=RequestMethod.GET)
	public ResultPage<FinishedProduct> findbypage(HttpServletRequest request,int page,int size,String queryName) {
		return fpService.findSearch(queryName, page,size);
	}

	/*
	 * 删除
	 */
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	  public ResultObject<FinishedProduct> deletDeli(long id){
	     return fpService.deletDel(id);
	  } 
	
	
	/*
	 * 删除
	 */
	@RequestMapping(value="/basedata",method=RequestMethod.GET)
	public ResultObject<FinishedProduct> basedata(){
		return fpService.basedata();
	} 
	
	/*
	 * 更新
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public ResultObject<FinishedProduct> saveCli(FinishedProduct finishedProduct){
	     return fpService.saveClient(finishedProduct);
	  }
}
