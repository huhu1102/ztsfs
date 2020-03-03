package com.zt.serviceImp;

import com.zt.common.Message;
import com.zt.common.MessageUtil;
import com.zt.common.UsersUtils;
import com.zt.common.Utils;
import com.zt.dao.*;
import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.*;
import com.zt.service.ProductManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author wl
 * @date 2019年5月7日 
 */
@Service("productmanageService")
@CacheConfig(cacheNames = "zt_caches_productmanage")
public class ProductManageServiceImp implements ProductManageService{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	ProductManageDao managerDao;
	@Autowired
	ClientDao clientDao;
	@Autowired
	ProductDao productDao;
	@Autowired
	ProductProcessDao proProDao;
	@Autowired
	ProductManageDetailsDao proManageDetailsDao;
	@Autowired
	MiddleProductDao midProDao;
	@Autowired
	EmployeeDao empDao;
	@Autowired
	ProductionPlanDetailsDao productionPlanDetailsDao;
	@Autowired
	FinishedProductDao finishProductDao;
	@Autowired
	FinishedRecievingDao finRecDao;
	@Autowired
	Message message;
	@Autowired
	MessageUtil msgUtil;
	@Autowired
	SalesPlanDao salesPlanDao;
	@Autowired
	ProductPreProcessDao productPreProcessDao;
	@Autowired
	ProductProcessDao productProcessDao;
	@Autowired
	ProductManageProgressDao productManageProgressDao;
	/*
	 * 分页查找
	 */
	@Override
//	@Cacheable(key="'prom_'+#queryName+'_'+#page+'_'+#size")
	public ResultPage<ProductManage> findSearch(String clientName, String productName, String empName, String start, String end, Integer status,int page, int size) throws BusinessRuntimeException {
		ResultPage<ProductManage> ro=new ResultPage<>();
		Pageable pageable = PageRequest.of(page-1,size);
		List<ProductManage>productManageList=new ArrayList<>();
		int totals=0 , totalPage=0;
		if(status!=null&&status.equals(1)){
			Page<ProductManage>	pagesS2= managerDao.findstateSearch(clientName,productName,empName,start,end,1,pageable);
			totals+=pagesS2.getTotalElements();
			totalPage+=pagesS2.getTotalPages();
			productManageList.addAll(pagesS2.getContent());
		}else{
			Page<ProductManage>	pages=managerDao.findSearch(clientName,productName,empName,start,end,status,pageable);
			productManageList=pages.getContent();
			totals+=pages.getTotalElements();
			totalPage+=pages.getTotalPages();
		}
		 if (productManageList!=null) {

	        	 ro.setData(productManageList);
	    	     ro.setTotal(totals);
	    	     ro.setTotalPages(totalPage);
	    	     ro.setSuccess(true);
			} else {
				ro.setSuccess(false);
				throw new BusinessRuntimeException(ResultCode.UNKNOWN_ERROR);
			}
		return ro;
	}
	/*
	 * 新建生产管理
	 */
	@Override
	@Transactional
	public ResultObject<ProductManage> addProductionManage(String note,long planDetailsId,long progressId){
		ResultObject<ProductManage> ro=new ResultObject<>();
		ProductManage productManage = new ProductManage();
		ProductionPlanDetails productionPlanDetails = productionPlanDetailsDao.findById(planDetailsId);
		productManage.setPlanDetails(productionPlanDetails);
		productManage.setMangeStatus(1);
		productManage.setNote(note);
		productManage.setCreateDate(new Date());
		productManage.setEnabled(true);
		productManage.setProgressId(progressId);
		long empId = UsersUtils.getCurrentHr().getEmpId();
		productManage.setManger(empDao.findById(empId));
		productManage.setManagerId(empId);
		String planNo = managerDao.findMaxPlanNo();
		productManage.setNumber(Utils.newPlanNo(planNo, "M"));
		List<ProductManageProgress>list= getList(progressId,productManage);//new ArrayList<>();
		productManage.setProgress(list);
		productManage = managerDao.save(productManage);
		if(productManage!=null){
			ro.setMsg("添加成功");
			//修改销售计划的状态
			salesPlanDao.updateManageStatus(1,2,productionPlanDetails.getSalesPlanId());
			//修改生产计划详情的状态
			productionPlanDetailsDao.updateStatus(2, planDetailsId);
			//新建进度汇总表

			//发消息
			String userName = UsersUtils.getCurrentHr().getEmpName();
			StringBuilder title = new StringBuilder();
			title.append(userName)
			.append("创建了一个新的生产管理")
			.append(productManage.getNumber());
			int res= msgUtil.sendMsg(title.toString(), title.toString(), "ProductManage",productManage.getId(), message.UserIds());
			if(res>0) {
				ro.setSuccess(true);
			}else {
				ro.setSuccess(false);
				ro.setMsg("发送消息失败");
			}
		}else{
			ro.setSuccess(false);
			ro.setMsg("添加失败");
			throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
		}
		return ro;
	}
   /**
	 *过去
	 * */
	private List<ProductManageProgress> getList(long progressId,ProductManage productManage) {
		ProductPreProcess productPreProcess = productPreProcessDao.findById(progressId);
		List<ProductProcess> productProcessList = productPreProcess.getProcess();
		List<ProductManageProgress>list=new ArrayList<>();
		if(productProcessList.size()>0){
			for (int i = 0,n=productProcessList.size(); i < n; i++) {
				ProductProcess productProcess = productProcessList.get(i);
				ProductManageProgress productManageProgress =  new ProductManageProgress();
				productManageProgress.setProductProcessId(productProcess.getId());
				productManageProgress.setProductProcess(productProcessDao.findById(productProcess.getId()));
				productManageProgress.setCreateDate(new Date());
				productManageProgress.setProductManage(productManage);
				productManageProgress.setProductManageId(productManage.getId());
				productManageProgress.setUnitId(productManage.getPlanDetails().getSalesPlan().getProduct().getSysUnitId());
				productManageProgress.setUnit(productManage.getPlanDetails().getSalesPlan().getProduct().getSysUnit());
//					productManageProgressDao.saveAndFlush(productManageProgress);
				list.add(productManageProgress);
			}
		}
		return  list;
	}

	/*
	 * 修改生产管理
	 */
	@Override
    @Transactional
	public ResultObject<ProductManage> update(String note,long id) throws BusinessRuntimeException{
		ResultObject<ProductManage> ro = new ResultObject<>();
		Integer update = managerDao.updateNote(note, id);
		if(update>0){
			ro.setSuccess(true);
		}else{
			ro.setSuccess(false);
			ro.setMsg("修改失败");
			throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
		}
		return ro;
	}
	/*
	 * 删除
	 */
	@Override
	@CacheEvict(allEntries = true)
	public ResultObject<ProductManage> deletPm(long id) throws BusinessRuntimeException {
		ResultObject<ProductManage> ro=new ResultObject<>(); 
		ProductManage pos=managerDao.findById(id);
		 if (pos!=null) {
			pos.setEnabled(false);
			pos.setMangeStatus(4);
			managerDao.saveAndFlush(pos);
			ro.setSuccess(true);
			ro.setMsg("操作成功");
			//发消息
			 String userName = UsersUtils.getCurrentHr().getEmpName();
			 StringBuilder title = new StringBuilder();
			 title.append(userName)
			 .append("删除了一个生产管理");
			 int re= msgUtil.sendMsg(title.toString(), title.toString(), "ProductManage",pos.getId(), message.UserIds());
			 if(re>0) {
				 ro.setSuccess(true);
				 ro.setMsg("发送消息成功");
			 }else {
				 ro.setSuccess(false);
				 ro.setMsg("发送消息失败");
			 }

		}else {
			ro.setSuccess(false);
			ro.setMsg("操作失败");
			throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
		}  
		return ro;
	}
	/*
	 * 编号
	 */
	public String  currentNo(){
		
		String mpNo = "0001";
		Long maxId= managerDao.findCurrentNo();
		if(maxId!=null) {
			maxId+=1;
			mpNo = String.format("%05d",maxId);	
		}
		return mpNo;
	}

	/*
	生产完成
	 */
	@Override
	@Transactional
	public ResultObject<ProductManage> endProduction(long id) throws BusinessRuntimeException {
		ResultObject<ProductManage> ro = new ResultObject<>();
		ProductManage productManage = managerDao.findById(id);
		//修改生产管理的状态
		int a = managerDao.upStatusId(3, id);
		//修改与之关联的某个生产计划详情
		int b = productionPlanDetailsDao.updateStatus(3, productManage.getPlanDetails().getId());
		if(a>0&&b>0){
			ro.setSuccess(true);
			ro.setMsg("状态修改成功");

			//需要查询销售计划是否下发完成
			SalesPlan salesPlan = productManage.getPlanDetails().getSalesPlan();
			if(salesPlan.getPlanStatus()==3){
				boolean result =false;
				//遍历所有的生产计划
				List<ProductionPlanDetails> productionPlanDetailsList = productionPlanDetailsDao.findBySalesPlanId(salesPlan.getId());
				for (int i = 0,n=productionPlanDetailsList.size(); i < n; i++) {
					ProductionPlanDetails productionPlanDetails = productionPlanDetailsList.get(i);
					if(productionPlanDetails.getStatus()==3){
						logger.info("当前生产计划详情生产完成");
						result=true;
						break;
					}
				}
				if(result){
					salesPlanDao.updateManageStatus(2,2, salesPlan.getId());
				}
			}
			//发送消息
			StringBuilder sb = new StringBuilder();
			sb.append("客户:")
					.append(productManage.getPlanDetails().getSalesPlan().getClient().getName())
					.append("产品名:")
					.append(productManage.getPlanDetails().getSalesPlan().getProduct().getProducteName());
			int result = msgUtil.sendMsg("生产完成", sb.toString(), "ProductManage",id, message.UserIds());
			if(result>0){
				ro.setSuccess(true);
				ro.setMsg("消息发送成功");
			}else{
				ro.setSuccess(false);
				ro.setMsg("消息发送失败");
			}
		}else{
			ro.setSuccess(false);
			ro.setMsg("状态修改失败");
		}
		return ro;
	}

	/*
	查看状态的数量
	 */
	@Override
	public ResultObject<ProductManage> findStatus() throws BusinessRuntimeException {
		ResultObject<ProductManage> ro = new ResultObject<>();
		Set<ProductManage> status = managerDao.findStatus();
		if(status!=null){
			List<ProductManage> list = new ArrayList<>(status);
			ro.setData(list);
			logger.info("成功");
			ro.setSuccess(true);
		}else{
			logger.info("查询失败！");
			ro.setSuccess(false);
			throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
		}
		return ro;
	}

	@Override
	public ResultPage<ProductManage> findForSend(String clientName, String productName, String empName, String start, String end, Integer status, int page, int size) {
		ResultPage<ProductManage>ro=new ResultPage<>();
		Pageable pageable = PageRequest.of(page-1,size);
		Page<ProductManage>	productSend= managerDao.findForSend(clientName,productName,empName,start,end,status,pageable);
		if (productSend!=null){
			ro.setSuccess(true);
            ro.setTotal(productSend.getTotalElements());
            ro.setData(productSend.getContent());
		}else{
			ro.setSuccess(false);
            throw  new BusinessRuntimeException(ResultCode.OPER_FAILED);
		}

		return ro;
	}

	@Override
	public ResultObject<ProductManage> revoke(long id) throws BusinessRuntimeException {
		ResultObject<ProductManage> ro = new ResultObject<>();
		int revoke = managerDao.revoke(id);
		if(revoke>0){
			ro.setMsg("修改状态成功");
			ro.setSuccess(true);
			//发消息给综合部
			int result = msgUtil.sendMsg("生产完成", "修改一个生产计划的发货状态", "ProductManage",id, message.UserIds());
			if(result>0){
				ro.setSuccess(true);
				ro.setMsg("消息发送成功");
			}else{
				ro.setSuccess(false);
				ro.setMsg("消息发送失败");
			}

		}else{
			ro.setMsg("修改失败");
			ro.setSuccess(false);
		}
		return ro;
	}
}
