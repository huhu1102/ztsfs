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
import com.zt.po.MidProductPurchasePlanDetail;
import com.zt.po.RowMaterialPurchasePlanDetail;
import com.zt.service.MidProductPurchasePlanDetailService;
import com.zt.service.RowMaterialPurchasePlanDetailService;

/**
 * @author whl
 * @date 2019年6月26日 
 */
@RestController
@RequestMapping(value="/midBuyPlanDetail")
public class MidProductPurchasePlanDetailController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private MidProductPurchasePlanDetailService  planDetailService;  
	
	@RequestMapping(value="/buyPlanDetail",method=RequestMethod.GET)
	public ResultObject<MidProductPurchasePlanDetail> getCurrentDetail(Long  materailId){
		return planDetailService.getCurrentDetail(materailId);
	}
	
}
