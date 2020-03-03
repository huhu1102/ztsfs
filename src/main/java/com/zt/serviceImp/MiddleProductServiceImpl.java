/**
 * 
 */
package com.zt.serviceImp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.zt.common.MidProductCreateRecieving;
import com.zt.common.PageUtil;
import com.zt.dao.ClientDao;
import com.zt.dao.MidProductPurchasePlanDetailDao;
import com.zt.dao.MiddleProductDao;
import com.zt.dao.ProductDao;
import com.zt.dao.RowMaterialPurchasePlanDetailDao;
import com.zt.dao.SysUnitDao;
import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.Client;
import com.zt.po.MidProductInRecieving;
import com.zt.po.MidProductPurchasePlanDetail;
import com.zt.po.MiddleProduct;
import com.zt.po.Product;
import com.zt.po.SysUnit;
import com.zt.service.MiddleProductService;

/**
 * @author yh
 * @date 2019年5月9日
 */
@Service("middleProductService")
@CacheConfig(cacheNames = "zt_caches_middleproduct")
public class MiddleProductServiceImpl implements MiddleProductService{
	@Autowired
	private MiddleProductDao mpdtd;
	@Autowired
	private SysUnitDao  unitDao;
	@Autowired
	private ProductDao  productDao;
	@Autowired
	private ClientDao  clientDao;
	@Autowired
	private MidProductPurchasePlanDetailDao  planDetailDao;
	@Autowired
	private MidProductCreateRecieving midProductCreateRec;
	@Override
//	@Cacheable(key="'midlpt_'+#queryName+#page+#size")	
	public ResultPage<MiddleProduct> findMiddleProduct(String queryName, int page, int size)
			throws BusinessRuntimeException {
		ResultPage<MiddleProduct> ro=new ResultPage<MiddleProduct>();
		Pageable pageable = PageRequest.of(page-1,size);
		Page<MiddleProduct> pagelist=mpdtd.findbypage(queryName, pageable);
		int count=mpdtd.countAllData(queryName);
		 if (pagelist!=null) {

	        	 ro.setData(pagelist.getContent());
	    	     ro.setTotal(count);
	    	     ro.setTotalPages(pagelist.getTotalElements());
	    	     ro.setSuccess(true);
			} else {
				ro.setSuccess(false);
				throw  new BusinessRuntimeException(ResultCode.UNKNOWN_ERROR);
			}
		return ro;
	}
	/*
	 * 新增
	 */
	@Override
	@CacheEvict(allEntries=true)//清除缓存
	public ResultObject<MiddleProduct> addMiddleProduct(MiddleProduct midProduct) throws BusinessRuntimeException {
		ResultObject<MiddleProduct> ro=new ResultObject<>();
		//半成品所处状态
		//midProduct.getMiddleProductstation();
		if(Long.valueOf(midProduct.getId())==null||midProduct.getId()==0) {
			midProduct.setCreateDate(new Date());
			//半成品的入库
		}				
		midProduct.setEnabled(true);
		if (midProduct.getUnitId()!=0) {
			 Optional<SysUnit> unit=unitDao.findById(midProduct.getUnitId());
			 if (unit.isPresent()) {
				 SysUnit  sunit=unit.get();
				 midProduct.setUnit(sunit);
				 midProduct.setUnitId(sunit.getId());
				 midProduct.setUnitName(sunit.getName());
			}
		}
		StringBuilder sb = new StringBuilder();
		sb.append(midProduct.getName())
		.append(midProduct.getStandard());
		
		String sa = sb.toString();
		
		List<MiddleProduct> findMP = mpdtd.findMiddleProduct(sa);
		if(findMP.size()>0) {
			ro.setMsg("该物料已存在");
			ro.setSuccess(false);
		}else {
			ro.setMsg("该物料可用");
			ro.setSuccess(true);
			MiddleProduct ars=mpdtd.save(midProduct);
			if(ars!=null) {
				ro.setSuccess(true);
				ro.setMsg("操作成功");
				//添加半成品入库记录
//				int result = midProductCreateRec.createMiddleProductInRecieving(midProduct, midProduct.getProductNo());
//				if(result>0) {
//					ro.setSuccess(true);
//					ro.setMsg("入库记录添加成功");
//				}else {
//					ro.setSuccess(false);
//					ro.setMsg("入库记录添加失败");
//					throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
//				}
				
			}else {
				ro.setSuccess(false);
				ro.setMsg("操作失败");
				throw   new BusinessRuntimeException(ResultCode.OPER_FAILED);
			}
		}
		
		return ro;
	}
	/*
	 * 修改方法
	 */
	@Override
	public ResultObject<MiddleProduct> updateMiddleProduct(MiddleProduct midProduct) throws BusinessRuntimeException {
		ResultObject<MiddleProduct> ro=new ResultObject<>();
		//半成品所处状态
		//midProduct.getMiddleProductstation();
		if(Long.valueOf(midProduct.getId())==null||midProduct.getId()==0) {
			midProduct.setCreateDate(new Date());
			//半成品的入库
		}				
		midProduct.setEnabled(true);
		if (midProduct.getUnitId()!=0) {
			Optional<SysUnit> unit=unitDao.findById(midProduct.getUnitId());
			if (unit.isPresent()) {
				SysUnit  sunit=unit.get();
				midProduct.setUnit(sunit);
				midProduct.setUnitId(sunit.getId());
				midProduct.setUnitName(sunit.getName());
			}
		}
		MiddleProduct ars=mpdtd.saveAndFlush(midProduct);
		 if(ars!=null) {
			   ro.setSuccess(true);
				ro.setMsg("操作成功");
		   }else {
			   ro.setSuccess(false);
			   ro.setMsg("操作失败");
			throw   new BusinessRuntimeException(ResultCode.OPER_FAILED);
		   }
		
		return ro;
	}
	
	@Override
	@CacheEvict(allEntries=true)//清除缓存
	public ResultObject<MiddleProduct> deletMiddleProduct(long id) throws BusinessRuntimeException {
		ResultObject<MiddleProduct> ro=new ResultObject<>(); 
		MiddleProduct acris=mpdtd.findById(id);
		 if (acris!=null) {
			 acris.setEnabled(false);
			 mpdtd.saveAndFlush(acris);
			ro.setSuccess(true);
			ro.setMsg("操作成功");
		}else {
			ro.setSuccess(false);
			ro.setMsg("操作失败");
		throw	new BusinessRuntimeException(ResultCode.OPER_FAILED);
		}  
		return ro;
	}

	@Override
	public ResultObject<MiddleProduct> basedata() throws BusinessRuntimeException {
		ResultObject<MiddleProduct> ro=new ResultObject<>();
		Map<String, Object> map=new HashMap<String, Object>();
		
		Set<SysUnit> units=  unitDao.findAllUnits();
		Set<Product> products = productDao.findAlldatas();
		Set<Client> clients = clientDao.findAllParent();
		if (products==null) {
			ro.setSuccess(false);
			throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
		}else {
			map.put("units", units);
			map.put("products", products);
			map.put("clients", clients);
			
			ro.setRoot(map);
			ro.setSuccess(true);
		}
		
		return ro;
	}
	@Override
	public ResultObject<MiddleProduct> getCurrentPlan() throws BusinessRuntimeException {
		ResultObject<MiddleProduct> ro=new ResultObject<>();
		Map<String, Object> map=new HashMap<String, Object>();
		Set<MidProductPurchasePlanDetail> units=  planDetailDao.getCurrentDetail(1L);
		if (units==null) {
			ro.setSuccess(false);
			throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
		}else {
			map.put("units", units);
			
			ro.setRoot(map);
			ro.setSuccess(true);
		}
		return ro;
	}
	

}
