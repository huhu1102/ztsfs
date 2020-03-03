/**  
* 
*/  
 
package com.zt.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zt.model.ResultObject;
import com.zt.po.RowMaterialPurchasePlanDetail;
import com.zt.service.RowMaterialPurchasePlanDetailService;

/**
 * @author whl
 * @date 2019年6月26日 
 */
@RestController
@RequestMapping(value="/rowMaterialBuyPlanDetail")
public class RowMaterialPurchasePlanDetailController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private RowMaterialPurchasePlanDetailService  planDetailService;  
	
	@RequestMapping(value="/buyPlanDetail",method=RequestMethod.GET)
	public ResultObject<RowMaterialPurchasePlanDetail> getCurrentDetail(Long  materailId){
		return planDetailService.getCurrentDetail(materailId);
	}
	
}
