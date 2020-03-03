package com.zt.controller;

import com.zt.model.ProcessModel;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.ProductPreProcess;
import com.zt.service.ProductPreProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wl
 * @date 2019年5月7日 
 */
@RestController
@RequestMapping("/productpreprocess")
public class ProductPreProcessController {
	@Autowired
	ProductPreProcessService pppService;
	
	
	/*
	 * 分页模糊条件查询
	 */
	@RequestMapping(value="/findbypage",method=RequestMethod.GET)
	public ResultPage<ProductPreProcess> findbypage(HttpServletRequest request,int page,int size,String queryName) {
		return pppService.findSearch(queryName, page,size);
	}
	/*
	根据工序id查工序
	 */
	@RequestMapping(value = "/findbyid",method = RequestMethod.GET)
	public ResultObject<ProductPreProcess> findById(long id){
		return pppService.findById(id);
	}
	/*
	 * 更新与修改
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	  public ResultObject<ProductPreProcess> saved( ProcessModel progeress){
	     return pppService.save(progeress);
	  }
	@RequestMapping(value="/update",method=RequestMethod.POST)
	  public ResultObject<ProductPreProcess> updateData( ProcessModel progeress){
	     return pppService.update(progeress);
	  }
	
	/*
	 * 删除
	 */
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	  public ResultObject<ProductPreProcess> deleteData(long id){
	     return pppService.deletDel(id);
	  }
}
