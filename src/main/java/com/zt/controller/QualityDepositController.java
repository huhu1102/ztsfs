package com.zt.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zt.model.ResultObject;
import com.zt.po.QualityDeposit;
import com.zt.service.QualityDepositService;

/**
 * @author wl
 * @date 2019年5月6日 
 */
@RestController
@RequestMapping("/qualitydeposit")
public class QualityDepositController {
	@Autowired
	QualityDepositService quaService;
	
	/*
	 * 更新与修改
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	  public ResultObject<QualityDeposit> saveDeli(QualityDeposit quade){
	     return quaService.savePosition(quade);
	  }
	
	/*
	 * 删除
	 */
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	  public ResultObject<QualityDeposit> deletDeli(long id){
	     return quaService.deletDel(id);
	  }
}
