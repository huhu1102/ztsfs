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
import com.zt.po.MiddleProduct;
import com.zt.po.RowMaterial;
import com.zt.service.MiddleProductService;

/**
 * @author yh
 * @date 2019年5月9日
 */
@RestController
@RequestMapping(value="/middleProduct")
public class MiddleProductController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private MiddleProductService  middleProductService;
	
	@RequestMapping(value="/delet",method=RequestMethod.GET)
	  public ResultObject<MiddleProduct> deletvf(long id){
	     return middleProductService.deletMiddleProduct(id);
	  }
	
	//此方法要写
	@RequestMapping(value="/add",method=RequestMethod.POST)
	  public ResultObject<MiddleProduct> save(MiddleProduct product){
	     return middleProductService.addMiddleProduct(product);
	  }
	@RequestMapping(value="/update",method=RequestMethod.POST)
	  public ResultObject<MiddleProduct> update(MiddleProduct mdt){
	     return middleProductService.updateMiddleProduct(mdt);
	  }
	//此方法要写
	@RequestMapping(value="/findbypage",method=RequestMethod.GET)
	public ResultPage<MiddleProduct> findbypage(HttpServletRequest request,int page,int size,String queryName) {
		return middleProductService.findMiddleProduct(queryName, page, size);
	}
	@RequestMapping(value="/basedata",method=RequestMethod.GET)
	  public ResultObject<MiddleProduct> basedata(){
	     return middleProductService.basedata();
	  }
	@RequestMapping(value="/currentPlan",method=RequestMethod.GET)
	public ResultObject<MiddleProduct> getCurrentPlan(){
		return middleProductService.getCurrentPlan();
	}
}
