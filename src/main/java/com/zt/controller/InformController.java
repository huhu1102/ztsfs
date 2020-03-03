package com.zt.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.Client;
import com.zt.po.Inform;
import com.zt.po.Position;
import com.zt.service.InformService;

/**
 * @author wl
 * @date 2019年4月16日
 */
@RestController
@RequestMapping("/inform")
public class InformController {
	@Autowired
	 InformService informService;

	/*
	 * 分页模糊条件查询
	 */
	@RequestMapping(value = "/findbypage", method = RequestMethod.GET)
	public ResultPage<Inform> findbypage(HttpServletRequest request,int page,int size,String queryName) {
		return informService.findSearch(queryName, page,size);
	}
	/*
	 * 修改通告
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	  public ResultObject<Inform> saveEmp(Inform inform){
		return informService.saveInform(inform);
	  }	
	/*
	 * 删除
	 */
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	  public ResultObject<Inform> deletEmp(long id){
	     return informService.deletIn(id);
	  }

}
