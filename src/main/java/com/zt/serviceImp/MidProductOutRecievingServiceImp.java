package com.zt.serviceImp;

import com.zt.common.Message;
import com.zt.common.MessageUtil;
import com.zt.common.UsersUtils;
import com.zt.constant.DepmentAndPosCode;
import com.zt.dao.DepartmentDao;
import com.zt.dao.EmployeeDao;
import com.zt.dao.MidProductOutRecievingDao;
import com.zt.dao.MiddleProductDao;
import com.zt.dao.UsersDao;
import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.MidProductOutRecieving;
import com.zt.po.MiddleProduct;
import com.zt.service.MidProductOutRecievingService;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author wl
 * @date 2019年7月1日 
 */
@Service("midProductOutRecievingService")
public class MidProductOutRecievingServiceImp implements MidProductOutRecievingService {
	@Autowired
	MidProductOutRecievingDao midProductOutDao;
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
	 * 半成品出库的撤回
	 */
	@Override
	public ResultObject<MidProductOutRecieving> withdraw(long id) {
		ResultObject<MidProductOutRecieving> ro = new ResultObject<>();
		MidProductOutRecieving outRec = midProductOutDao.findById(id);
		//将撤销状态改成撤销
		outRec.setVerifyStatus(2);
		outRec = midProductOutDao.saveAndFlush(outRec);
		float quantity = outRec.getQuantity();
		
		MiddleProduct midProduct = outRec.getMidproduct();
		midProduct.setProductNo(midProduct.getProductNo()+quantity);
		midProduct = midProductDao.saveAndFlush(midProduct);
		if(outRec!=null&&midProduct!=null) {
			ro.setSuccess(true);
			ro.setMsg("撤销成功");
			//发消息
			String userName = UsersUtils.getCurrentHr().getEmpName();
			StringBuilder sb = new StringBuilder();
			sb.append(userName)
			.append("撤销了一个半成品的出库");
			
			StringBuilder ss = new StringBuilder();
			ss.append("半成品为:")
			.append(outRec.getName())
			.append("数量为:")
			.append(outRec.getQuantity())
			.append("时间为:")
			.append(outRec.getCreateDate());
			
			int re = msgutil.sendMsg(sb.toString(), ss.toString(), "MidProductOutRecieving", outRec.getId(), message.UserIds());
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
		
		return ro;
	}
	/*
	 * 分页模糊查询
	 */
	@Override
	public ResultPage<MidProductOutRecieving> findSearch(String queryName, int page, int size)
			throws BusinessRuntimeException {
		ResultPage<MidProductOutRecieving> ro = new ResultPage<>();
		Pageable pageable = PageRequest.of(page-1, size);
		Page<MidProductOutRecieving> pages = midProductOutDao.findSearch(queryName, pageable);
		
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
	 * 新增
	 */
	@Override
	public ResultObject<MidProductOutRecieving> addNew(long id, float quaity, String notes)
			throws BusinessRuntimeException {
		ResultObject<MidProductOutRecieving> ro = new ResultObject<>();
		MidProductOutRecieving m = new MidProductOutRecieving();
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
		m = midProductOutDao.save(m);
		if(m!=null) {
			ro.setSuccess(true);
			ro.setMsg("新增成功");
			//修改库存
			midProduct.setProductNo(midProduct.getProductNo()-quaity);
			midProduct = midProductDao.saveAndFlush(midProduct);
			if(midProduct!=null) {
				ro.setSuccess(true);
				ro.setMsg("库存修改成功");
				//发消息
				String userName = UsersUtils.getCurrentHr().getEmpName();
				StringBuilder sb = new StringBuilder();
				sb.append(userName)
				.append("撤销了一个半成品的出库");
				
				StringBuilder ss = new StringBuilder();
				ss.append("半成品为:")
				.append(m.getName())
				.append("数量为:")
				.append(m.getQuantity())
				.append("时间为:")
				.append(m.getCreateDate());
				int re = msgutil.sendMsg(sb.toString(), ss.toString(), "MidProductOutRecieving", m.getId(), message.UserIds());
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
