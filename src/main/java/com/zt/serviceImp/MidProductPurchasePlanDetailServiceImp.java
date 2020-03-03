/**
 * 
 */
package com.zt.serviceImp;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zt.common.MessageUtil;
import com.zt.dao.MidProductPurchasePlanDetailDao;
import com.zt.dao.MsgContentDao;
import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.po.MidProductPurchasePlanDetail;
import com.zt.service.MidProductPurchasePlanDetailService;


/**
 * @author whl
 * @date 2019年6月26日
 */
@Service("midProductPurchasePlanDetialService")
//@CacheConfig(cacheNames = "zt_caches_purchaseplan")
public class MidProductPurchasePlanDetailServiceImp implements MidProductPurchasePlanDetailService{
	@Autowired
	private MidProductPurchasePlanDetailDao planDetailDao;
	@Autowired
	MessageUtil msgutil;
	@Autowired
	MsgContentDao  msgctd;
	@Override
	public ResultObject<MidProductPurchasePlanDetail> getCurrentDetail(Long materailId)
			throws BusinessRuntimeException {
		ResultObject<MidProductPurchasePlanDetail> ro=new ResultObject<>();
		Map<String, Object> map=new HashMap<String, Object>();
		Set<Object[]> list=planDetailDao.findCurretDetail(materailId);
		if (null!=list) {
			map.put("midproductlist", list);
			ro.setRoot(map);
		    ro.setSuccess(true);			
		}else {
			ro.setSuccess(false);
			throw new   BusinessRuntimeException(ResultCode.UNKNOWN_ERROR);
		}
		return ro;
	}
    
}
