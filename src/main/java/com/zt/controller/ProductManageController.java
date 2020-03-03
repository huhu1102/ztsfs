package com.zt.controller;

import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.ProductManage;
import com.zt.po.ProductManageDetails;
import com.zt.service.ProductManageService;
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
@RequestMapping("/productmanage")
public class ProductManageController {
	@Autowired
	ProductManageService pmService;
	/**
	 * 分页查询
	 */
	@RequestMapping(value="/findbypage",method=RequestMethod.GET)
	public ResultPage<ProductManage> findbypage(String clientName, String productName, String empName, String start, String end, Integer status,int page,int size) {
		return pmService.findSearch(clientName,productName,empName,start,end,status,page,size);
	}
	/**
	 * 分页查询
	 */
	@RequestMapping(value="/findForSend",method=RequestMethod.GET)
	public ResultPage<ProductManage> findForSend(String clientName, String productName, String empName, String start, String end, Integer status,int page,int size) {
		return pmService.findForSend(clientName,productName,empName,start,end,status,page,size);
	}
	/*
	查询状态条数
	 */
	@RequestMapping(value = "findStatus",method = RequestMethod.GET)
	public ResultObject<ProductManage> findStatus(){
		return pmService.findStatus();
	}

	/*
	 * 新增生产管理
	 * @Param progressId 生产工序Id
	 * @Param planDetailsId 生产计划详情Id
	 * @Param note 备注
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public ResultObject<ProductManage> addProductionManage(String note,long planDetailsId,long progressId){
		return pmService.addProductionManage(note, planDetailsId,progressId );
		
	}
	/*
	 * 修改生产管理
	 */
	@RequestMapping(value = "/update",method = RequestMethod.POST)
	public ResultObject<ProductManage> update(String note,long id) throws Exception{
		return pmService.update(note,id);
	}
	/*
	 * 删除
	 */
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	  public ResultObject<ProductManage> deletDeli(long id){
	     return pmService.deletPm(id);
	  }
	  /*
	  确认生产完成
	   */
	  @RequestMapping(value = "/end",method = RequestMethod.GET)
	public ResultObject<ProductManage> endProduction(long id){
	  	return pmService.endProduction(id);
	  }


	/*
	撤销发货请求的状态
	 */
	@RequestMapping(value = "/revoke",method = RequestMethod.GET)
	public ResultObject<ProductManage> revoke(long id){
		return pmService.revoke(id);
	}
}
