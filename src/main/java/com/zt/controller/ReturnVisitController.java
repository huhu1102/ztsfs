package com.zt.controller;

import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.ReturnVisit;
import com.zt.service.ReturnVisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 回访信息 控制器
 * @author wl
 * @date 2019年5月9日 
 */
@RestController
@RequestMapping("/returnvisit")
public class ReturnVisitController {
	@Autowired
	ReturnVisitService rvService;	
	/*
	 * 分页模糊条件查询
	 */
	@RequestMapping(value="/findbypage",method=RequestMethod.GET)
	public ResultPage<ReturnVisit> findbypage(HttpServletRequest request,int page,int size,String queryName) {
		return rvService.findSearch(queryName, page,size);
	}

	@RequestMapping(value="/findByBillId/{billId}",method=RequestMethod.GET)
	public ResultPage<ReturnVisit> findByBillId(@PathVariable long billId) {
		return rvService.findByBillId(billId);
	}
	/*
	 * 更新与修改
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	  public ResultObject<ReturnVisit> saveDeli(ReturnVisit rv){
	     return rvService.savePosition(rv);
	  }
	
	/*
	 * 删除
	 */
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	  public ResultObject<ReturnVisit> deletebyIds(long id){
	     return rvService.deletDel(id);
	  }

}