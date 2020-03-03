/**
 * 
 */
package com.zt.serviceImp;


import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import com.zt.common.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import org.springframework.stereotype.Service;

import com.zt.common.MessageUtil;
import com.zt.common.UsersUtils;
import com.zt.common.Utils;
import com.zt.constant.DepmentAndPosCode;
import com.zt.dao.DepartmentDao;
import com.zt.dao.EmployeeDao;
import com.zt.dao.MsgContentDao;
import com.zt.dao.PositionDao;
import com.zt.dao.SysMessageDao;
import com.zt.dao.UsersDao;
import com.zt.dao.VechclerequestDao;
import com.zt.dao.VechclerequestDetailsDao;
import com.zt.dao.VehicleInfoDao;
import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.model.VechclerequestModel;
import com.zt.po.Employee;
import com.zt.po.Vechclerequest;
import com.zt.po.VechclerequestDetails;
import com.zt.po.VehicleInfo;
import com.zt.service.VechclerequestService;




/**
 * @author yh
 * @date 2019年4月19日
 */
@Service("VechclerequestService")
//@CacheConfig(cacheNames = "zt_caches_Vechclerequest")   //缓存要添加到pom文件中
public class VechclerequestServiceImpl implements VechclerequestService{
	 private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		private Logger logger = LoggerFactory.getLogger(this.getClass());
		//接数据的model不装配在这里  直接用
	@Autowired
	VechclerequestDao vcrdaskd;
	@Autowired
	VechclerequestDetailsDao detailsDao;
	//根据这个可以获得车牌号
	@Autowired
	VehicleInfoDao carDao;
	@Autowired
	 MessageUtil msgutil;
	@Autowired
	MsgContentDao  msgctd;
	@Autowired
	DepartmentDao depDao;
	@Autowired
	PositionDao posDao;
	@Autowired
	EmployeeDao empDao;
	@Autowired
	UsersDao usersDao;
	@Autowired
	Message message;
	
	
	
	
	@Override
//	@Cacheable(key="'ask_'+#page+'_'+#size")
	public List<Vechclerequest> findByvehicleNumber(String vehicleNumber) {
		List<Vechclerequest> akd=findByvehicleNumber(vehicleNumber);
		return akd;
		
	}
	@Override
//	@CachePut(key="'ask_'+#page+'_'+#size")
	public boolean updatematterCycleById(String matterCycle, long id) {
		boolean flag=false;
		 int resutl=0;
		 try {
//			 resutl=vcrdaskd.updatematterCycleByvehicleNumber(matterCycle, id);
			 System.out.println(resutl+"********************");
		  flag=true;
		/*if (veh!=null) {
			 bl=true;
		}*/
		 } catch (Exception e) {
				e.printStackTrace();
			}
		return flag;
	}
	//这个方法
	@Override
//	@CachePut(key="'ask_'+#page+'_'+#size")
	@CacheEvict(allEntries=true)
		public ResultObject<Vechclerequest> saveVechclerequest(Vechclerequest asd)  {
			ResultObject<Vechclerequest> ro=new ResultObject<>();
			List<Date> use=null;
			//String carNo=asd.getVcif().getPlateNumber();
			//编辑申请单
			if(asd!=null) {
				String carNo=asd.getCarNo();
				//String carNo=asd.getVcif().getPlateNumber();
				//VehicleInfo carif =asd.getVcif();
				//carif.getPlateNumber()
			//if(carif!=null) {
				//开始日期
				Date start=asd.getPreStartDate();
				//结束日期
				Date end=asd.getPreEndDate();
				int da=end.compareTo(start);
				logger.info(da+"****************"+"mene");
				if(da>=0) {
					 use=Utils.getTwoDaysDay(simpleDateFormat.format(start),simpleDateFormat.format(end));
					//获得申请详情单列表
					 List<VechclerequestDetails>  delis =detailsDao.findNotUse(carNo,use);
					 if(delis!=null&&delis.size()>0) {
						 StringBuilder buf=new StringBuilder();
						 for (int i = 0,n=delis.size(); i < n; i++) {
							 buf.append(simpleDateFormat.format((delis.get(i).getRequestTime())))
							 .append("\n");
						 }
						 //查到了
						 //请选择新的日期
						 ro.setMsg("日期"+buf+"已申请，请重新选择日期");
						 ro.setSuccess(false);
						 //未查找同一辆车有重复的申请日期
					 }else {
						 //保存申请请求
						 asd.setEnabled(true);
						 asd.setRequestStatus("UN_MANAGED");
						 Employee emp=empDao.findById(UsersUtils.getCurrentHr().getEmpId());   
						 asd.setVehicleInfo(carDao.findByPlateNumber(asd.getCarNo()));
						 asd.setEmp(emp);
						 asd.setCreateDate(new Date());
						 asd.setEmpName(emp.getName());
						 asd.setOperatorId(emp.getId());
						 asd.setOperatorName(emp.getName());
						 List<VechclerequestDetails>relist=new ArrayList<VechclerequestDetails>();
						 asd.setDetails(relist);
						 asd=vcrdaskd.saveAndFlush(asd);
						 String username = UsersUtils.getCurrentHr().getEmpName();					
							 for (int i = 0,n=use.size(); i < n; i++) {
								 VechclerequestDetails detail=new VechclerequestDetails();
								 Date date=use.get(i);
								 detail.setRequsetState("CREATE");;
								 detail.setRequestTime(date);
								 detail.setRequestId(asd.getId());
								 detail.setRequestDetail(asd);
								 relist.add(detail);
							 }
							     asd.setDetails(relist);
								 Vechclerequest  carRequest=vcrdaskd.saveAndFlush(asd);
								 StringBuilder title = new StringBuilder();
								 title.append(username);
								 title.append("发送了一个车辆申请单");
								 StringBuilder contents = new StringBuilder();
								 contents.append("用车申请:车牌号:");
								 contents.append(carRequest.getCarNo());
								 contents.append(";\n用车时间:");
								 contents.append(simpleDateFormat.format(start));
								 contents.append("至");
								 contents.append(simpleDateFormat.format(end));
								 contents.append(";\n申请人:");
								 contents.append(username);
								 int fromto= msgutil.sendMsg(title.toString(), contents.toString(), "Vechclerequest", carRequest.getId(),message.UserIds());
								 if(fromto>0) {
								 	ro.setSuccess(true);
								 	ro.setMsg("发送消息成功");
								 }else{
									ro.setSuccess(false);
									ro.setMsg("发送消息失败");
								 }


				}}else {
					 asd.setEnabled(true);
					 asd.setRequestStatus("UN_MANAGED");
					 Employee emp=empDao.findById(UsersUtils.getCurrentHr().getEmpId());   
					 asd.setVehicleInfo(carDao.findByPlateNumber(asd.getVehicleInfo().getPlateNumber()));;
					 asd.setEmp(emp);
					 asd.setCreateDate(new Date());
					 asd.setEmpName(emp.getName());
					 asd.setOperatorId(emp.getId());
					 asd.setOperatorName(emp.getName());
					 List<VechclerequestDetails>relist=new ArrayList<VechclerequestDetails>();
					 asd.setDetails(relist);
					 asd=vcrdaskd.saveAndFlush(asd);
					 String username = UsersUtils.getCurrentHr().getEmpName();					
						 for (int i = 0,n=use.size(); i < n; i++) {
							 VechclerequestDetails detail=new VechclerequestDetails();
							 Date date=use.get(i);
							 detail.setRequsetState("CREATE");;
							 detail.setRequestTime(date);
							 detail.setRequestId(asd.getId());
							 detail.setRequestDetail(asd);
							 relist.add(detail);
							 
							 asd.setDetails(relist);
							 Vechclerequest  carRequest=vcrdaskd.saveAndFlush(asd);
							 StringBuilder title = new StringBuilder();
							 title.append(username);
							 title.append("发送了一个车辆申请单");
							 StringBuilder contents = new StringBuilder();
							 contents.append("用车申请:车牌号:");
							 contents.append(carRequest.getCarNo());
							 contents.append(";\n用车时间:");
							 contents.append(simpleDateFormat.format(start));
							 contents.append("至");
							 contents.append(simpleDateFormat.format(end));
							 contents.append(";\n申请人:");
							 contents.append(username);
							 int fromto= msgutil.sendMsg(title.toString(), contents.toString(), "Vechclerequest", carRequest.getId(),message.UserIds());
							 if(fromto>0) {
								 ro.setSuccess(true);
								 ro.setMsg("发送消息成功");
							 }else{
							 	ro.setSuccess(false);
							 	ro.setMsg("发送消息失败");
							 }

						 }
				}
			
			////此处属于新建
			}else {
				String carNo=asd.getCarNo();
				
				Date start=asd.getPreStartDate();
				//结束日期
				Date end=asd.getPreEndDate();
				int da=end.compareTo(start);
				logger.info(da+"****************"+"mene");
				if(da>=0) {
					 use=Utils.getTwoDaysDay(simpleDateFormat.format(start),simpleDateFormat.format(end));
					 //返回该车辆申请记录的清单列表
					 List<VechclerequestDetails>  delis =detailsDao.findNotUse(carNo,use);
					
					 if(delis!=null&&delis.size()>0) {
						 StringBuilder buf=new StringBuilder();
						 for (int i = 0,n=delis.size(); i < n; i++) {
							 buf.append(simpleDateFormat.format((delis.get(i).getRequestTime())))
							 .append("\n");
						 }
						 //查到了
						 //请选择新的日期
						 ro.setMsg("日期"+buf+"已申请，请重新选择日期");
						 ro.setSuccess(false);
						 //未查找同一辆车有重复的申请日期
					 }else {
						 //保存申请请求
						 asd.setEnabled(true);
						 asd.setRequestStatus("UN_MANAGED");
						 Employee emp=empDao.findById(UsersUtils.getCurrentHr().getEmpId());   
						 asd.setVehicleInfo(carDao.findByPlateNumber(asd.getVehicleInfo().getPlateNumber()));;
						 asd.setEmp(emp);
						 asd.setCreateDate(new Date());
						 asd.setEmpName(emp.getName());
						 asd.setOperatorId(emp.getId());
						 asd.setOperatorName(emp.getName());
						 List<VechclerequestDetails>relist=new ArrayList<VechclerequestDetails>();
						 asd.setDetails(relist);
						 asd=vcrdaskd.saveAndFlush(asd);
						 String username = UsersUtils.getCurrentHr().getEmpName();					
							 for (int i = 0,n=use.size(); i < n; i++) {
								 VechclerequestDetails detail=new VechclerequestDetails();
								 Date date=use.get(i);
								 detail.setRequsetState("CREATE");;
								 detail.setRequestTime(date);
								 detail.setRequestId(asd.getId());
								 detail.setRequestDetail(asd);
								 relist.add(detail);
							 }
							     asd.setDetails(relist);
								 Vechclerequest  carRequest=vcrdaskd.saveAndFlush(asd);
								 StringBuilder title = new StringBuilder();
								 title.append(username);
								 title.append("发送了一个车辆申请单");
								 StringBuilder contents = new StringBuilder();
								 contents.append("用车申请:车牌号:");
								 contents.append(carRequest.getCarNo());
								 contents.append(";\n用车时间:");
								 contents.append(simpleDateFormat.format(start));
								 contents.append("至");
								 contents.append(simpleDateFormat.format(end));
								 contents.append(";\n申请人:");
								 contents.append(username);
								 int fromto= msgutil.sendMsg(title.toString(), contents.toString(), "Vechclerequest", carRequest.getId(),message.UserIds());
								 if(fromto>0) {
									 ro.setSuccess(true);
									 ro.setMsg("发送消息成功");
								 }else{
								 	ro.setSuccess(false);
								 	ro.setMsg("发送消息失败");
								 }

				}}else {
					 asd.setEnabled(true);
					 asd.setRequestStatus("UN_MANAGED");
					 Employee emp=empDao.findById(UsersUtils.getCurrentHr().getEmpId());   
					 asd.setVehicleInfo(carDao.findByPlateNumber(asd.getVehicleInfo().getPlateNumber()));;
					 asd.setEmp(emp);
					 asd.setCreateDate(new Date());
					 asd.setEmpName(emp.getName());
					 asd.setOperatorId(emp.getId());
					 asd.setOperatorName(emp.getName());
					 List<VechclerequestDetails>relist=new ArrayList<VechclerequestDetails>();
					 asd.setDetails(relist);
					 asd=vcrdaskd.saveAndFlush(asd);
					 String username = UsersUtils.getCurrentHr().getEmpName();					
						 for (int i = 0,n=use.size(); i < n; i++) {
							 VechclerequestDetails detail=new VechclerequestDetails();
							 Date date=use.get(i);
							 detail.setRequsetState("CREATE");;
							 detail.setRequestTime(date);
							 detail.setRequestId(asd.getId());
							 detail.setRequestDetail(asd);
							 relist.add(detail);
							 
							 asd.setDetails(relist);
							 Vechclerequest  carRequest=vcrdaskd.saveAndFlush(asd);
							 StringBuilder title = new StringBuilder();
							 title.append(username);
							 title.append("发送了一个车辆申请单");
							 StringBuilder contents = new StringBuilder();
							 contents.append("用车申请:车牌号:");
							 contents.append(carRequest.getCarNo());
							 contents.append(";\n用车时间:");
							 contents.append(simpleDateFormat.format(start));
							 contents.append("至");
							 contents.append(simpleDateFormat.format(end));
							 contents.append(";\n申请人:");
							 contents.append(username);
							 int fromto= msgutil.sendMsg(title.toString(), contents.toString(), "Vechclerequest", carRequest.getId(),message.UserIds());
							 if(fromto>0) {
								 ro.setSuccess(true);
								 ro.setMsg("发送消息成功");
							 }else{
							 	ro.setSuccess(false);
							 	ro.setMsg("发送消息失败");
							 }

						 }
				}
				
			}
			
			  //Date start=asd.getPreStartDate();
			  //Date end=asd.getPreEndDate();
			  //int da=end.compareTo(start);
			  //logger.info(da+"****************"+"mene");
			  //if(da>=0){
				// use=Utils.getTwoDaysDay(simpleDateFormat.format(start),simpleDateFormat.format(end));
				 // [2019-5-23,2019-5-24]
				 //获取详情单列表
				 //
				 //VehicleInfo carif =asd.getVcif();
				 
				// List<VechclerequestDetails>  delis =detailsDao.findNotUse(carif.getPlateNumber(),use);
				 
				// if(delis!=null&&delis.size()>0) {
					// StringBuilder buf=new StringBuilder();
					// for (int i = 0,n=delis.size(); i < n; i++) {
					//	 buf.append(simpleDateFormat.format((delis.get(i).getRequestTime())))
					//	 .append("\n");
					// }
					 //查到了
					 //请选择新的日期
					 //ro.setMsg("日期"+buf+"已申请，请重新选择日期");
					// ro.setSuccess(false);
				 //}else {
					 //保存申请请求
					// asd.setEnabled(true);
					// asd.setRequestStatus("UN_MANAGED");
					// Employee emp=empDao.findById(UsersUtils.getCurrentHr().getEmpId());   
					// asd.setVcif(carDao.findByPlateNumber(asd.getVcif().getPlateNumber()));;
					// asd.setEmp(emp);
					// asd.setCreateDate(new Date());
					// asd.setEmpName(emp.getName());
					// asd.setOperatorId(emp.getId());
					// asd.setOperatorName(emp.getName());
					// List<VechclerequestDetails>relist=new ArrayList<VechclerequestDetails>();
					// asd.setDetails(relist);
					 //asd=vcrdaskd.saveAndFlush(asd);
					 // List<Long> ids=new ArrayList<Long>();
					 
					 //List<Long> idsss=ids.subList(0, ids.size()-1);
					 //String username = UsersUtils.getCurrentHr().getEmpName();
//					 if (ad!=null) {
						 //调用方法 生成申请详情表  日期由前台传进来
						 //获得其实终止日期
						
						// for (int i = 0,n=use.size(); i < n; i++) {
							// VechclerequestDetails detail=new VechclerequestDetails();
							// Date date=use.get(i);
							// detail.setRequsetState("CREATE");;
							// detail.setRequestTime(date);
							// detail.setRequestId(asd.getId());
							// detail.setRequestDetail(asd);
							// relist.add(detail);
						// }
						     //asd.setDetails(relist);
							// Vechclerequest  carRequest=vcrdaskd.saveAndFlush(asd);
//							 String title = username+"发送了一个车辆申请单";
							// StringBuilder title = new StringBuilder();
							 //title.append(username);
							// title.append("发送了一个车辆申请单");
//							 String  contents="用车申请：车牌号:"
//							                  +ad.getCarNo()
//							                  +";\n用车时间:"+ 
//							                  simpleDateFormat.format(start)+"至"+simpleDateFormat.format(end)
//							                  +";\n申请人:"
//							                  +username;
							// StringBuilder contents = new StringBuilder();
							 //contents.append("用车申请:车牌号:");
							// contents.append(carRequest.getCarNo());
							// contents.append(";\n用车时间:");
							 //contents.append(simpleDateFormat.format(start));
							 //contents.append("至");
							 //contents.append(simpleDateFormat.format(end));
							// contents.append(";\n申请人:");
							// contents.append(username);
							// List<Long> ids=receiveIds();
							// logger.info(ids+"*********");
							// if (ids.size()>0) {
								// int fromto= msgutil.sendMsg(title.toString(), contents.toString(), "Vechclerequest", carRequest.getId(),ids);
								// if(fromto>0) {
								//	 ro.setSuccess(true);
								//	 ro.setMsg("发送消息成功");  
								// }
							 //}else {
							//	 ro.setSuccess(false);
							//	 ro.setMsg("操发送消息失败");
							//	 throw  new  BusinessRuntimeException(ResultCode.OPER_FAILED);
							// }
							
//					 }else { 
//						 ro.setSuccess(false);
//					 ro.setMsg("操作失败");
//					 throw  new  BusinessRuntimeException(ResultCode.OPER_FAILED);};
				// }
			 // }else {
				 // ro.setSuccess(false);
				 // ro.setMsg("結束日期大于起始日期!请重新选择日期");
			 // }
			return ro;
			
			
			
	}

	
	@Override
//	@CachePut(key="'ask_'+#page+'_'+#size")
	public boolean update(Vechclerequest askds) {
		boolean flag = false;
		try {
			askds	= vcrdaskd.saveAndFlush(askds);
			if (null!=askds) {
				 flag = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	@Override
	@Cacheable(key="'ask_'+#page+'_'+#size")
	public ResultPage<Vechclerequest> findbyPage(Integer page, Integer size, String sort) {
		ResultPage<Vechclerequest> rp=new ResultPage<>();
		Direction sortt=null;
		if (sort.equals("asdfa")&&sort!=null) {
		 	sortt=   Sort.Direction.DESC;
		}else {
			sortt=   Sort.Direction.ASC;
		}
//		System.out.println("heneneneanna");
        Pageable pageable =PageRequest.of(page-1, size);//new PageRequest(page,size,sortt,"createDate");
	    Page<Vechclerequest> pages=vcrdaskd.findAll(pageable);
        int totals=(int) pages.getTotalElements();
        List<Vechclerequest> rolelist= pages.getContent();
	     rp.setData(rolelist);
	     rp.setTotal(totals);
	     rp.setSuccess(true);
		return rp;
	}
	@Override
	@Cacheable(key="'ask_'+#page+'_'+#size")
	public Page<Vechclerequest> findSearch(String vehicleNumber, Pageable pageable) {
		Page<Vechclerequest> VechclerequestPage =vcrdaskd.findSearch(vehicleNumber, pageable);
        return VechclerequestPage;
	}
	@Override
//	@Cacheable(key="'vrqt_'+#page+'_'+#size")
	public ResultPage<Vechclerequest> findVechclerequest(String queryName, int page, int size) {
		ResultPage<Vechclerequest> ro=new ResultPage<Vechclerequest>();
		PageRequest pageable = PageRequest.of(page-1,size);
		Page<Vechclerequest> pages =vcrdaskd.findbypage(queryName, pageable);
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
	//申请请求
	/*
	@Override
	@CacheEvict(allEntries=true)
	public ResultObject<Vechclerequest> addVechclerequest(Vechclerequest asd){
		ResultObject<Vechclerequest> ro=new ResultObject<>();
		if(Long.valueOf(asd.getId())==null||asd.getId()==0) {
			asd.setCreateDate(new Date());
		}				
		asd.setEnabled(true);
		asd.setRequestStatus("UN_MANAGED");//暂且有疑问
		//保存申请单
		Vechclerequest rq=askd.saveAndFlush(asd);
		
		 if(rq!=null) {
			   ro.setSuccess(true);
				ro.setMsg("操作成功");;
		   }else {
			   ro.setSuccess(false);
			   ro.setMsg("操作失败");
			throw   new BusinessRuntimeException(ResultCode.OPER_FAILED);
		   }
		
		return ro;
	}
	*/
	@Override
	@CacheEvict(allEntries=true)
	public ResultObject<Vechclerequest> deletVechclerequest(long id)  {
		ResultObject<Vechclerequest> ro=new ResultObject<>(); 
		Vechclerequest request=vcrdaskd.findById(id);
		 if (request!=null) {
			 List<VechclerequestDetails> detailist =request.getDetails();
			 request.setDetails(null);
			 request.setEnabled(false);
			 vcrdaskd.saveAndFlush(request);
			 for (VechclerequestDetails detail : detailist) {
				 detailsDao.deleteById(detail.getId());
			}
//			发送撤销消息
			 msgutil.cancelMsg("Vechclerequest", request.getId());
			 
			ro.setSuccess(true);
			ro.setMsg("操作成功");
		}else {
			ro.setSuccess(false);
			ro.setMsg("操作失败");
		throw	new BusinessRuntimeException(ResultCode.OPER_FAILED);
		}  
		return ro;
	}
	//15.23 4/28
	@Override
	public ResultObject<VehicleInfo> findVehicleInfos() throws BusinessRuntimeException {
		
		ResultObject<VehicleInfo> ro=new ResultObject<>(); 
		List<VehicleInfo> listvf=carDao.findUnused();
		if(listvf.isEmpty()==true) {
			ro.setSuccess(false);
			ro.setMsg("操作失败");
			throw	new BusinessRuntimeException(ResultCode.OPER_FAILED);
		}else {
			ro.setData(listvf);
			
		}
			return  ro;
	}
	@Override
	public ResultObject<Vechclerequest> addVechclerequest(Vechclerequest asd) throws BusinessRuntimeException {
		ResultObject<Vechclerequest> ro=new ResultObject<>();
		
		//没有任何申请单  直接设置申请
		if(Long.valueOf(asd.getId())==null){
			asd.setCreateDate(new Date());
			//获取页面设置的值
			//asd.setPreStartDate(preStartDate);
			//获取页面设置的值
			asd.setEnabled(true);
			asd.setRequestStatus("UN_MANAGED"); 
		}
			String cn=asd.getCarNo();
			VehicleInfo car=  carDao.findByPlateNumber(cn);
			asd.setVehicleInfo(car);
			
			Vechclerequest rq=vcrdaskd.saveAndFlush(asd);	
			if (rq!=null) {
				
				ro.setSuccess(true);
				ro.setMsg("操作成功");;
			} else {
				
				ro.setSuccess(false);
				ro.setMsg("操作失败");
				throw   new BusinessRuntimeException(ResultCode.OPER_FAILED);	
			}
			return ro;
		}
		
			  
		   
			  
		  
	@Override
	public ResultObject<Vechclerequest> save(VechclerequestModel vcrqm) throws BusinessRuntimeException {
			ResultObject<Vechclerequest> ro=new ResultObject<>();
			
			//JSONArray array=JSONArray.fromObject(vcrqm.getStat());
			//JSONArray array1=JSONArray.fromObject(vcrqm.getEnd());
		return null;
	}
	
	public ResultObject<Vechclerequest> findVechclerequest(String carNo){
		ResultObject<Vechclerequest> ro=new ResultObject<>();
		List<Vechclerequest> akd=findByvehicleNumber(carNo);
		     
		return ro;
		
	}
	
}

