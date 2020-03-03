package com.zt.controller;

import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.Product;
import com.zt.po.ProductPreProcess;
import com.zt.service.ProductService;
import com.zt.vo.ProductModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wl
 * @date 2019年4月18日
 */
@RestController
@RequestMapping("/product")
public class ProductController {
	@Autowired
	ProductService productService;

	/*
	 * 分页模糊条件查询
	 */
	@RequestMapping(value = "/findbypage",	 method = RequestMethod.GET)
	public ResultPage<Product> findbypage(HttpServletRequest request,String queryName,String keycolorName,String keyspeName,String keyUse, int page, int size) {
		return productService.findSearch(queryName,keycolorName,keyUse,keyspeName, page,size);
	}
	/*
	分页查询所有
	 */
	@RequestMapping(value = "findall",method = RequestMethod.GET)
	public ResultPage<Object> findAll(int page,int size){
		return productService.findAll(page,size);
	}
	/*
	 * 修改
	 */
	@RequestMapping(value="/posAdd",method=RequestMethod.POST)
	  public ResultObject<Product> save(ProductModel product){
	     return productService.saveProduct(product);	
	  }
	/*
	 * 新增
	 */
	@RequestMapping(value="/addNew",method=RequestMethod.POST)
	public ResultObject<Product> addProduct(ProductModel productModel) throws Exception{
		return productService.addProduct(productModel);
	}
	
	/*
	 * 删除
	 */
	@RequestMapping(value="/deletPro",method=RequestMethod.GET)
	  public ResultObject<Product> deletEmp(long id){
	     return productService.deletPro(id);
	  }
	/**
	 * @PARAM
	 * @return
	 *  查询钱前台页面在员工修改页面需要的数据
	 */
	@RequestMapping(value="/basicdata",method=RequestMethod.GET)
	  public ResultObject<Object> basicdata(){
	    
		return productService.getBaseData();
	  }
	
	/*
	 * 删除工序
	 */
	@RequestMapping(value="/deletePre",method=RequestMethod.GET)
	public ResultObject<ProductPreProcess> deletePro(long id){
		return productService.deletePre(id);
	}

	/*
	新建工序
	 */
	@RequestMapping(value="/addProcess",method=RequestMethod.GET)
	public ResultObject<ProductPreProcess> addProcess(long productId ,String stepIds){
		return productService.addProcess(productId,stepIds);
	}

}
