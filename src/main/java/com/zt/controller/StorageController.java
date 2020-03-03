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

import com.zt.po.Storage;
import com.zt.service.StorageService;

/**
 * @author yh
 * @date 2019年5月10日
 */
@RestController
@RequestMapping(value="/storage")
public class StorageController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private StorageService  bdsc;
	
	@RequestMapping(value="/delet",method=RequestMethod.GET)
	  public ResultObject<Storage> deletStorage(long id){
	     return bdsc.deletStorage(id);
	  }
	
	//此方法要写
	@RequestMapping(value="/add",method=RequestMethod.POST)
	  public ResultObject<Storage> saveStorage(Storage bgg){
	     return bdsc.addStorage(bgg);
	  }
	//此方法要写
	@RequestMapping(value="/findbypage",method=RequestMethod.GET)
	public ResultPage<Storage> findbypage(HttpServletRequest request,int page,int size,String designation) {
		return bdsc.findStorage(designation, page, size);
	}

}
