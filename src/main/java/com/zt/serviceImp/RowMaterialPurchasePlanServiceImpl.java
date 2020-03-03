/**
 * 
 */
package com.zt.serviceImp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.zt.common.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zt.common.MessageUtil;
import com.zt.common.UsersUtils;
import com.zt.constant.DepmentAndPosCode;
import com.zt.dao.ClientDao;
import com.zt.dao.DepartmentDao;
import com.zt.dao.EmployeeDao;
import com.zt.dao.MiddleProductDao;
import com.zt.dao.RowMaterialPurchasePlanDetailDao;
import com.zt.dao.RowMaterialDao;
import com.zt.dao.RowMaterialPurchasePlanDao;
import com.zt.dao.SysUnitDao;
import com.zt.dao.UsersDao;
import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.Client;
import com.zt.po.RowMaterialPurchasePlan;
import com.zt.po.RowMaterialPurchasePlanDetail;
import com.zt.po.SysUnit;
import com.zt.service.RowMaterialPurchasePlanService;
import com.zt.vo.PlanDetailsModel;
import com.zt.vo.PlanModel;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author yh
 * @date 2019年5月9日
 */
@Service("rowMaterialPurchasePlanService")
//@CacheConfig(cacheNames = "zt_caches_purchaseplan")
public class RowMaterialPurchasePlanServiceImpl implements RowMaterialPurchasePlanService{
	 private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	@Autowired
	private RowMaterialPurchasePlanDao planDao;
	@Autowired
	private RowMaterialPurchasePlanDetailDao planDetailDao;
	@Autowired
	private RowMaterialDao 	 materialDao;
	@Autowired
	private MiddleProductDao 	 midProductDao;
	@Autowired
	private ClientDao  clientDao;
	@Autowired
	private SysUnitDao unitDao;
	@Autowired
	private MessageUtil msgutil;
	@Autowired
	private RowMaterialDao rowMaterialDao;
	@Autowired
	Message message;
	@Override
//	@Cacheable(key="'pp_'+#identifier+#page+#size")
	public ResultPage<RowMaterialPurchasePlan> findpurchasePlan(String identifier, int page, int size)
			throws BusinessRuntimeException {
		ResultPage<RowMaterialPurchasePlan> ro=new ResultPage<>();
		Pageable pageable = PageRequest.of(page-1,size);

		Page<RowMaterialPurchasePlan> pagelist=planDao.findbypage(identifier,pageable);
//		int count=planDao.countAllData(identifier);
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
	 * 删除
	 */
	@Override
//	@CacheEvict(allEntries=true)//清除缓存
	public ResultObject<RowMaterialPurchasePlan> deletpurchasePlan(long id) throws BusinessRuntimeException {
		ResultObject<RowMaterialPurchasePlan> ro=new ResultObject<>(); 
		RowMaterialPurchasePlan acris=planDao.findById(id);
		 if (acris!=null) {
			 List<RowMaterialPurchasePlanDetail> detailList = acris.getDetail();
			 for (int i = 0,n = detailList.size(); i < n; i++) {
				 RowMaterialPurchasePlanDetail ppd = detailList.get(i);
				 ppd.setEnabled(false);
				 List<RowMaterialPurchasePlanDetail> su = planDetailDao.findConnectDetail(ppd.getMaterialId());
					if(su==null||su.size()==0) {
						int ss = updateStatus(ppd.getMaterialId());
						if(ss>0) {
							ro.setMsg("修改状态成功");
						}else {
							ro.setMsg("修改状态失败");
							throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
						}
						
					}
				 detailList.add(ppd);
			}
			 acris.setDetail(detailList);
			 acris.setEnabled(false);
			 acris = planDao.saveAndFlush(acris);
			 if(acris!=null) {
				 ro.setSuccess(true);
				 ro.setMsg("操作成功");
				 //发送消息提示删除了一个采购计划
				 
				 String userName = UsersUtils.getCurrentHr().getEmpName();
				 StringBuilder sb = new StringBuilder();
				 sb.append(userName)
				 .append("删除了一个原材料采购计划");
				 
				 int re = msgutil.sendMsg(sb.toString(), acris.getPlanName(), "RowMaterialPurchasePlan", acris.getId(), message.UserIds());
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
				throw	new BusinessRuntimeException(ResultCode.OPER_FAILED);
			}  
			 
			
		}
		return ro;
	}

	/**
	 *   获取编号
	 */
	@Override
	public ResultObject<RowMaterialPurchasePlan> getSerialNumber() throws BusinessRuntimeException {
		ResultObject<RowMaterialPurchasePlan> ro=new ResultObject<>();
		String no="P00001";
		Long maxId=planDao.getMaxId();
		 if (maxId!=null) {
			 maxId++;
			 no= String.format("P%05d",maxId);
		}
		Map<String, String> root =new HashMap<String, String>();
		root.put("serialNumber", no);
		ro.setRoot(root);
		return ro;
	}

	/**
	 * 获取页面初始化数据
	 */
	@Override
	public ResultObject<RowMaterialPurchasePlan> basedata() throws BusinessRuntimeException {
		ResultObject<RowMaterialPurchasePlan> ro=new ResultObject<>();
		//查询上游公司
		Map<String, Object> root =new HashMap<String, Object>();
		List<Object[]>    materiallist=materialDao.findAllMaterail();
		List<Object[]>     midProductlist=midProductDao.findAllPruduct();
		Set<Client> clients=clientDao.findAllByTypes("up");
		Set<SysUnit> units=  unitDao.findAllUnits();
		List<Object[]> midProSpces = midProductDao.findSpecs();
		List<Object[]> materialSpces = materialDao.findSpecs();
		if (units!=null) {
			root.put("clients", clients);
			root.put("units", units);
			root.put("materials", materiallist);
			root.put("products", midProductlist);
			root.put("midProSpces",midProSpces);
			root.put("materialSpces", materialSpces);
			ro.setRoot(root);
			ro.setSuccess(true);
		}else { 
			ro.setSuccess(false);
			throw new BusinessRuntimeException(ResultCode.UNKNOWN_ERROR);
		}
		return ro;
	}
	/*
	 * 新增采购计划
	 */
	@Override
	@Transactional
	public ResultObject<RowMaterialPurchasePlan> andNewPlan(PlanModel plan) throws BusinessRuntimeException {
		ResultObject<RowMaterialPurchasePlan> ro = new ResultObject<>();
			PlanModel mo = new PlanModel();
		RowMaterialPurchasePlan purPlan = mo.v1p(plan);
		//解析出详情中的数据,存储在对象中
		JSONArray array = JSONArray.fromObject(plan.getDetail());
		List<RowMaterialPurchasePlanDetail> details = new ArrayList<>();
		PlanDetailsModel detailmo=new PlanDetailsModel();
		List<String> sn = planDao.selectNumber(plan.getSerialNumber());
		if(sn==null||sn.size()==0) {
		if(array.size()>0) {
			for (int i = 0,n=array.size(); i < n; i++) {
				JSONObject object = array.getJSONObject(i);
				PlanDetailsModel planDetailModel = new PlanDetailsModel();
				RowMaterialPurchasePlanDetail planDetail=new RowMaterialPurchasePlanDetail();
				planDetailModel.setName(String.valueOf(object.get("name")));
				planDetailModel.setQuantity(Float.parseFloat(String.valueOf(object.get("quantity"))));
				long uid = Long.parseLong(String.valueOf(object.get("unitId")));
				planDetailModel.setUnitId(uid);
				planDetailModel.setUnitName(String.valueOf(object.get("unitName")));
				planDetailModel.setNotes(String.valueOf(object.get("notes")));
				long parseLong = Long.parseLong(String.valueOf(object.get("materialId")));
				planDetailModel.setMaterialId(parseLong);
				planDetailModel.setSpecifications(String.valueOf(object.get("specifications")));
				planDetail=detailmo.v1p(planDetailModel);
				planDetail.setConfirmStatus(1);
				if(uid>0) {
					SysUnit role = unitDao.findById(uid);
					planDetail.setUnits(role);
				}
				//原材料
				planDetail.setRowMaterial(rowMaterialDao.findById(parseLong));
				int updateStatus = updateStatus(planDetailModel.getMaterialId());
				if(updateStatus>0) {
					ro.setMsg("修改状态成功");
				}else {
					ro.setMsg("修改状态失败");
					throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
				}
				planDetail.setPlan(purPlan);
				details.add(planDetail);
			}
		}
//		details= planDetailDao.saveAll(details);
		 String planName = simpleDateFormat.format(new Date())+"采购单";
		 if (plan.getClientId()!=null&&plan.getClientId()!=0) {
			 Optional<Client> c=clientDao.findById(plan.getClientId());
			 if (c.isPresent()) {
				 purPlan.setClient(c.get());  
			}
		}
		purPlan.setPlanName(planName);; 
		purPlan.setEnabled(true);
		purPlan.setDetail(details);
		purPlan.setSerialNumber(plan.getSerialNumber());
		purPlan = planDao.save(purPlan);
		if(purPlan!=null) {
			ro.setSuccess(true);
			ro.setMsg("操作成功");
			//发送消息
			//获取当前操作人的姓名
			String userName = UsersUtils.getCurrentHr().getEmpName();
			StringBuilder sb = new StringBuilder();
			sb.append(userName)
			.append("新建了一个采购计划");
			
			int re = msgutil.sendMsg(sb.toString(), purPlan.getPlanName(), "PurchasePlan", purPlan.getId(), message.UserIds());
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
		}else {
			ro.setSuccess(false);
			ro.setMsg("该编号已存在,请重新新建");
			//throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
		}
		return ro;
	}
	/*
	 * 修改
	 */
	@Override
	@Transactional
	public ResultObject<RowMaterialPurchasePlan> update(PlanModel plan) throws BusinessRuntimeException {
		ResultObject<RowMaterialPurchasePlan> ro = new ResultObject<>();
		PlanModel mo=new PlanModel();
		PlanDetailsModel detailmo=new PlanDetailsModel();
		JSONArray array = JSONArray.fromObject(plan.getDetail());
		List<PlanDetailsModel> detailsModelList = new ArrayList<>();
		if(plan.getId()>0) {
			RowMaterialPurchasePlan purPlan = planDao.findById(plan.getId());
			List<RowMaterialPurchasePlanDetail> planDetailList =purPlan.getDetail();
			purPlan = mo.v1p(plan);
			if(array.size()>0) {
				for (int i = 0,n = array.size(); i < n; i++) {
					JSONObject object = array.getJSONObject(i);
					PlanDetailsModel detailsModel = new PlanDetailsModel();
					Long detailId=Long.parseLong(String.valueOf(object.get("id")));
					Long unitId=Long.parseLong(String.valueOf(object.get("unitId")));
					Float quantity=Float.parseFloat(String.valueOf(object.get("quantity")));
					SysUnit unit=null;
					if (unitId!=null&&unitId>0) {
						Optional<SysUnit> units = unitDao.findById(unitId);
						if (units.isPresent()) {
							unit=units.get();
						}
					}
					String name=String.valueOf(object.get("name"));		
					String notes=String.valueOf(object.get("notes"));		
					String type=String.valueOf(object.get("type"));
					String unitName=String.valueOf(object.get("unitName"));
					Long materialId = Long.valueOf(String.valueOf(object.get("materialId")));
					String specifications = String.valueOf(object.get("specifications"));					
					detailsModel.setId(detailId);
					detailsModel.setName(name);
					detailsModel.setQuantity(quantity);
					detailsModel.setUnitName(unitName);
					detailsModel.setMaterialId(materialId);
					detailsModel.setSpecifications(specifications);
				
					if (null!=unitId&&unitId>0) {
						detailsModel.setUnitId(unitId);
					}
					detailsModel.setNotes(notes);
					
					switch (type) {
					 /**
				     * delete
				     * update
				     * new
				     */
					case "new":
						RowMaterialPurchasePlanDetail detail=detailmo.v1p(detailsModel);
						if (unit!=null) {
							detail.setUnits(unit);
						}
						//原材料
						detail.setRowMaterial(rowMaterialDao.findById((long)detail.getMaterialId()));
						
						int updateStatus = updateStatus(detailsModel.getMaterialId());
						if(updateStatus>0) {
							ro.setMsg("修改状态成功");
						}else {
							ro.setMsg("修改状态失败");
							throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
						}
						detail.setPlan(purPlan);
						detail.setConfirmStatus(1);
						detail=planDetailDao.save(detail);
						planDetailList.add(detail);
						break;
					case "update":
						RowMaterialPurchasePlanDetail detailup=detailmo.v1p(detailsModel);
						if(unit!=null) {
							detailup.setUnits(unit);
							}
						//原材料
						detailup.setRowMaterial(rowMaterialDao.findById((long)detailup.getMaterialId()));
						detailup.setPlan(purPlan);
						detail=planDetailDao.saveAndFlush(detailup);
	//					planDetailList.add(detail);
						break;
					case "delete":
						RowMaterialPurchasePlanDetail detailde=detailmo.v1p(detailsModel);
						for (int j = 0,m=planDetailList.size(); j < m; j++) {
							RowMaterialPurchasePlanDetail ppd = planDetailList.get(j);
							if (ppd.getId()==detailde.getId()) {
								planDetailDao.deleteById(detailsModel.getId());
								planDetailList.remove(j);
								
								
								List<RowMaterialPurchasePlanDetail> su = planDetailDao.findConnectDetail(ppd.getMaterialId());
								if(su==null||su.size()==0) {
									int ss = updateStatus(ppd.getMaterialId());
									if(ss>0) {
										ro.setMsg("修改状态成功");
									}else {
										ro.setMsg("修改状态失败");
										throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
									}
								}
							}
						}
						break;
					default:
						break;
					}
				}
				purPlan.setDetail(planDetailList);
				
				purPlan= planDao.saveAndFlush(purPlan);
				
				if(purPlan!=null) {
					ro.setSuccess(true);
					ro.setMsg("操作成功");
					//发送消息提示
					String userName = UsersUtils.getCurrentHr().getEmpName();
					StringBuilder sb = new StringBuilder();
					sb.append(userName)
					.append("修改了一个采购计划");
					
					int re = msgutil.sendMsg(sb.toString(), purPlan.getPlanName(), "PurchasePlan", purPlan.getId(), message.UserIds());
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
			}
		}else {
			ro.setSuccess(false);
			ro.setMsg("操作失败");
			throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
		}
		
		
		return ro;
	}
	
	public int updateStatus(Long id) {
		int result = 0;
		Integer updateByrepairman = rowMaterialDao.updateByrepairman(id);
		if(updateByrepairman!=null&&updateByrepairman>0) {
			result = 1;
		}
		return result;
	}
	
    
}
