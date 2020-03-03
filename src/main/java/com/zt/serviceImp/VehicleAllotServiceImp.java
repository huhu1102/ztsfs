/**
 * 
 */
package com.zt.serviceImp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import org.springframework.transaction.annotation.Transactional;

import com.zt.common.MessageUtil;
import com.zt.common.UsersUtils;
import com.zt.common.Utils;
import com.zt.dao.DepartmentDao;
import com.zt.dao.EmployeeDao;
import com.zt.dao.UsersDao;

import com.zt.dao.VechclerequestDao;
import com.zt.dao.VechclerequestDetailsDao;
import com.zt.dao.VehicleAllotDao;
import com.zt.dao.VehicleInfoDao;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.Employee;
import com.zt.po.Vechclerequest;
import com.zt.po.VechclerequestDetails;
import com.zt.po.VehicleAllot;
import com.zt.po.VehicleInfo;
import com.zt.service.VehicleAllotService;
import com.zt.vo.CarAssignedModel;

/**
 * @author yh
 * @date 2019年4月28日
 */
@Service("VehicleAllotService")
@CacheConfig(cacheNames = "zt_caches_vehicleallot")
public class VehicleAllotServiceImp implements VehicleAllotService{
	 private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private VehicleAllotDao vallotDao;
	@Autowired
	private VehicleInfoDao carDao;
	
	@Autowired
	private VechclerequestDao  requestDao;
	@Autowired
	private VechclerequestDetailsDao  detailsDao;
	@Autowired
	private EmployeeDao  empDao;
	@Autowired
	MessageUtil  msgUtil;

	@Autowired
	DepartmentDao depDao;
	@Autowired
	UsersDao usersDao;
	@Override
//	@Cacheable(key="'vcalot_'+#page+'_'+#size")
	public ResultPage<VehicleAllot> findbypage(String queryName, int page, int size) throws BusinessRuntimeException {
		ResultPage<VehicleAllot> ro=new ResultPage<VehicleAllot>();
		Pageable pageable = PageRequest.of(page-1,size);
	Page<VehicleAllot> pages =vallotDao.findbypage(queryName, pageable);
		
		 if (pages!=null) {
	        	int totals=(int) pages.getTotalElements();
	        	 ro.setData(pages.getContent());
	    	     ro.setTotal(totals);
	    	     ro.setTotalPages(pages.getTotalPages());
	    	     ro.setSuccess(true);
			} else {
				ro.setSuccess(false);
				throw  new BusinessRuntimeException(ResultCode.UNKNOWN_ERROR);
			}
		
		return ro;
	}
   //分配车辆 分配单
	@Override
	@CacheEvict(allEntries=true)
	//根据申请车辆分配申请单
	public ResultObject<VehicleAllot> addvcalt(VehicleAllot car) throws BusinessRuntimeException {
		ResultObject<VehicleAllot> ro=new ResultObject<>();
		//分配之前要做的事情
		//获取
		//获得所有的申请年单
		//List <Vechclerequest> vrt=requestDao.findAll();
		//判断是否存在分配单 ，存在则属于修改  不存在则属于创建
		if(Long.valueOf(car.getId())==null||car.getId()==0) {
			//创建新的分配单
			car.setCreateDate(new Date());
			//创建分配单的分配日期   获得系统时间
			//为申请单分配车辆
			car.setCarNo("");
			//获得申请单id;
			Long carrqt=car.getCarRequestId();
			
		}
		//与申请单关联的车牌号码
		String carNos=car.getCarNo();
//		 VehicleInfo car=carDao.findByPlateNumber(carNo);
//		 vcmte.setVech(car);
		car.setEnabled(true);
		List<VehicleInfo> vcf=carDao.findUnused();//或得所有可用的车辆信息
			if(vcf!=null) { 
				for(int i=0;i<vcf.size();i++) {
					VehicleInfo  vf=vcf.get(i);//获取某个空闲车辆
				String vpn=vf.getPlateNumber();
				//根据车牌号和申请人姓名获取申请单
				
				Vechclerequest dd=requestDao.findAsked(vpn);
				//车牌号和车辆申请信息绑定  这句逻辑有问题
				//保存分配单
				//vallotDao.
				VehicleAllot vat  =vallotDao.saveAndFlush(car);
				//更改申请单的申请状态
				 dd.setRequestStatus("MANAGED");
				 ro.setSuccess(true);
					ro.setMsg("操作成功");
				}
			}
				
			else {
			   ro.setSuccess(false);
			   ro.setMsg("操作失败");
			throw   new BusinessRuntimeException(ResultCode.OPER_FAILED);
		   }
		
		return ro;
	}

	@Override
	@CacheEvict(allEntries=true)
	public ResultObject<VehicleAllot> deletvcalt(long id) throws BusinessRuntimeException {
		ResultObject<VehicleAllot> ro=new ResultObject<>(); 
		VehicleAllot vmttt=vallotDao.findById(id);
		 if (vmttt!=null) {
			 vmttt.setEnabled(false);
			 vallotDao.saveAndFlush(vmttt);
			ro.setSuccess(true);
			ro.setMsg("操作成功");
		}else {
			ro.setSuccess(false);
			ro.setMsg("操作失败");
		throw	new BusinessRuntimeException(ResultCode.OPER_FAILED);
		}  
		return ro;
	}
	/**
	 *  生成用车分配记录
	 */
	@Override
	public ResultObject<VehicleAllot> addAssigned(CarAssignedModel assignedModel) throws BusinessRuntimeException {
		ResultObject<VehicleAllot> ro=new ResultObject<VehicleAllot>();
		Vechclerequest  req=requestDao.findById(assignedModel.getRequestId());
		VehicleAllot assign=new VehicleAllot();
		assign.setAplyDate(req.getCreateDate());
        assign.setCarNo(req.getCarNo());	
        assign.setCarRequestId(assignedModel.getRequestId());
        assign.setCreateDate(new Date());
        assign.setEnabled(true);
        assign.setPreEndDate(req.getPreEndDate());
        assign.setRequsetUserId(req.getEmp().getId());
        assign.setEmpName(req.getEmpName());//申请人姓名
        assign.setOperatorId(req.getOperatorId());
        assign.setOperatorName(req.getOperatorName());
        assign.setPreStartDate(req.getPreStartDate());
        assign.setNote(assignedModel.getNote());
        assign.setAssingeState(1);
        assign.setAssingedId( UsersUtils.getCurrentHr().getEmpId());
        assign.setVech(carDao.findByPlateNumber(req.getCarNo()));
        assign=vallotDao.save(assign);
        if (assign!=null) {
        	//修改相关申记录为已分配
        	req.setRequestStatus("MANAGED");
        	//保存申请              
        	req=requestDao.save(req);
              // 给申请人发送消息
         String username=req.getEmpName();
         String title = username+","+"你的车已经分配完毕";
         String contents="综合部"+assign.getEmpName()+"在"+assign.getCreateDate()+"号"+"分配了"+assign.getCarNo()+"的车"+"给你";
//            (String title,String content,String styles, long styleId, long userId
         msgUtil.sendMsg(title, contents, "VehicleAllot", assign.getId(), req.getEmp().getId());
         ro.setSuccess(true);
         ro.setMsg("分配消息发送成功");	
		}else {
			ro.setMsg("请重新分配");
			ro.setSuccess(false);
		}
		return ro;
	}

	/**
	 * 用车分配 修改分配单   既然是修改申请单  此分配单肯定已经存在   要回到编辑状态  无论改人  车  ，或者时间    都需要去数据库  原始记录  已确定他能否修改成功
	 */
	@Override
	@Transactional
	public ResultObject<VehicleAllot> update(VehicleAllot allot) throws BusinessRuntimeException {
		ResultObject<VehicleAllot> ro=new ResultObject<VehicleAllot>();
		
		//获取的分配单所涉及要修改的字段  从前台页面获取的数据
		String allotCarNo=allot.getCarNo(); 
		Date  PreEnd =allot.getPreEndDate();
		String  PreEndTime= simpleDateFormat.format(PreEnd);
		Date  PreStar =allot.getPreStartDate();
		String  PreStarTime=simpleDateFormat.format(PreStar);
		Employee opt= empDao.findById((long)allot.getOperatorId());
		String useEmpName =allot.getOperatorName();
		if (null!=opt) {
			useEmpName=opt.getName();
		}
		allot.setAssingedId(UsersUtils.getCurrentHr().getEmpId());
		allot.setOperatorName(useEmpName);
       //修改车  修改时间  修改人
       //拿到分配单的车牌号 
	   //获得申请id    
		 long  requestid=allot.getCarRequestId();
		 //获得申请单实体对象
		 Vechclerequest vr =requestDao.findById(requestid);
		 allot.setAplyDate(vr.getCreateDate());
		 //获取数据库中的数据  从数据库中拿到的数据
		   String  requestCarNo = vr.getCarNo();
		   String  usedEmpName =vr.getOperatorName();
	       Date     rePreEnd =vr.getPreEndDate();
	       String rePreEndTime=simpleDateFormat.format(rePreEnd);
		   Date    PreStart =vr.getPreStartDate();
		   String PreStartTime=simpleDateFormat.format(PreStart);
		// 人变  车不变  时间变化
		   boolean  carNoE=allotCarNo.equals(requestCarNo),//车牌
				    userNameE= useEmpName.equals(usedEmpName),//人名
					PreEndTimeE=PreEndTime.equals(rePreEndTime),//终止时间
					PreStarTimeE=PreStarTime.equals(PreStartTime),
		            preTime=PreEndTimeE||PreStarTimeE;
		if(carNoE&&userNameE&&preTime){
			
			
			allot= vallotDao.saveAndFlush(allot); 
			if (allot!=null) {
				ro.setSuccess(true);
				
			}else {
				ro.setSuccess(false);
				ro.setMsg("操作失败");
			}
				
			}
		//重新分配用车人 人名不一样  车牌号不修改  行车时间不修改
		else if(carNoE&&PreEndTimeE&&PreStarTimeE&&!userNameE) {
			//获取要换的人的车辆申请记录  确认时间上有无冲突
			
			allot= vallotDao.saveAndFlush(allot); 
				if (allot!=null) {
					StringBuilder b=new StringBuilder();
					b.append("你申请的车已经分配给")
					.append(useEmpName);
					String content=b.toString();
					List<Long> userId= usersDao.finduserIdbyEmpId(vr.getEmp().getId());
					//给申请人发消息
					if (userId!=null&&userId.size()>0) {
						
						msgUtil.sendMsg("用车变更", content, "Vechclerequest", vr.getId(), userId);
					}
					//给分配到的当前的人发消息
					List<Long>  requsetUserId=usersDao.finduserIdbyEmpId(allot.getRequsetUserId());
					msgUtil.sendMsg("用车通知", "您有新用车通知！请查看", "Vechclerequest", vr.getId(), requsetUserId);
					ro.setSuccess(true);
				}else {
					ro.setSuccess(false);
					ro.setMsg("操作失败");
				}
					
			//换车情况
		}else if(preTime&&userNameE&&!carNoE) {
			//查寻换过后的车有没有在出行记录在那天
			List<Date> use= Utils.getTwoDaysDay(simpleDateFormat.format(PreStart),simpleDateFormat.format(rePreEnd));  
			
			List<VechclerequestDetails>  delis =  detailsDao.findNotUse(allotCarNo,use);
			if(delis!=null&&delis.size()>0) {
				 StringBuilder buf=new StringBuilder();
				 for (int i = 0,n=delis.size(); i < n; i++) {
					 buf.append("日期")
					  .append(simpleDateFormat.format((delis.get(i).getRequestTime())))
					 .append("\n")
					 .append("已申请，请重新选择日期")
					 ;
				 }
				 //查到了
				 //请选择新的日期
				 ro.setMsg(buf.toString());
				 ro.setSuccess(false);
			 }else {
				 //保存申请请求
				 //删除原有用车申请
				 List<VechclerequestDetails>  requestDetailList =  vr.getDetails();
				 for (VechclerequestDetails vechclerequestDetails : requestDetailList) {
					 detailsDao.deleteById(vechclerequestDetails.getId());
				}
				 vr.setDetails(null);
				 vr.setCarNo(allotCarNo);
				 VehicleInfo car=carDao.findByPlateNumber(allotCarNo);
					 if (car!=null) {
						 vr.setVehicleInfo(car);
					}
				 vr.setRequestStatus("MANAGED");//已分配
				 vr=requestDao.saveAndFlush(vr);
				  //生成新的用车申请
				 String username = UsersUtils.getCurrentHr().getEmpName();
				 if (vr!=null) {
					 //调用方法 生成申请详情表  日期由前台传进来
					 //获得其实终止日期
					 List<VechclerequestDetails>relist=new ArrayList<VechclerequestDetails>();
					 for (int i = 0,n=use.size(); i < n; i++) {
						 Date date=use.get(i);
						 VechclerequestDetails detail=new VechclerequestDetails();
						 detail.setRequsetState("MANAGED");//已分配
						 detail.setRequestTime(date);
						 detail.setRequestDetail(vr);
						 detail.setRequestId(vr.getId());;
						 relist.add(detail);
					 }
					 relist=  detailsDao.saveAll(relist);
					 vr.setDetails(relist);
					 //保存重新分配之后的用车申请
					 vr=requestDao.saveAndFlush(vr);
					 if (vr!=null) {
						 allot.setVech(car);
						 //分配记录修改
						 allot= vallotDao.saveAndFlush(allot);
						 if (allot!=null) {
							 //发送消息
							 String title = username+"发送了一个车辆分配信息";
							 StringBuilder sb=new StringBuilder();
							 sb.append("用车分配：车牌号:")
							 .append(vr.getCarNo())
							 .append(";\n用车时间:")
							 .append(simpleDateFormat.format(PreStart))
							 .append("至")
							 .append(simpleDateFormat.format(rePreEnd))
							 .append(";\n分配人:")
							 .append(username);
							 String  contents=sb.toString();
							 List<Long> ids=usersDao.finduserIdbyEmpId(vr.getEmp().getId());
							 logger.info(ids+"*********");
							 if (ids.size()>0) {
								 int fromto= msgUtil.sendMsg(title, contents, "Vechclerequest", vr.getId(),ids);
								 if(fromto>0) {
									 ro.setSuccess(true);
									 ro.setMsg("发送消息成功");  
								 }
							 }else {
								 ro.setSuccess(false);
								 ro.setMsg("操发送消息失败");
								 throw  new  BusinessRuntimeException(ResultCode.OPER_FAILED);
							 }
						}
						}else {
							ro.setMsg("未發送消息");
						}
				 }else { 
					  ro.setSuccess(false);
				      ro.setMsg("操作失败");
				      throw  new  BusinessRuntimeException(ResultCode.OPER_FAILED);};
			 }
		}
		 //更换申请表的申请时间 车子  和人没变化  修改申请时间；
		 //用车人不变
		 //使用的车不变
		 //全变	
		else  {
			ro=changeTime(PreStar,PreEnd,PreStart,rePreEnd,ro,vr,allot);
	   
		}
		return ro;
	}
	/**
	 * @param PreStar  重新分配车辆起始时间
	 * @param PreEnd 重新分配车辆结束时间
	 * @param PreStart  申请用车开始时间
	 * @param rePreEnd  申请用车结束时间
	 * @param ro 结果集
	 * @param vr 用车申请实体
	 * @param allot 用车分配 （前台过来需要修改的）
	 * @return
	 */
	private ResultObject<VehicleAllot>  changeTime(Date PreStar,Date PreEnd,Date PreStart,Date rePreEnd, ResultObject<VehicleAllot> ro, Vechclerequest vr, VehicleAllot allot) {
		String allotCarNo=allot.getCarNo();
		//改变用车申请时间
		//分配天数
		List<Date> allotDates= Utils.getTwoDaysDay(simpleDateFormat.format(PreStar),simpleDateFormat.format(PreEnd));
		//用车申请天数
		List<Date> requestDates=Utils.getTwoDaysDay(simpleDateFormat.format(PreStart),simpleDateFormat.format(rePreEnd));
		List<Date> allotCopyDate=new ArrayList<Date>();
		List<Date> requestCopyDate=new ArrayList<Date>();
		allotCopyDate.addAll(allotDates);
		requestCopyDate.addAll(requestDates);
		allotCopyDate.retainAll(requestDates);
		if (allotCopyDate.size()>0) {
			//有交集
			requestCopyDate.removeAll(allotDates);
			if (requestCopyDate.size()>0) {
				/**有要删除的旧的 直接删除 附表，
                   * 更改申请时间，
                   * 保存分配表，
                   * 给申请人发送消息、
                  */
				List<VechclerequestDetails> oodlist=vr.getDetails();
				for (int i = 0,n=oodlist.size(); i < n; i++) {
					VechclerequestDetails detail=	oodlist.get(i);
					for (int j = 0,m=requestCopyDate.size(); j <m; j++) {
						if (detail.getRequestTime().equals(requestCopyDate.get(j))) {
							detailsDao.deleteById(detail.getId());						}
					}
				}
			}
			allotDates.removeAll(requestDates);
			if (allotDates.size()>0) {
				//有新增的
				List<VechclerequestDetails>  delis =  detailsDao.findNotUse(allotCarNo,allotDates);
				if(delis!=null&&delis.size()>0) {
					 StringBuilder buf=new StringBuilder();
					 for (int i = 0,n=delis.size(); i < n; i++) {
						 buf.append("日期")
						  .append(simpleDateFormat.format((delis.get(i).getRequestTime())))
						 .append("\n")
						 .append("已申请，请重新选择日期")
						 ;
					 }
					 //查到了
					 //请选择新的日期
					 ro.setMsg(buf.toString());
					 ro.setSuccess(false);
				 }else {
					 //保存申请请求
					 //删除原有用车申请
					 VehicleInfo car=carDao.findByPlateNumber(allotCarNo);
						 if (car!=null) {
							 vr.setVehicleInfo(car);
						}
					 vr=requestDao.saveAndFlush(vr);
					  //生成新的用车申请
					 String username = UsersUtils.getCurrentHr().getEmpName();
					 if (vr!=null) {
						 //调用方法 生成申请详情表  日期由前台传进来
						 //获得其实终止日期
						 List<VechclerequestDetails>relist=new ArrayList<VechclerequestDetails>();
						 for (int i = 0,n=allotDates.size(); i < n; i++) {
							 Date date=allotDates.get(i);
							 VechclerequestDetails detail=new VechclerequestDetails();
							 detail.setRequsetState("MANAGED");//已分配
							 detail.setRequestDetail(vr);
							 detail.setRequestId(vr.getId());
							 detail.setRequestTime(date);
							 relist.add(detail);
						 }
						 relist=  detailsDao.saveAll(relist);
						 vr.setDetails(relist);
						 vr.setPreEndDate(PreEnd);
						 vr.setPreStartDate(PreStar);
						 //保存重新分配之后的用车申请
						 vr=requestDao.saveAndFlush(vr);
						 if (vr!=null) {
							 allot.setVech(car);
							 //分配记录修改
							 allot= vallotDao.saveAndFlush(allot);
							 if (allot!=null) {
								 //发送消息
								 String title = username+"发送了一个车辆分配信息";
								 StringBuilder sb=new StringBuilder();
								 sb.append("用车分配：车牌号:")
								 .append(vr.getCarNo())
								 .append(";\n用车时间:")
								 .append(simpleDateFormat.format(PreStar))
								 .append("至")
								 .append(simpleDateFormat.format(PreEnd))
								 .append(";\n分配人:")
								 .append(username);
								 String  contents=sb.toString();
								 List<Long> ids=usersDao.finduserIdbyEmpId(vr.getEmp().getId());
								 logger.info(ids+"*********");
								 if (ids.size()>0) {
									 int fromto= msgUtil.sendMsg(title, contents, "Vechclerequest", vr.getId(),ids);
									 if(fromto>0) {
										 ro.setSuccess(true);
										 ro.setMsg("发送消息成功");  
									 }
								 }else {
									 ro.setSuccess(false);
									 ro.setMsg("操发送消息失败");
									 throw  new  BusinessRuntimeException(ResultCode.OPER_FAILED);
								 }
							}
							}else {
								ro.setMsg("未發送消息");
							}
					 }else { 
						  ro.setSuccess(false);
					      ro.setMsg("操作失败");
					      throw  new  BusinessRuntimeException(ResultCode.OPER_FAILED);};
				 }
				
			}
		}else {
			//无交集 相当于重新分配
			//分配天数
//			List<Date> allotDates= getDates(PreStar,PreEnd);
//			//用车申请天数
//			List<Date> requestDates= getDates(PreStart,rePreEnd);
			
			List<Date> use= Utils.getTwoDaysDay(simpleDateFormat.format(PreStar),simpleDateFormat.format(PreEnd));  
			List<VechclerequestDetails>  delis =  detailsDao.findNotUse(allotCarNo,use);
			if(delis!=null&&delis.size()>0) {
				 StringBuilder buf=new StringBuilder();
				 for (int i = 0,n=delis.size(); i < n; i++) {
					 buf.append("日期")
					  .append(simpleDateFormat.format((delis.get(i).getRequestTime())))
					 .append("\n")
					 .append("已申请，请重新选择日期")
					 ;
				 }
				 //查到了
				 //请选择新的日期
				 ro.setMsg(buf.toString());
				 ro.setSuccess(false);
			 }else {
				 //保存申请请求
				 //删除原有用车申请
				 List<VechclerequestDetails>  requestDetailList =  vr.getDetails();
				 for (VechclerequestDetails vechclerequestDetails : requestDetailList) {
					 detailsDao.deleteById(vechclerequestDetails.getId());
				}
				 vr.setDetails(null);
				 vr.setCarNo(allotCarNo);
				 VehicleInfo car=carDao.findByPlateNumber(allotCarNo);
					 if (car!=null) {
						 vr.setVehicleInfo(car);
					}
				 vr.setRequestStatus("MANAGED");//已分配
				 vr=requestDao.saveAndFlush(vr);
				  //生成新的用车申请
				 String username = UsersUtils.getCurrentHr().getEmpName();
				 if (vr!=null) {
					 //调用方法 生成申请详情表  日期由前台传进来
					 //获得其实终止日期
					 List<VechclerequestDetails>relist=new ArrayList<VechclerequestDetails>();
					 for (int i = 0,n=use.size(); i < n; i++) {
						 Date date=use.get(i);
						 VechclerequestDetails detail=new VechclerequestDetails();
						 detail.setRequsetState("MANAGED");//已分配
						 detail.setRequestTime(date);
						 detail.setRequestId(vr.getId());;
						 detail.setRequestDetail(vr);
						 relist.add(detail);
					 }
					 relist=  detailsDao.saveAll(relist);
					 vr.setDetails(relist);
					 vr.setPreEndDate(PreEnd);
					 vr.setPreStartDate(PreStar);
					 //保存重新分配之后的用车申请
					 vr=requestDao.saveAndFlush(vr);
					 if (vr!=null) {
						 allot.setVech(car);
						 //分配记录修改
						 allot= vallotDao.saveAndFlush(allot);
						 if (allot!=null) {
							 //发送消息
							 String title = username+"车辆分配信息已更改";
							 StringBuilder sb=new StringBuilder();
							 sb.append("用车分配：车牌号:")
							 .append(vr.getCarNo())
							 .append(";\n用车时间:")
							 .append(simpleDateFormat.format(PreStart))
							 .append("至")
							 .append(simpleDateFormat.format(rePreEnd))
							 .append(";\n分配人:")
							 .append(username);
							 String  contents=sb.toString();
							 List<Long> ids=usersDao.finduserIdbyEmpId(allot.getOperatorId());
							 logger.info(ids+"*********");
							 if (ids.size()>0) {
								 int fromto= msgUtil.sendMsg(title, contents, "Vechclerequest", vr.getId(),ids);
								 if(fromto>0) {
									 ro.setSuccess(true);
									 ro.setMsg("发送消息成功");  
								 }
							 }else {
								 ro.setSuccess(false);
								 ro.setMsg("操发送消息失败");
								 throw  new  BusinessRuntimeException(ResultCode.OPER_FAILED);
							 }
						}
						}else {
							ro.setMsg("未發送消息");
						}
				 }else { 
					  ro.setSuccess(false);
				      ro.setMsg("操作失败");
				      throw  new  BusinessRuntimeException(ResultCode.OPER_FAILED);};
			 }
		
//			查询新分配的是否可行 
//			可行的话删除原来的
//			新增现在的
		}
		return ro;
	}
	/**
	 * @param start
	 * @param end
	 * @return
	 */
	@Override
	@Transactional
	public ResultObject<VehicleAllot> refuse(CarAssignedModel assignedModel) throws BusinessRuntimeException {
		ResultObject<VehicleAllot> ro=new ResultObject<VehicleAllot>();
		Vechclerequest  req=requestDao.findById(assignedModel.getRequestId());
		 req.setRequestStatus("REFUSE");
		 List<VechclerequestDetails>     r=     req.getDetails();
		 
		   for (int i = 0,n=r.size(); i < n; i++) {
			   detailsDao.deleteById(r.get(i).getId());
		}		
		   r.removeAll(r);
		 StringBuilder sb=new StringBuilder();
		   sb.append(req.getNotes());
		   if (assignedModel.getNote()!=null&&assignedModel.getNote().trim().equals("")) {
			   sb.append(" 回绝原因:已拒绝；附加信息:")
			   .append(assignedModel.getNote());
		  }else {
			  sb.append("已拒绝");
		  }
		   req.setNotes(sb.toString());
		   req=requestDao.saveAndFlush(req);
		   
        if (req!=null) {
        	//修改相关申记录为已分配
              // 给申请人发送消息
         String username=req.getEmpName();
         String title = username+","+"您的申请已被拒绝";
         StringBuilder content=new StringBuilder();
         if (assignedModel.getNote()!=null&&assignedModel.equals("")) {
        	 content.append(req.getNotes());
		}else {
			content.append("已拒绝");
		}
           List<Long> ids=      usersDao.finduserIdbyEmpId( req.getEmp().getId());
         msgUtil.sendMsg(title, content.toString(), "Vechclerequest", req.getId(),ids);
         ro.setSuccess(true);
         ro.setMsg("发送成功");	
		}else {
			ro.setMsg("操作失败");
			ro.setSuccess(false);
		}
		return ro;
	
	}
	
	

}
