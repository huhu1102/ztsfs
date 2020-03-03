package com.zt.controller;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultPage;
import com.zt.po.ShippingRequestDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zt.model.ResultObject;
import com.zt.po.ProductManageDetails;
import com.zt.service.ProductManageDetailsService;

import javax.xml.transform.Result;
import java.util.Date;

/**
 * @author wl
 * @date 2019年5月17日 
 */
@RestController
@RequestMapping("/pmDetails")
public class ProductManageDetailsController {
	@Autowired
	ProductManageDetailsService productManageDetailsService;
	/*
	分页查询
	 */
	@RequestMapping(value = "/findbypage",method = RequestMethod.GET)
	public ResultPage<ProductManageDetails> findSearch(int page, int size) throws BusinessRuntimeException{
		return productManageDetailsService.findSearch(page, size);
	}
	//根据生产管理Id查询生产进度Id
	@RequestMapping(value = "/findByMangeId",method = RequestMethod.GET)
	public ResultPage<ProductManageDetails> findByMangeId(long manageId, int page, int size, String start,String end ) throws BusinessRuntimeException{
		return productManageDetailsService.findByMangeId(manageId,page,size,start,end);
	}

	/*
	查询对应的生产管理下的详情(每一步工序完成的数量)
	 */
	@RequestMapping(value = "/findend",method = RequestMethod.GET)
	public ResultObject<ProductManageDetails> findEnd(long productManageId){
		return productManageDetailsService.findEnd(productManageId);
	}

	/*
	新建
	 */
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	public ResultObject<ProductManageDetails> add(long manageId,String manageDetails)throws BusinessRuntimeException{
		return productManageDetailsService.add(manageId,manageDetails);
	}
	/*
	新建(xin)
	 */
	@RequestMapping(value = "/addFinish",method = RequestMethod.POST)
	public ResultObject<ProductManageDetails> addFinish(long manageId,ProductManageDetails manageDetails)throws BusinessRuntimeException{
		return productManageDetailsService.addFinish(manageId,manageDetails);
	}
	/*
	修改(xin)
	 */
	@RequestMapping(value = "/updateFinish",method = RequestMethod.POST)
	public ResultObject<ProductManageDetails> updateFinish(long manageId,ProductManageDetails manageDetails)throws BusinessRuntimeException{
		return productManageDetailsService.updateFinish(manageId,manageDetails);
	}
	/*
	修改
	 */
	@RequestMapping(value = "/update",method = RequestMethod.POST)
	public ResultObject<ProductManageDetails> update(long manageId,long manageDetailId,float quantity,float junkedNo,String plant,String notes)throws BusinessRuntimeException{
		return productManageDetailsService.update(manageId,manageDetailId,quantity,junkedNo,plant,notes);
	}

    /**
     *  过程撤销
     * @param manageDetailsId
     * @return
     * @throws BusinessRuntimeException
     */
    @RequestMapping(value = "/deleteFinish",method = RequestMethod.GET)
    public ResultObject<ProductManageDetails> deleteFinish(long manageDetailsId)throws BusinessRuntimeException{
        return productManageDetailsService.deleteFinish(manageDetailsId);
    }
}
