package com.zt.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.Position;
import com.zt.po.Product;
import com.zt.po.ProductProcess;
import com.zt.service.ProductProcessService;

/**
 * @author wl
 * @date 2019年4月18日 
 */
@RestController
@RequestMapping("/ppro")
public class ProductProcessController {
	@Autowired
	ProductProcessService pps ;
	/*
	 * 根据产品查询工序
	 */
	@RequestMapping(value="/findbyproduct",method=RequestMethod.GET)
	public ResultObject<ProductProcess> findByProduct(String productName){
		ResultObject<ProductProcess> ppp = new ResultObject<>();
		try {
			ppp = pps.findProductProcess(productName);
			ppp.setSuccess(true);
			ppp.setMsg("成功!");
		} catch (Exception e) {
			e.printStackTrace();
			ppp.setMsg("失败");
			ppp.setSuccess(false);
		}
		return ppp;
	}
	/*
	 * 分页查询
	 */
	@RequestMapping(value="/findbypage",method=RequestMethod.GET)
	public ResultPage<ProductProcess> findbypage(HttpServletRequest request,int page,int size,String queryName) {
		return pps.findSearch(queryName, page,size);
	}
	/*
	 * 更新与修改
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	  public ResultObject<ProductProcess> save(ProductProcess productProcess){
	     return pps.saveProductProcess(productProcess);
	  }
	
	/*
	 * 删除
	 */
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	  public ResultObject<ProductProcess> delet(long id){
	     return pps.deletDep(id);
	  }
	
	
	
}
