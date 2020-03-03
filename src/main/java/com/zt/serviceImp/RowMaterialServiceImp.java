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

import com.zt.common.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.zt.dao.ClientDao;
import com.zt.dao.EmployeeDao;
import com.zt.dao.RowMaterialDao;
//import com.zt.dao.RowMaterialRecievingDao;
import com.zt.dao.SysUnitDao;
import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.Client;
import com.zt.po.Department;
import com.zt.po.Position;
import com.zt.po.RowMaterial;
import com.zt.po.RowMaterialOutRecieving;
//import com.zt.po.RowMaterialRecieving;
import com.zt.po.SysUnit;
import com.zt.service.RowMaterialService;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author yh
 * @date 2019年4月28日
 */
@Service("RowMaterialService")
//@CacheConfig(cacheNames = "zt_caches_RowMaterial")
public class RowMaterialServiceImp implements RowMaterialService{
	@Autowired
	private RowMaterialDao rowMaterialDao;
	@Autowired
	private SysUnitDao unitDao;
	@Autowired
	private ClientDao clientDao;
	
	@Autowired
	private EmployeeDao empDao;
	
	@Autowired
	RowMaterialCreateRecieving rowMaterCreateRec;

	@Autowired
	Message message;
	@Autowired
	MessageUtil messageUtil;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
//	@Cacheable(key="'RowMaterial_'+#queryName+#page+#size")	
	public ResultPage<RowMaterial> findRowMaterial(String queryName, int page, int size)
			throws BusinessRuntimeException {
		ResultPage<RowMaterial> ro=new ResultPage<RowMaterial>();
		Pageable pageable = PageRequest.of(page-1,size);
//		Page<VehicleInfo> pages =vid.findSearch(queryName, pageable);
		Page<RowMaterial> pages=rowMaterialDao.findbypage(queryName,pageable);
		int count=rowMaterialDao.countAllData(queryName);
		 if (pages!=null) {
//	        	int totals=(int) pages.getTotalElements();
	        	 ro.setData(pages.getContent());
	    	     ro.setTotal(pages.getTotalElements());
	    	     ro.setTotalPages(pages.getTotalPages());
	    	     ro.setSuccess(true);
			} else {
				ro.setSuccess(false);
				throw  new BusinessRuntimeException(ResultCode.UNKNOWN_ERROR);
			}
		
		return ro;
		
	}
	//增加或修改原材料基础信息
		
//	@CacheEvict(allEntries=true)//清除缓存
	@Override
	public ResultObject<RowMaterial> addRowMaterial(RowMaterial material) throws BusinessRuntimeException {
		ResultObject<RowMaterial> ro=new ResultObject<>();
		 if(material!=null&&Long.valueOf(material.getId())!=null ) {
			 	material.setCreateDate(new Date());
		 }
				String unitName=unitDao.findUtilsName(material.getUnitId());
				Optional<SysUnit> unit = unitDao.findById(material.getUnitId());
				SysUnit u=null;
				if (unit.isPresent()) {
					u=unit.get();
					material.setUnit(u);
				}
				material.setUnitName(unitName);
				material.setEnabled(true);
				
				StringBuilder sb = new StringBuilder();
				sb.append(material.getMaterialName())
				.append(material.getSpecs());
				
				String sa = sb.toString();
				
				 List<RowMaterial> findRM = rowMaterialDao.findRowMaterial(sa);
				if(findRM.size()>0) {
					ro.setMsg("该名字已存在");
					ro.setSuccess(false);
				}else {
					ro.setMsg("该名字可用");
					ro.setSuccess(true);
					material=rowMaterialDao.save(material);
					if (material!=null) {
						ro.setSuccess(true);
						ro.setMsg("操作成功！");;
					}else {
						ro.setSuccess(false);
						ro.setMsg("操作失敗");
						throw new  BusinessRuntimeException(ResultCode.OPER_FAILED);
						
					}
				}
				
		return ro;
	}
	
	/*
	 * 修改
	 */
	@Override
	public ResultObject<RowMaterial> updateRowMaterial(RowMaterial material) throws BusinessRuntimeException {
		ResultObject<RowMaterial> ro=new ResultObject<>();
		 if(material!=null&&Long.valueOf(material.getId())!=null ) {
			 	material.setCreateDate(new Date());
		 }
			String unitName=unitDao.findUtilsName(material.getUnitId());
			Optional<SysUnit> unit = unitDao.findById(material.getUnitId());
			SysUnit u=null;
			if (unit.isPresent()) {
				u=unit.get();
				material.setUnit(u);
			}
			material.setUnitName(unitName);
			material.setEnabled(true);
			material=rowMaterialDao.saveAndFlush(material);
			if (material!=null) {
				ro.setSuccess(true);
				ro.setMsg("修改成功");;
			}else {
				ro.setSuccess(false);
				ro.setMsg("操作失敗");
				throw new  BusinessRuntimeException(ResultCode.OPER_FAILED);

			}
		return ro;
	}

	@Override
//	@CacheEvict(allEntries=true)//清除缓存
	public ResultObject<RowMaterial> deletRowMaterial(long id) throws BusinessRuntimeException {
		ResultObject<RowMaterial> ro=new ResultObject<>(); 
		RowMaterial acris=rowMaterialDao.findById(id);
		 if (acris!=null) {
			 acris.setEnabled(false);
			 rowMaterialDao.saveAndFlush(acris);
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
	public ResultObject<RowMaterial> basedata() throws BusinessRuntimeException {
		ResultObject<RowMaterial> ro=new ResultObject<>();
		Map<String, Object> map=new HashMap<String, Object>();
		
		Set<SysUnit> units=  unitDao.findAllUnits();
		Set<Client> clients = clientDao.findAllParent();
		if (clients==null) {
			ro.setSuccess(false);
			throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
		}else {
			map.put("units", units);
			map.put("clients", clients);
			
			ro.setRoot(map);
			ro.setSuccess(true);
		}
		
		return ro;
	}

	@Override
//	@CacheEvict(allEntries=true)//清除缓存
	@Transactional
	public ResultObject<RowMaterial> receive(long id, float quantity) throws BusinessRuntimeException {
		ResultObject<RowMaterial> ro=new ResultObject<>();
		RowMaterial  material=rowMaterialDao.findById(id);
		   if (material!=null) {
			   float v = material.getQuantity() - quantity;
			   int quan = rowMaterialDao.updateQuan(v, id);
			   if (quan>0) {
				   ro.setSuccess(true);
				   ro.setMsg("出库成功");
				   if(v<=material.getStoreStatue()){
				   		StringBuilder sb = new StringBuilder();
				   		sb.append("原材料:").append(material.getMaterialName()).append(",库存不足,剩余库存数量为:").append(v).append("请及时补充库存");
				   		int mess = messageUtil.sendMsg("原材料库存不足", sb.toString(), "RowMaterial", id, message.UserIds());
				   		if(mess>0){
				   			ro.setSuccess(true);
							logger.info("消息发送成功");
						}else{
				   			ro.setSuccess(false);
				   			logger.info("消息发送失败");
						}
				   }
				   //增加出库记录
				   int result = rowMaterCreateRec.createRowMaterialOutRecieving(material, quantity);
				   if(result>0) {
					   ro.setSuccess(true);
					   ro.setMsg("增加出库记录成功");
				   }else {
					   ro.setSuccess(false);
					   ro.setMsg("增加出库记录失败");
					   throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
				   }
			}else {
				 ro.setSuccess(false);
				 ro.setMsg("出库失败");
			}
		}
		
		return ro;
	}



}
