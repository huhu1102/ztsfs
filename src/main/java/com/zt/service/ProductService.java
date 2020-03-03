package com.zt.service;


import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.Product;
import com.zt.po.ProductPreProcess;
import com.zt.vo.ProductModel;




/**
 * @author wl
 * @date 2019年4月18日 
 * 产品的业务接口
 */
public interface ProductService {
	
	/*
	 *  分页模糊条件查询
     */
	ResultPage<Product> findSearch(String queryName,String keycolorName,String keyUse, String keyspeName,int page, int size) throws BusinessRuntimeException;
    /*
     * 产品的修改
     */
    ResultObject<Product> saveProduct(ProductModel product)throws BusinessRuntimeException;
    /*
     * 产品的新增
     */
    ResultObject<Product> addProduct(ProductModel productModel)throws Exception;
    /*
     * 删除
     */
   	ResultObject<Product> deletPro(long id)throws BusinessRuntimeException;
	/**
	 * @return
	 */
	ResultObject<Object> getBaseData()throws BusinessRuntimeException;
	/*
	 * 删除工序
	 */
	ResultObject<ProductPreProcess> deletePre(long id)throws BusinessRuntimeException;
	/*
	增加工序
	 */
    ResultObject<ProductPreProcess> addProcess(long productId, String stepIds)throws BusinessRuntimeException;

    ResultPage<Object> findAll(int page ,int size)throws BusinessRuntimeException;
}
