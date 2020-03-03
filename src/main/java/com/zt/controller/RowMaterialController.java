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
import com.zt.po.RowMaterial;
import com.zt.po.VehicleInfo;
import com.zt.service.RowMaterialService;


/**
 * @author yh
 * @date 2019年4月28日
 */
@RestController
@RequestMapping(value="/material")
public class RowMaterialController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	 RowMaterialService acrService;
	
	@RequestMapping(value="/delet",method=RequestMethod.GET)
	  public ResultObject<RowMaterial> deletvf(long id){
	     return acrService.deletRowMaterial(id);
	  }
	
	//此方法要写
	@RequestMapping(value="/add",method=RequestMethod.POST)
	  public ResultObject<RowMaterial> savevf(RowMaterial accs){
	     return acrService.addRowMaterial(accs);
	  }
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	  public ResultObject<RowMaterial> update(RowMaterial accs){
	     return acrService.updateRowMaterial(accs);
	  }
	
	//此方法要写
	@RequestMapping(value="/findbypage",method=RequestMethod.GET)
	public ResultPage<RowMaterial> findbypage(HttpServletRequest request,int page,int size,String queryName) {
		return acrService.findRowMaterial(queryName, page, size);
	}
	@RequestMapping(value="/basedata",method=RequestMethod.GET)
	  public ResultObject<RowMaterial> basedata(){
	     return acrService.basedata();
	  }
	/**
	 * @param id
	 * @param quantity  出库数量
	 * @return
	 */
	@RequestMapping(value="/receive",method=RequestMethod.GET)
	public ResultObject<RowMaterial> receive(long id,float quantity){
		return acrService.receive(id,quantity);
	}
}
