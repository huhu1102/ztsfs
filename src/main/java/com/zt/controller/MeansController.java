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

import com.zt.po.Means;
import com.zt.service.MeansService;

/**
 * @author yh
 * @date 2019年5月9日
 */
@RestController
@RequestMapping(value="/means")
public class MeansController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private MeansService  ms;
	
	@RequestMapping(value="/delet",method=RequestMethod.GET)
	  public ResultObject<Means> deletMeans(long id){
	     return ms.deletMeans(id);
	  }
	
	//此方法要写
	@RequestMapping(value="/add",method=RequestMethod.POST)
	  public ResultObject<Means> saveMeans(Means itlce){
	     return ms.addMeans(itlce);
	  }
	//此方法要写
	@RequestMapping(value="/findbypage",method=RequestMethod.GET)
	public ResultPage<Means> findbypage(HttpServletRequest request,int page,int size,String meansName) {
		return ms.findMeans(meansName, page, size);
	}
}
