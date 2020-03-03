/**
 * 
 */
package com.zt.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.Bidding;
import com.zt.po.Intelligence;
import com.zt.service.IntelligenceService;

/**
 * @author yh
 * @date 2019年5月9日
 */
@RestController
@RequestMapping(value="/intelligence")
public class IntelligenceController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private IntelligenceService  itgcs;
	
	@RequestMapping(value="/delet",method=RequestMethod.GET)
	  public ResultObject<Intelligence> deletIntelligence(long id){
	     return itgcs.deletIntelligence(id);
	  }
	
	//此方法要写
	@RequestMapping(value="/add",method=RequestMethod.POST)
	  public ResultObject<Intelligence> saveIntelligence(Intelligence itlce){
	     return itgcs.addIntelligence(itlce);
	  }
	//此方法要写
	@RequestMapping(value="/findbypage",method=RequestMethod.GET)
	public ResultPage<Intelligence> findbypage(HttpServletRequest request,int page,int size,String type) {
		return itgcs.findIntelligence(type, page, size);
	}
}
