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

import com.zt.dao.BiddingResultDao;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.BiddingNotice;
import com.zt.po.BiddingResult;
import com.zt.service.BiddingResultService;


/**
 * @author yh
 * @date 2019年5月9日
 */
@RestController
@RequestMapping(value="/biddingNotice")
public class BiddingResultController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private BiddingResultService  bdtsc;
	
	@RequestMapping(value="/delet",method=RequestMethod.GET)
	  public ResultObject<BiddingResult> deletvf(long id){
	     return bdtsc.deletBiddingOutCome(id);
	  }
	
	//此方法要写
	@RequestMapping(value="/add",method=RequestMethod.POST)
	  public ResultObject<BiddingResult> savevf(BiddingResult bdgcm){
	     return bdtsc.addBiddingOutCome(bdgcm);
	  }
	//此方法要写
	@RequestMapping(value="/findbypage",method=RequestMethod.GET)
	public ResultPage<BiddingResult> findbypage(HttpServletRequest request,int page,int size,String completeStatus) {
		return bdtsc.findBiddingOutCome(completeStatus, page, size);
	}

}
