package com.zt.serviceImp;

import com.zt.common.Message;
import com.zt.common.MessageUtil;
import com.zt.common.UsersUtils;
import com.zt.constant.DepmentAndPosCode;
import com.zt.dao.DepartmentDao;
import com.zt.dao.EmployeeDao;
import com.zt.dao.MidProductInRecievingDao;
import com.zt.po.MidProductInRecieving;
import com.zt.po.MiddleProduct;
import com.zt.service.MidProductInRecievingService;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.zt.dao.MiddleProductDao;
import com.zt.dao.UsersDao;
import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;

/**
 * @author wl
 * @date 2019年7月1日 
 */
@Service("midProductInRecievingService")
public class MidProductInRecievingServiceImp implements MidProductInRecievingService {
	@Autowired
	MidProductInRecievingDao midProductInRecDao;
	@Autowired
	MiddleProductDao midProductDao;
	@Autowired
	EmployeeDao employeeDao;
	@Autowired
	private MessageUtil msgutil;
	@Autowired
	DepartmentDao depDao;
	@Autowired
	UsersDao usersDao;
	@Autowired
	Message message;
	
	/*
	 * 半成品入库的撤回
	 */
	@Override
	public ResultObject<MidProductInRecieving> withdraw(long id) throws BusinessRuntimeException{
		ResultObject<MidProductInRecieving> ro = new ResultObject<>();
		MidProductInRecieving inRec = midProductInRecDao.findById(id);
		inRec.setVerifyStatus(2);
		inRec = midProductInRecDao.saveAndFlush(inRec);
		float quantity = inRec.getQuantity();
		
		MiddleProduct midproduct = inRec.getMidproduct();
		if(midproduct.getProductNo()-quantity>=0) {
			midproduct.setProductNo(midproduct.getProductNo()-quantity);
			midproduct.setBuyStatus(true);
			midproduct = midProductDao.saveAndFlush(midproduct);
			if(inRec!=null&&midproduct!=null) {
				ro.setSuccess(true);
				ro.setMsg("撤销成功");
				//发送消息提示撤销了一个入库
				String userName = UsersUtils.getCurrentHr().getEmpName();
				StringBuilder sb = new StringBuilder();
				sb.append(userName)
				.append("撤销了一个半成品的自购入库");
				
				StringBuilder ss = new StringBuilder();
				ss.append("半成品为:")
				.append(inRec.getName())
				.append("数量为:")
				.append(inRec.getQuantity())
				.append("时间为:")
				.append(inRec.getCreateDate());
				
				int re = msgutil.sendMsg(sb.toString(), ss.toString(), "MidProductInRecieving", inRec.getId(), message.UserIds());
				if(re>0) {
					ro.setSuccess(true);
					ro.setMsg("发送消息成功");
				}else {
					ro.setSuccess(false);
					ro.setMsg("发送消息失败");
				}
				
				
			}else {
				ro.setSuccess(false);
				ro.setMsg("撤销失败");
				throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
			}
		}
		
		return ro;
	}
	/*
	 * 分页模糊查询
	 */
	@Override
	public ResultPage<MidProductInRecieving> findSearch(String queryName, int page, int size)
			throws BusinessRuntimeException {
		ResultPage<MidProductInRecieving> ro = new ResultPage<>();
		Pageable pageable = PageRequest.of(page-1, size);
		Page<MidProductInRecieving> pages = midProductInRecDao.findSearch(queryName, pageable);
		
		if(pages!=null) {
			int totals=(int) pages.getTotalElements();
			ro.setData(pages.getContent());
			ro.setTotal(totals);
			ro.setTotalPages(pages.getTotalPages());
			ro.setSuccess(true);
		}else {
			ro.setSuccess(false);
			throw new BusinessRuntimeException(ResultCode.UNKNOWN_ERROR);
		}
		return ro;
	}
	/*
	 * 新增方法
	 */
	@Override
	public ResultObject<MidProductInRecieving> addNew(long id, float quaity, String notes)
			throws BusinessRuntimeException {
		ResultObject<MidProductInRecieving> ro = new ResultObject<>();
		MidProductInRecieving m = new MidProductInRecieving();
		MiddleProduct midProduct = midProductDao.findById(id);
		m.setName(midProduct.getName());
		m.setUnitId(midProduct.getUnitId());
		m.setUnitName(midProduct.getUnitName());
		m.setQuantity(quaity);
		m.setMidproduct(midProduct);
		m.setVerifyStatus(1);
		m.setEnable(true);
		m.setNotes(notes);
		long empId = UsersUtils.getCurrentHr().getEmpId();
		m.setOperatorId(empId);
		m.setOperator(employeeDao.findById(empId));
		m.setOperatorName(employeeDao.findById(empId).getName());
		m.setCreateDate(new Date());
		m = midProductInRecDao.save(m);
		if(m!=null) {
			ro.setSuccess(true);
			ro.setMsg("新增成功");
			//修改库存
			midProduct.setProductNo(quaity+midProduct.getProductNo());
			midProduct = midProductDao.saveAndFlush(midProduct);
			if(midProduct!=null) {
				ro.setSuccess(true);
				ro.setMsg("库存修改成功");
				//发送消息提示新建一个入库
				String userName = UsersUtils.getCurrentHr().getEmpName();
				StringBuilder sb = new StringBuilder();
				sb.append(userName)
				.append("撤销了一个半成品的自购入库");
				
				StringBuilder ss = new StringBuilder();
				ss.append("半成品为:")
				.append(m.getName())
				.append("数量为:")
				.append(m.getQuantity())
				.append("时间为:")
				.append(m.getCreateDate());
				int re = msgutil.sendMsg(sb.toString(), ss.toString(), "MidProductInRecieving", m.getId(), message.UserIds());
				if(re>0) {
					ro.setSuccess(true);
					ro.setMsg("发送消息成功");
				}else {
					ro.setSuccess(false);
					ro.setMsg("发送消息失败");
				}
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
