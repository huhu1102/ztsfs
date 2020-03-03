package com.zt.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.zt.dao.MiddleProductDao;
import com.zt.dao.RowMaterialDao;
import com.zt.dao.ShippingDao;
import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.RowMaterial;
import com.zt.po.Shipping;
import com.zt.service.ShippingService;

/**
 * @author wl
 * @date 2019年5月30日 
 */
//出库记录单
@Service("shippingService")
public class ShippingServiceImp implements ShippingService{
	@Autowired
	ShippingDao shippingDao;
	
	@Autowired
	RowMaterialDao  rowmaterial;
	@Autowired
	MiddleProductDao  middleproduct;
	/*
	 * 分页模糊查询
	 */
	@Override
	public ResultPage<Shipping> findSearch(String queryName, int page, int size) throws BusinessRuntimeException {
		ResultPage<Shipping> ro=new ResultPage<Shipping>();
		Pageable pageable = PageRequest.of(page-1,size);
		Page<Shipping> pages = shippingDao.findSearch(queryName,pageable);
		
		 if (pages!=null) {
	        	int totals=(int) pages.getTotalElements();
	        	 ro.setData(pages.getContent());
	    	     ro.setTotal(totals);
	    	     ro.setTotalPages(pages.getTotalPages());
	    	     ro.setSuccess(true);
			} else {
				ro.setSuccess(false);
				throw new BusinessRuntimeException(ResultCode.UNKNOWN_ERROR);
			}
		return ro;
	}
	/*
	 * 删除
	 */
	@Override
	public ResultObject<Shipping> deletSh(long id) throws BusinessRuntimeException {
		ResultObject<Shipping> ro=new ResultObject<>(); 
		Shipping sh=shippingDao.findById(id);
		 if (sh!=null) {
			 sh.setEnable(false);
			 shippingDao.saveAndFlush(sh);
			ro.setSuccess(true);
			ro.setMsg("操作成功");
		}else {
			ro.setSuccess(false);
			ro.setMsg("操作失败");
			throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
		}  
		return ro;
	}
	@Override
	public ResultObject<Shipping> updateSh(Shipping shipping) throws BusinessRuntimeException {
		ResultObject<Shipping> ro=new ResultObject<>(); 
		Shipping sh=shippingDao.saveAndFlush(shipping);
		//@return get chanpin id   get name   get num 
		//long id=shipping.getId();
		//RowMaterial rm=rowmaterial.findById(id);
		//String matername=rm.getMaterialName(); //获得名字
		//Float materialq=rm.getMaterialQuantity();//获得数量
		if(sh!=null) {
			sh.setEnable(false);
			 shippingDao.saveAndFlush(sh);
			ro.setSuccess(true);
			ro.setMsg("操作成功");
			
		}else {
			ro.setSuccess(false);
			ro.setMsg("操作失败");
			throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
			
		}
		return ro;
	}
	

}
