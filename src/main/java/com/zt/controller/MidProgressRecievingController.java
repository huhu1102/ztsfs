package com.zt.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zt.model.ResultPage;
import com.zt.po.MidProductPurchaseVerify;
import com.zt.po.MidProgressRecieving;
import com.zt.service.MidProgressRecievingService;

/**
 * @author whl
 * @date 2019年7月5日 
 */
@RestController
@RequestMapping("/midProgressRecieve")
public class MidProgressRecievingController {

	@Autowired
	MidProgressRecievingService   recivingSevice;
	/*
	 * 分页查询
	 */
	@RequestMapping(value="/findbypage",method=RequestMethod.GET)
	public ResultPage<MidProgressRecieving> findbypage(String clientName,String productName,String empName,String start,String end,int page,int size) {
		return recivingSevice.findbyPage(clientName,productName,empName,start,end,page, size);
	}
	
}
