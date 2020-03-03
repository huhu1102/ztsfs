package com.zt.serviceImp;


import com.zt.dao.RowMaterialDao;
import com.zt.dao.RowMaterialInRecievingDao;
import com.zt.dao.RowMaterialPurchasePlanDao;
import com.zt.po.RowMaterial;
import com.zt.po.RowMaterialInRecieving;
import com.zt.service.RowMaterialInRecievingService;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.zt.common.UsersUtils;
import com.zt.dao.EmployeeDao;
import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;

/**
 * @author wl
 * @date 2019年5月30日 
 */
//入库记录单
@Service("inRecievingService")
public class RowMaterialInRecievingServiceImp implements RowMaterialInRecievingService {
	@Autowired
	RowMaterialPurchasePlanDao pcpd;
	@Autowired
	RowMaterialInRecievingDao inRecievingDao;
	@Autowired
	RowMaterialDao rowmaterial;
	@Autowired
	EmployeeDao employeeDao;
	/*
	 * 分页模糊查询
	 */
	@Override
	public ResultPage<RowMaterialInRecieving> findSearch(String queryName, int page, int size) throws BusinessRuntimeException {
		ResultPage<RowMaterialInRecieving> ro=new ResultPage<>();
		Pageable pageable = PageRequest.of(page-1,size);
		Page<RowMaterialInRecieving> pages = inRecievingDao.findSearch(queryName,pageable);
		
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
	 * 撤回
	 */
	@Override
	public ResultObject<RowMaterialInRecieving> withdraw(long id) {
		ResultObject<RowMaterialInRecieving> ro = new ResultObject<>();
		RowMaterialInRecieving inRec = inRecievingDao.findById(id);
		//将撤销状态改成撤销
		inRec.setVerifyStatus(2);
		inRec = inRecievingDao.saveAndFlush(inRec);
		float quantity = inRec.getQuantity();
		
		RowMaterial material = inRec.getMaterail();
		
		if(material.getQuantity()-quantity>=0) {
			material.setQuantity(material.getQuantity()-quantity);
			material = rowmaterial.saveAndFlush(material);
			if(inRec!=null&&material!=null) {
				ro.setSuccess(true);
				ro.setMsg("撤销成功");
				
			}else {
				ro.setSuccess(false);
				ro.setMsg("撤销失败");
				throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
			}
		}
		return ro;
	}
	/*
	 * 新增
	 */
	@Override
	public ResultObject<RowMaterialInRecieving> addNew(long id, float quaity, String notes)
			throws BusinessRuntimeException {
		ResultObject<RowMaterialInRecieving> ro = new ResultObject<>();
		RowMaterialInRecieving rowMaterialInRecieving = new RowMaterialInRecieving();
		RowMaterial rowMaterial = rowmaterial.findById(id);
		rowMaterialInRecieving.setName(rowMaterial.getMaterialName());
		rowMaterialInRecieving.setUnitId(rowMaterial.getUnitId());
		rowMaterialInRecieving.setUnitName(rowMaterial.getUnitName());
		rowMaterialInRecieving.setQuantity(quaity);
		rowMaterialInRecieving.setMaterailId(id);
		rowMaterialInRecieving.setMaterail(rowMaterial);
		rowMaterialInRecieving.setVerifyStatus(1);
		rowMaterialInRecieving.setEnable(true);
		rowMaterialInRecieving.setNotes(notes);
		
		long empId = UsersUtils.getCurrentHr().getEmpId();
		rowMaterialInRecieving.setOperatorId(empId);
		rowMaterialInRecieving.setOperatorName(employeeDao.findById(empId).getName());
		rowMaterialInRecieving.setOperator(employeeDao.findById(empId));
		rowMaterialInRecieving.setCreateDate(new Date());
		rowMaterialInRecieving = inRecievingDao.save(rowMaterialInRecieving);
		if(rowMaterialInRecieving!=null) {
			ro.setSuccess(true);
			ro.setMsg("新增成功");
			//获取之前的原料库中的库存
			
			//数量的改变
			rowMaterial.setQuantity(quaity+rowMaterial.getQuantity());
			rowMaterial = rowmaterial.saveAndFlush(rowMaterial);
			if(rowMaterial!=null) {
				ro.setSuccess(true);
				ro.setMsg("库存修改成功");
			}else {
				ro.setSuccess(false);
				ro.setMsg("库存修改失败");
				throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
			}
			
		}else {
			ro.setSuccess(false);
			ro.setMsg("新增失败");
			throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
		}
		
		return ro;
	}
	
	
	
}
