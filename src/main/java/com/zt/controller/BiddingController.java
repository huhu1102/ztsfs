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
import com.zt.po.MiddleProduct;
import com.zt.service.BiddingService;
import com.zt.service.MiddleProductService;

/**
 * @author yh
 * @date 2019年5月9日
 */
@RestController
@RequestMapping(value="/bidding")
public class BiddingController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private BiddingService  bdsc;
	
	@RequestMapping(value="/delet",method=RequestMethod.GET)
	  public ResultObject<Bidding> deletvf(long id){
	     return bdsc.deletBidding(id);
	  }
	
	//此方法要写
	@RequestMapping(value="/add",method=RequestMethod.POST)
	  public ResultObject<Bidding> savevf(Bidding bgg){
	     return bdsc.addBidding(bgg);
	  }
	//此方法要写
	@RequestMapping(value="/findbypage",method=RequestMethod.GET)
	public ResultPage<Bidding> findbypage(HttpServletRequest request,int page,int size,String queryName) {
		return bdsc.findBidding(queryName, page, size);
	}
}
