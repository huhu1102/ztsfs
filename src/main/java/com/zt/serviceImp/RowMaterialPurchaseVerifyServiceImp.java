package com.zt.serviceImp;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zt.common.RowMaterialCreateRecieving;
import com.zt.common.UsersUtils;
import com.zt.dao.EmployeeDao;
import com.zt.dao.MiddleProductDao;
import com.zt.dao.RowMaterialPurchasePlanDetailDao;
import com.zt.dao.RowMaterialPurchaseVerifyDao;
import com.zt.dao.RowMaterialDao;
import com.zt.dao.RowMaterialInRecievingDao;
import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.RowMaterialPurchaseVerify;
import com.zt.service.RowMaterialPurchaseVerifyService;
import com.zt.po.RowMaterial;
import com.zt.po.RowMaterialPurchasePlanDetail;

/**
 * @author wl
 * @date 2019年6月20日 
 */
@Service("rowMaterialPurchaseVerifyDetailService")
public class RowMaterialPurchaseVerifyServiceImp implements RowMaterialPurchaseVerifyService{

	@Autowired
	RowMaterialPurchaseVerifyDao purVerifyDao;
	@Autowired
	RowMaterialPurchasePlanDetailDao purPlanDetailDao;
	@Autowired
	RowMaterialDao rowMaterialDao;
	@Autowired
	MiddleProductDao middleProductDao;
	@Autowired
	EmployeeDao empDao;
	@Autowired
	RowMaterialInRecievingDao rowMaterialRecDao;
	@Autowired
	RowMaterialCreateRecieving createRecieving;
	/*
	 * 查找所有
	 */
	@Override
	public ResultPage<RowMaterialPurchaseVerify> findVerifyDetail(String identifier, int page, int size)
			throws BusinessRuntimeException {
		ResultPage<RowMaterialPurchaseVerify> ro=new ResultPage<>();
		Pageable pageable = PageRequest.of(page-1,size);
		Page<RowMaterialPurchaseVerify> pagelist=purVerifyDao.findbypage(identifier, pageable);
		 if (pagelist!=null) {
	        	 ro.setData(pagelist.getContent());
	    	     ro.setTotal(pagelist.getTotalElements());
	    	     ro.setTotalPages(pagelist.getTotalPages());
	    	     ro.setSuccess(true);
			} else {
				ro.setSuccess(false);
				throw  new BusinessRuntimeException(ResultCode.UNKNOWN_ERROR);
			}
		
		return ro;
	}
	/*
	 * 新建
	 */
	@Override
	public ResultObject<RowMaterialPurchaseVerify> addNew(long purchasePlanDetailId,Integer confirmStatus,float  quantity,String notes)
			throws BusinessRuntimeException {
		ResultObject<RowMaterialPurchaseVerify> ro = new ResultObject<>();
		RowMaterialPurchasePlanDetail planDetail = purPlanDetailDao.findById(purchasePlanDetailId);
		RowMaterial rowMaterial = null;
		if(planDetail.getRowMaterial()!=null) {
			rowMaterial = planDetail.getRowMaterial();
		}
		RowMaterialPurchaseVerify verifyDetail = new RowMaterialPurchaseVerify();
		verifyDetail.setPurchasePlanDetail(planDetail);
		verifyDetail.setSerialNumber(planDetail.getPlan().getSerialNumber());
		verifyDetail.setPlanDate(planDetail.getPlan().getCreateDate());
		verifyDetail.setName(planDetail.getName());
		verifyDetail.setSpecifications(planDetail.getSpecifications());
		verifyDetail.setQuantity(planDetail.getQuantity());
		verifyDetail.setPracticalQuantity(quantity);
		verifyDetail.setUnitId(planDetail.getUnitId());
		verifyDetail.setNotes(notes);
		verifyDetail.setEnabled(true);
		verifyDetail.setCreateDate(new Date());
		verifyDetail.setVerifyStatus(1);
		//获取当前登录的用户的员工ID
		long empId = UsersUtils.getCurrentHr().getEmpId();
		verifyDetail.setEmployeeId(empId);
		verifyDetail.setEmployee(empDao.findById(empId));
		//前台传过来已确认的
		if(confirmStatus==2) {
			int status = purPlanDetailDao.updateStatus(purchasePlanDetailId);
			if(status>0) {
				ro.setSuccess(true);
				ro.setMsg("确认成功");
			}else {
				ro.setSuccess(false);
				ro.setMsg("确认失败 ");
			}
		}
			//原料
			rowMaterial.setQuantity(quantity+rowMaterial.getQuantity());
			rowMaterial = rowMaterialDao.saveAndFlush(rowMaterial);
			if(rowMaterial!=null) {
				ro.setSuccess(true);
				ro.setMsg("修改库存成功");
			}else {
				ro.setSuccess(false);
				ro.setMsg("修改库存失败");
				throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
			}
			
		verifyDetail = purVerifyDao.save(verifyDetail);
		if(verifyDetail!=null) {
			ro.setSuccess(true);
			ro.setMsg("操作成功");
			//判断采购详情中是否还有该物品的采购计划
			int notConfirmByMid = purPlanDetailDao.getNotConfirmByMid(planDetail.getMaterialId());
			if(notConfirmByMid==0) {
				rowMaterial.setBuyStatus(false);
				rowMaterial = rowMaterialDao.saveAndFlush(rowMaterial);
				if(rowMaterial!=null) {
					ro.setSuccess(true);
					ro.setMsg("操作成功");
				}else {
					ro.setSuccess(false);
					ro.setMsg("操作失败");
					throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
				}
			}
		}else {
			ro.setSuccess(false);
			ro.setMsg("操作失败");
			throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
		}
		return ro;
	}
	/*
	 * 撤回
	 */
	@Override
	@Transactional
	public ResultObject<RowMaterialPurchaseVerify> withdraw(long id) throws BusinessRuntimeException {
		ResultObject<RowMaterialPurchaseVerify> ro = new ResultObject<>();
		
		RowMaterialPurchaseVerify verifyDetail = purVerifyDao.findById(id);
		//确认单设置为已撤销
		verifyDetail.setVerifyStatus(2);

		verifyDetail = purVerifyDao.saveAndFlush(verifyDetail);
		if(verifyDetail!=null){
			ro.setSuccess(true);
			ro.setMsg("修改成功");
		}else{
			ro.setSuccess(false);
			ro.setMsg("修改失败");
			throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
		}
		//获取确认单中的数量
		float quantity = verifyDetail.getPracticalQuantity();
		
		//修改采购计划详情中的状态   确认2改成未确认1
		RowMaterialPurchasePlanDetail purchasePlanDetail = verifyDetail.getPurchasePlanDetail();
		purchasePlanDetail.setConfirmStatus(1);
		purchasePlanDetail = purPlanDetailDao.saveAndFlush(purchasePlanDetail);
		if(purchasePlanDetail!=null) {
			ro.setSuccess(true);
			ro.setMsg("修改成功");
		}else {
			ro.setSuccess(false);
			ro.setMsg("修改失败");
			throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
		}
		
		//修改原料库中的库存和状态
		RowMaterial rowMaterial = purchasePlanDetail.getRowMaterial();
		if(rowMaterial!=null) {
			//修改库存
			if(rowMaterial.getQuantity()-quantity>=0){
				rowMaterial.setQuantity(rowMaterial.getQuantity()-quantity);
				rowMaterial.setBuyStatus(true);
				rowMaterial = rowMaterialDao.saveAndFlush(rowMaterial);
				if(rowMaterial!=null) {
					ro.setSuccess(true);
					ro.setMsg("操作成功");
				}else {
					ro.setSuccess(false);
					ro.setMsg("操作失败");
					throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
				}
			}else{
				ro.setMsg("数量不对");
				ro.setSuccess(false);
				throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
			}
		}else {
			ro.setMsg("撤回失败");
			ro.setSuccess(false);
			throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
		}		
		return ro;
	}
}
