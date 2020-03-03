package com.zt.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.Position;
import com.zt.service.PositionService;

/**
 * @author wl
 * @date 2019年4月27日 
 */
@RestController
@RequestMapping("/position")
public class PositionController {
	@Autowired
	PositionService positionService;
	/*
	 * 分页模糊条件查询
	 */
	//此方法要写
	@RequestMapping(value="/findbypage",method=RequestMethod.GET)
	public ResultPage<Position> findbypage(HttpServletRequest request,int page,int size,String queryName) {
		return positionService.findSearch(queryName, page,size);
	}
	/*
	 * 更新与修改
	 */
	@RequestMapping(value="/posAdd",method=RequestMethod.POST)
	  public ResultObject<Position> saveEmp(Position position){
	     return positionService.savePosition(position);
	  }
	/*
	 * 删除
	 */
	@RequestMapping(value="/deletPro",method=RequestMethod.GET)
	  public ResultObject<Position> deletEmp(long id){
	     return positionService.deletDep(id);
	  }
}
