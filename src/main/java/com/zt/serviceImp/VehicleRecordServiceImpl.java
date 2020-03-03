/**
 * 
 */
package com.zt.serviceImp;




import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.zt.dao.MsgContentDao;
import com.zt.dao.UsersDao;
import com.zt.dao.VechclerequestDao;
import com.zt.dao.VehicleAllotDao;
import com.zt.dao.VehicleInfoDao;
import com.zt.dao.VehicleMaintenanceDao;
import com.zt.dao.VehicleRecordDao;
import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.model.VehicleRecordModel;

import com.zt.po.Vechclerequest;
import com.zt.po.VehicleAllot;
import com.zt.po.VehicleInfo;

import com.zt.po.VehicleRecord;
import com.zt.service.VehicleRecordService;
  //根据前台返回内容可确定走的哪个方法   需要更改
/**
 * @author yh
 * @date 2019年4月19日
 */
@Service("vehicleRecordService")
@CacheConfig(cacheNames = "zt_caches_vehiclerecord")
public class VehicleRecordServiceImpl implements VehicleRecordService{
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	@Autowired
	private VehicleRecordDao vRecordDao;
	@Autowired
	private VehicleInfoDao carDao;
	@Autowired
	private VehicleMaintenanceDao  carmaintenceDao;
	@Autowired
	private VechclerequestDao carrequest;
	@Autowired
	private VehicleAllotDao  carallowDao;
	@Autowired
	private UsersDao usersDao;
	@Autowired
	 MessageUtil msgutil;
	@Autowired
	MsgContentDao  msgctd;
	
	@Override
	@Cacheable(key="'vehr_'+#vehicleNumber")
	public List<VehicleRecord> findByvehicleNumber(String vehicleNumber) {
	List<VehicleRecord> vd	=vRecordDao.findByvehicleNumber(vehicleNumber);
	return  vd;
		
	}

	@Override
	@CachePut(key="'vehr_'+#vehicleNumber")
	public boolean updateById(String licensePlateNumber, long id) {
		 boolean flag=false;
		 int resutl=0;
		 try {
			 resutl=vRecordDao.updateById(licensePlateNumber, id);
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

	

	@Override
	@Cacheable(key="'vehr_'+#vehicleNumber")
	public ResultPage<VehicleRecord> findbyPage(Integer page, Integer size, String sort) {
		ResultPage<VehicleRecord> rp=new ResultPage<>();
		Direction sortt=null;
		if (sort.equals("asdfa")&&sort!=null) {
		 	sortt=   Sort.Direction.DESC;
		}else {
			sortt=   Sort.Direction.ASC;
		}
		
		
        Pageable pageable =  PageRequest.of(page-1, size);//new PageRequest(page,size,sortt,"createDate");
	    Page<VehicleRecord> pages=vRecordDao.findAll(pageable);
        int totals=   (int) pages.getTotalElements();
        List<VehicleRecord> rolelist= pages.getContent();
	     rp.setData(rolelist);
	     rp.setTotal(totals);
	     rp.setSuccess(true);
		return rp;
	}

	@Override
	@Cacheable(key="'vehr_'+#vehicleNumber")
	public Page<VehicleRecord> findSearch(String vehicleNumber, Pageable pageable) {
		Page<VehicleRecord> vehiclred = vRecordDao.findAll(pageable);
        return vehiclred;
	}
  //6.4 6.5
	@Override
	@CachePut(key="'vehr_'+#vehicleNumber")
	public ResultObject<VehicleRecord> saveVehicleRecord(VehicleRecord carrecord) {
		ResultObject<VehicleRecord> ro=new ResultObject<>();
		
		if(Long.valueOf(carrecord.getId())==null) {
			//model是和前台对应的   传model可以写model。
			//车牌号
			
			//实际事由
			
			//出发日期
			
			//终点里程数
			//carrecordmodel.getEndMileage();
			//结束日期
			//carrecordmodel.getEndDate();
			//初始里程数
			//carrecordmodel.getStartMileage();
			//出发事由
			
			//备注
			//carrecordmodel.getNote();
			
			
			String  carNb=carrecord.getCarNo();
		float end=carrecord.getEndMileage();
		float  start=carrecord.getStartMileage();
			//
		float carnextyuyue=carmaintenceDao.findMaintenances(carNb);
		if(carnextyuyue!=0||(String.valueOf(carnextyuyue))!=null) {
		if(start==carnextyuyue||Math.abs(carnextyuyue-start)<=50f||end==carnextyuyue||Math.abs(carnextyuyue-end)<=50f) {
			
			//车牌号
			carrecord.setCarNo(carNb);
			//实际事由
			//carrecord.setEndCause(carrecordmodel.getEndCause());
			//结束日期
			//carrecordmodel.setEndDate(carrecordmodel.getEndDate());
			//结束里程
			//carrecordmodel.setEndMileage(carrecordmodel.getEndMileage());
			//备注
			//carrecordmodel.setNote(carrecordmodel.getNote());
			//初始里程
			//carrecordmodel.setStartMileage(carrecordmodel.getStartMileage());
			//开始日期
			//carrecordmodel.setOutDate(carrecordmodel.getOutDate());
			//出发事由
			//carrecordmodel.setStartCause(carrecordmodel.getStartCause());
			
			 carrecord.setStartDate(new Date());
			carrecord.setEndDate(new Date());
			//carrecord.setOutDate(new Date());
			vRecordDao.save(carrecord);
			ro.setSuccess(true);
			ro.setMsg(carNb+"已经临近保养");
		}
			
		else {
			//车牌号
			carrecord.setCarNo(carNb);
			//实际事由
			//carrecord.setEndCause(carrecordmodel.getEndCause());
			//结束日期
			//everd.setEndDate(carrecordmodel.getEndDate());
			//结束里程
			//everd.setEndMileage(carrecordmodel.getEndMileage());
			//备注
			//everd.setNote(carrecordmodel.getNote());
			//初始里程
			//everd.setStartMileage(carrecordmodel.getStartMileage());
			//开始日期
			//everd.setOutDate(carrecordmodel.getOutDate());
			//出发事由
			//everd.setStartCause(carrecordmodel.getStartCause());
			vRecordDao.save(carrecord);
			ro.setSuccess(true);
			ro.setMsg("操作成功");
			
		}}else {
			//获得初始里程数
			float carstartmileage=carrecord.getStartMileage();
			float  carendtmileage=carrecord.getEndMileage();
			//车牌号
			carrecord.setCarNo(carNb);
			//实际事由
			//everd.setEndCause(carrecordmodel.getEndCause());
			//结束日期
			//everd.setEndDate(carrecordmodel.getEndDate());
			//结束里程
			//everd.setEndMileage(carrecordmodel.getEndMileage());
			//备注
			//everd.setNote(carrecordmodel.getNote());
			//初始里程
			//everd.setStartMileage(carrecordmodel.getStartMileage());
			//开始日期
			//everd.setOutDate(carrecordmodel.getOutDate());
			//出发事由
			//everd.setStartCause(carrecordmodel.getStartCause());
			vRecordDao.save(carrecord);
			ro.setSuccess(true);
			
			ro.setMsg("操作成功");
			
		}
		
		//查询分配已完成后  该车辆以前的行车记录单   获得最近的行车记录单
		//如果这个参数是空的 则证明它属于存储
		//long recordId=everd.getId();
		//下面条件成立则说明属于新建行车记录表   查询上一个对象是否建立  建立的话 传入部分参数 建立此对象  此对象数据分为两部分 一部分 数据库获取 另一部分  页面添加 为空则表示关系没建立
		//List<VehicleAllot> carAllot=carallowDao.findAll();
		//车辆  人  出发时间
		/*List<Vechclerequest> carrequests=carrequest.findAll();
		for(int i=0;i<carrequests.size();i++) {
			Vechclerequest carquest	=carrequests.get(i);
					String carNo=carquest.getCarNo();
					String  empName=carquest.getEmpName();
					Date preStartDate=carquest.getPreStartDate();
					Date preEndDate=carquest.getPreEndDate();
		VehicleAllot  carbyfenpei =carallowDao.findByCarNoAndEmpNameAndPreStartDateAndPreEndDate(carNo, empName, preStartDate, preEndDate);
		*/
		//VehicleMaintenance  carmaintence=carmaintenceDao.findByusernameAndcarNoAndcreateDate(username, carNo, createDate);
		//分配单存在时
		//if(carbyfenpei!=null ) {
			//根据车牌号和此车牌号的最后一条维护记录做对比 是否需要更改申请单
			//保存填写行车记录单时
			//List<VehicleMaintenance>  carmaintence=carmaintenceDao.findAll();
			//根据车牌号查找到该车的保养记录  有 或者一条或者多条 无 则可以随便创建 
			//根据车牌号
			//List<VehicleMaintenance>  carmaintence=carmaintenceDao.findBycarNo(carNo);
			//根据车辆返回该车辆下一次预约里程
			//float now =carmaintenceDao.findvehicleMaintenances(carNo);
			//VehicleRecordModel carrecord  =new VehicleRecordModel();
			//carrecord.getCarApplication();
			//carrecord.getDistributeDate();
			//carrecord.getCreateDate();
			//carrecord.getEndDate();
			//carrecord.getEndCause();
			//carrecord.getEndMileage();
			//carrecord.getNote();
			//carrecord.getStartCause();
			//carrecord.getStartDate();
			//carrecord.getStartMileage();
			//carrecord.getTravellingPeople();
			//有的情况查询，没得情况不管   因为建立不建立和这个没关系
			//if(carmaintence!=null) {
				//后台根据行车记录分配的车牌号
				
				/*for(int t=0;i<carmaintence.size();i++) {
					VehicleMaintenance carmaintenance=carmaintence.get(i);
					//当前里程数
					float nowMileage=carmaintenance.getNowMileage();
					 
				}
				*/
				
			//}
			//获得页面初始行程数
			//float startMileage=carrecord.getStartMileage();
			//float endmileange =carrecord.getEndMileage();
			/*if(startMileage==now||Math.abs(now-startMileage)<=50f||endmileange==now||Math.abs(now-endmileange)<=50f) {
				//调用工具类
				ro.setSuccess(false);
				ro.setMsg("这个车临近保养");
				
			}else {
				 
				everd.setCarNo(carNo);
				everd.setCreateDate(new Date());
				//从前台获得部分数据
				
				//everd.   可以设置属性
				vRecordDao.save(everd);
				//更改车辆状态为已完成
				ro.setSuccess(true);
				ro.setMsg("记录填写成功");
			}
			
			
		}else {
			ro.setSuccess(false);
			ro.setMsg("你的车辆请求未分配");
			
		}
		}
		
		/*if(!carAllot.isEmpty()) {
			for(int i=0;i<carAllot.size();i++) {
				System.out.println(carAllot.get(i));
				VehicleAllot finishcarllot=carAllot.get(i);//获得分配完成的对象
				//可获得部分数据  共同数据   设置进行车记录表
				//建立一个行车记录model  从前台获得部分数据  此处是新增  不考虑从数据库拿数据
				//everd.setCreateDate(createDate);
				//everd.setDistributeDate(distributeDate);
				//everd.setEnabled(enabled);
				//everd.setEndCause(endCause);
				//everd.setEndDate(endDate);
				//everd.setEndMileage(endMileage);
				if(Long.valueOf(recordId)==null) {
					//获得已经详情已经分配的单子    提取此单子的部分参数  保存进这个表部分 其余填写
					
					String event="";
					everd.setEndCause(event);
					
					
				}//属于修改行车记录表
				else {
					
					
				}
				
			}
		}*/
		
		//从哪里获取  通过什么方法？
		
		//获得所有已分配的车辆请求信息
		//新建成功的标记  获得
		//String carchepai=everd.getCarNo();
		//Date  createrecord=everd.getCreateDate();
		//行驶的人
		//String  driver=everd.getTravellingPeople();
		//下面这句先放这里
		//VehicleRecord carrecord=vRecordDao.findBycarNoAndtravellingPeopleAndcreateDate(carchepai, travellingPeople, createDate);
		
		//List<Vechclerequest> v=carrequest.findByduration("已分配");
		//  获取固定车  固定人  某一天的行车记录
		//根据出行记录 获得申请单子
		//Vechclerequest request=everd.getCarApplication();
		//获得已经分配的车
		//String  carByallot=request.getCarNo();
		//carmaintenceDao.findByCarNo(carByallot);
		 // if(request!=null&&request.getDuration().equals("MANAGED")) {
		
			 // VehicleRecord vcrd= vRecordDao.saveAndFlush(everd); 
			  //ro.setSuccess(true);
				//ro.setMsg("操作成功");
		 // }	
		//else {
			//ro.setSuccess(false);
			//ro.setMsg("操作失败");
		//}	
	}else {
		//实际事由
		//everd.setEndCause(carrecordmodel.getEndCause());
		//结束日期
		//everd.setEndDate(carrecordmodel.getEndDate());
		//结束里程
		//everd.setEndMileage(carrecordmodel.getEndMileage());
		//备注
		//everd.setNote(carrecordmodel.getNote());
		//初始里程
		//everd.setStartMileage(carrecordmodel.getStartMileage());
		//开始日期
		//everd.setOutDate(carrecordmodel.getOutDate());
		//出发事由
		//everd.setStartCause(carrecordmodel.getStartCause());
		//long  recordid=carrecord.getId();
		
		vRecordDao.saveAndFlush(carrecord);
		
	}
		return ro;
	}
	@Override
	public ResultPage<VehicleRecord> findBylicensePlateNumber(String licensePlateNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultObject<VehicleRecord> saveVehicleRecord(Date complate) throws BusinessRuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Cacheable(key="'vddd_'+#page+'_'+#size")
	public ResultPage<VehicleRecord> findbypage(String queryName, int page, int size) throws BusinessRuntimeException {
		ResultPage<VehicleRecord> ro=new ResultPage<VehicleRecord>();
		Pageable pageable = PageRequest.of(page-1,size);
		Page<VehicleRecord> pages =vRecordDao.findSearch(queryName, pageable);
		
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

	@Override
	@CacheEvict(allEntries=true)
	public ResultObject<VehicleRecord> addRecord(VehicleRecord vrd){
		ResultObject<VehicleRecord> ro=new ResultObject<>();
		//判断此处是新建
		
		if(Long.valueOf(vrd.getId())==null||vrd.getId()==0){
			//vrd.setCreateDate(new Date());
			String  carNb=vrd.getCarNo();
			//获得页面的东西
			Float end=vrd.getEndMileage();
			Float  start=vrd.getStartMileage();
			//获得页面日期
			Date starttime1=vrd.getStartDate();
			Date endtime2=vrd.getEndDate();
			//判断非空或者不为0    可以获得记录
		Float carnextyuyue=carmaintenceDao.findMaintenances(carNb);
		
		//这些都不为零并且车辆维护记录存在记录并且不为零
		if(end!=null&&end!=0||start!=null&&start!=0&&carnextyuyue!=null&&carnextyuyue.floatValue()!=0) {
			//行车记录经过查询计算后发信息
			//比较值 终止》初始里程
			//前面  加那些条件 比如 结束时间  初始时间   初始里程  结束里程   先判断数据设置是否成功  ，在对比法消息
		if(start==carnextyuyue||Math.abs(carnextyuyue-start)<=50||end==carnextyuyue||Math.abs(carnextyuyue-end)<=50){
			//车牌号
			//vrd.setCarNo(carNb);
			//实际事由
			//carrecord.setEndCause(carrecordmodel.getEndCause());
			//结束日期
			//carrecordmodel.setEndDate(carrecordmodel.getEndDate());
			//结束里程
			//carrecordmodel.setEndMileage(carrecordmodel.getEndMileage());
			//备注
			//carrecordmodel.setNote(carrecordmodel.getNote());
			//初始里程
			//carrecordmodel.setStartMileage(carrecordmodel.getStartMileage());
			//开始日期
			//carrecordmodel.setOutDate(carrecordmodel.getOutDate());
			//出发事由
			//carrecordmodel.setStartCause(carrecordmodel.getStartCause());
			//vrd.setStartDate(new Date());
			//vrd.setEndDate(new Date());
			//vrd.setOutDate(new Date());
			//Date starttime=vrd.getStartDate();
			//Date endtime=vrd.getEndDate();
			//&&starttime1.compareTo(endtime2)<0
			if(starttime1.before(endtime2)==true) {
				vrd.setCreateDate(new Date());
				vrd.setCarNo(carNb);
				vrd.setEndDate(endtime2);
				vrd.setStartDate(starttime1);
				vRecordDao.save(vrd);
				ro.setSuccess(true);
				ro.setMsg(carNb+"已经临近保养");
			}else {
				ro.setSuccess(false);
				ro.setMsg("返回时间不能早于出发时间");
			}
			
		}
	
		else if(carnextyuyue==0||(String.valueOf(carnextyuyue))==null){
		//走的是这里
		//车牌号
		vrd.setCarNo(carNb);
		vRecordDao.save(vrd);
		ro.setSuccess(true);
		ro.setMsg("操作成功");
	}else if(end==null||end==0||start==null||start==0) {
	vrd.setCarNo(carNb);
	vRecordDao.saveAndFlush(vrd);
	ro.setSuccess(true);
	ro.setMsg("另类操作成功");
	//下面属于修改
	}else {
		ro.setSuccess(true);
		ro.setMsg("保存失败");
	}
	}
		
		if(starttime1.before(endtime2)==true&&start.compareTo(end)<0==true) {
			float biaoji=start.floatValue()-carnextyuyue.floatValue();
			
					//
			boolean flag1=Math.abs(carnextyuyue.floatValue()-start.floatValue())<=50.0000;
			float biaoji2=end.floatValue()-carnextyuyue.floatValue();
			boolean flag3=Math.abs(carnextyuyue.floatValue()-end.floatValue())<=50.0000;
			if(biaoji==0.0000||flag1==true||biaoji2==0.0000||flag3==true) {
				vrd.setCreateDate(new Date());
				vrd.setCarNo(carNb);
				vrd.setEndDate(endtime2);
				vrd.setStartDate(starttime1);
				vRecordDao.save(vrd);
				ro.setSuccess(true);
				ro.setMsg(carNb+"已经临近保养");
				VehicleInfo carif=carDao.findByPlateNumber(carNb);
//				carif.setCarStatus("已完成");
			}else {
			vrd.setCreateDate(new Date());
			vrd.setCarNo(carNb);
			vrd.setEndDate(endtime2);
			vrd.setStartDate(starttime1);
			vRecordDao.save(vrd);
			ro.setSuccess(true);
			ro.setMsg("保存成功");
			}
		}else if(endtime2.before(starttime1)){
			ro.setSuccess(false);
			ro.setMsg("回来时间不能在出发时间之前");
		}else if(start.compareTo(end)>0==true) {
			ro.setSuccess(false);
			ro.setMsg("初始公里数不能大于终试公里数");	
		}
		//vrd.setCreateDate(new Date());
		//vRecordDao.saveAndFlush(vrd);
		//ro.setSuccess(true);
		//ro.setMsg("操作成功了");
		
		
		// 修改状态   修改行车记录表
		}else {
			vrd.getCarNo();
			vrd.getEndMileage();
			vrd.getEndDate();
			
		}
		return ro;
	}
	@Override
	@CacheEvict(allEntries=true)
	public ResultObject<VehicleRecord> deletRecord(long id) throws BusinessRuntimeException {
		ResultObject<VehicleRecord> ro=new ResultObject<>(); 
		VehicleRecord vhid=vRecordDao.findById(id);
		 if (vhid!=null) {
			 vhid.setEnabled(false);
			 vRecordDao.saveAndFlush(vhid);
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
	public ResultObject<Object> findBaseData()  {
		ResultObject<Object> ro=new ResultObject<>();
		Map<String, Object> map=new HashMap<String, Object>();
		
		List<VehicleInfo> carlist=  carDao.findAllcars();
		if (carlist==null) {
			ro.setSuccess(false);
		   throw	new BusinessRuntimeException(ResultCode.OPER_FAILED);
		}else {
			map.put("cars", carlist);
			ro.setRoot(map);
			ro.setSuccess(true);
		}
		
		return ro;
	}

}
	
		

