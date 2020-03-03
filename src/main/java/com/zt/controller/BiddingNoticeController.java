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
import com.zt.po.BiddingNotice;
import com.zt.po.BiddingResult;
import com.zt.service.BiddingNoticeService;

/**
 * @author yh
 * @date 2019年5月10日
 */
@RestController
@RequestMapping(value="/biddingResult")
public class BiddingNoticeController  {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private BiddingNoticeService  bdsc;
	
	@RequestMapping(value="/delet",method=RequestMethod.GET)
	  public ResultObject<BiddingNotice> deletTenderNotice(long id){
	     return bdsc.deletTenderNotice(id);
	  }
	
	//此方法要写
	@RequestMapping(value="/add",method=RequestMethod.POST)
	  public ResultObject<BiddingNotice> saveTenderNotice(BiddingNotice bgg){
	     return bdsc.addTenderNotice(bgg);
	  }
	//此方法要写
	@RequestMapping(value="/findbypage",method=RequestMethod.GET)
	public ResultPage<BiddingNotice> findbypage(HttpServletRequest request,int page,int size,String tdInstitution) {
		return bdsc.findTenderNotice(tdInstitution, page, size);
	}
}
