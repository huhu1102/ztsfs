package com.zt.serviceImp;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zt.common.MidProductCreateRecieving;
import com.zt.common.UsersUtils;
import com.zt.dao.EmployeeDao;
import com.zt.dao.MidProductInRecievingDao;
import com.zt.dao.MidProductPurchasePlanDetailDao;
import com.zt.dao.MidProductPurchaseVerifyDao;
import com.zt.dao.MiddleProductDao;
import com.zt.dao.RowMaterialPurchasePlanDetailDao;
import com.zt.dao.RowMaterialPurchaseVerifyDao;
import com.zt.dao.RowMaterialDao;
import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.MidProductInRecieving;
import com.zt.po.MidProductPurchasePlanDetail;
import com.zt.po.MidProductPurchaseVerify;
import com.zt.po.MiddleProduct;
import com.zt.po.RowMaterialPurchaseVerify;
import com.zt.service.MidProductPurchaseVerifyService;
import com.zt.service.RowMaterialPurchaseVerifyService;
import com.zt.po.RowMaterial;
import com.zt.po.RowMaterialPurchasePlanDetail;

/**
 * @author wl
 * @date 2019年6月20日 
 */
@Service("midProductPurchaseVerifyDetailService")
public class MidProductPurchaseVerifyServiceImp implements MidProductPurchaseVerifyService{

	@Autowired
	MidProductPurchaseVerifyDao purVerifyDao;
	@Autowired
	MidProductPurchasePlanDetailDao purPlanDetailDao;
	@Autowired
	MiddleProductDao middleProductDao;
	@Autowired
	EmployeeDao empDao;
	@Autowired
	MidProductCreateRecieving createRecieving;
	@Autowired
	MidProductInRecievingDao midInRecDao;
	/*
	 * 查找所有
	 */
	@Override
	public ResultPage<MidProductPurchaseVerify> findVerifyDetail(String identifier, int page, int size)
			throws BusinessRuntimeException {
		ResultPage<MidProductPurchaseVerify> ro=new ResultPage<>();
		Pageable pageable = PageRequest.of(page-1,size);
		Page<MidProductPurchaseVerify> pagelist=purVerifyDao.findbypage(identifier, pageable);
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
	public ResultObject<MidProductPurchaseVerify> addNew(long purchasePlanDetailId,Integer confirmStatus,float  quantity,String notes)
			throws BusinessRuntimeException {
		ResultObject<MidProductPurchaseVerify> ro = new ResultObject<>();
		MidProductPurchasePlanDetail planDetail = purPlanDetailDao.findById(purchasePlanDetailId);
		MiddleProduct middleProduct = null;
		if(planDetail.getMiddlePproduct()!=null) {
			middleProduct = planDetail.getMiddlePproduct();
		}
		MidProductPurchaseVerify verifyDetail = new MidProductPurchaseVerify();
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
		//半成品  修改库存
		middleProduct.setProductNo(quantity+middleProduct.getProductNo());
		middleProduct =middleProductDao.saveAndFlush(middleProduct);
		if(middleProduct!=null) {
			ro.setSuccess(true);
			ro.setMsg("库存修改成功");
		}else {
			ro.setSuccess(false);
			ro.setMsg("库存修改失败");
			throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
		}
		verifyDetail = purVerifyDao.save(verifyDetail);
		if(verifyDetail!=null) {
			ro.setSuccess(true);
			ro.setMsg("操作成功");
			//判断采购详情中是否还有该物品的采购计划
			int notConfirmByMid = purPlanDetailDao.getNotConfirmByMid(planDetail.getMaterialId());
			if(notConfirmByMid==0) {
				middleProduct.setBuyStatus(false);
				middleProductDao.saveAndFlush(middleProduct);
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
	public ResultObject<MidProductPurchaseVerify> withdraw(long id) throws BusinessRuntimeException {
		ResultObject<MidProductPurchaseVerify> ro = new ResultObject<>();
		int re=0;
		  //确认单设置为已撤销
		 re=   purVerifyDao.drow(id);
		 if (re>0) {
			 //修改计划详情状态为未确认
		  re = purPlanDetailDao.drow(id);
			   if(re>0) {
					//修改半成品库中的库存和状态
					float midQuality = middleProductDao.getQuality(id);//.getMiddlePproduct();
					float currentQuality=purVerifyDao.findActualQuality(id);
					if(midQuality>=0){
						midQuality-=currentQuality;
						if(midQuality>=0){
							//修改库存
							re = middleProductDao.drow(midQuality,id);
							if(re>0) {
								ro.setSuccess(true);
								ro.setMsg("撤销成功");
							}else {
								ro.setSuccess(false);
								ro.setMsg("撤销失败！");
							}
						}else{
							ro.setMsg("请勿重复撤销！");
							ro.setSuccess(false);
							throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
						}

					}else {
						ro.setMsg("库房为空");
						throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
					}
					
				}else {
					ro.setSuccess(false);
					ro.setMsg("修改采购计划失败");
					throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
				}
			
		
		}else {
			ro.setSuccess(false);
            ro.setMsg("失败！");
		}
		//获取确认单中实收数量
		
		return ro;
	}
	
	
	
}
