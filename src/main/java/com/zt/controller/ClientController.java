/**
 * 
 */
package com.zt.controller;


import javax.servlet.http.HttpServletRequest;

import com.zt.po.SalesPlan;
import org.omg.CORBA.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.Client;
import com.zt.service.ClientService;
import com.zt.vo.ClientModel;

/**
 * @author wl
 * @date 2019年4月15日 
 * 客户信息的控制层
 */
@RestController
@RequestMapping("/client")
public class ClientController {
	@Autowired
	ClientService clientService;
	/*
	 * 新增
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public ResultObject<Client> saveCli(ClientModel client){
	     return clientService.saveClient(client);
	  }
	
	/*
	 * 修改
	 */
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public ResultObject<Client> update(ClientModel client){
	     return clientService.update(client);
	  }
	
	/*
	 * 分页模糊条件查询
	 */
	@RequestMapping(value="/findbypage",method=RequestMethod.GET)
	public ResultPage<Client> findbypage(int page,int size,String queryName,String types,String shopType) {
		return clientService.findSearch(queryName, page,size,types,shopType);
	}

	/*
	查询最近三次的销售计划
	 */
	@RequestMapping(value="/findsalesplan",method = RequestMethod.GET)
	public ResultPage<SalesPlan> findSalesPlan(long clientId,int page,int size){
		return clientService.findSalesPlan(clientId,page,size);
	}

	/*
	 * 删除
	 */
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	  public ResultObject<Client> deletEmp(long id){
	     return clientService.deletCli(id);
	  }
	@RequestMapping(value="/baseData",method=RequestMethod.GET)
	public ResultObject<Client> getbasdata(){
		return clientService.getbasdata();
	}
	@RequestMapping(value="/uploadfiles",method=RequestMethod.POST)
	public String uploadfile(HttpServletRequest request){
		return "success";
	}

}