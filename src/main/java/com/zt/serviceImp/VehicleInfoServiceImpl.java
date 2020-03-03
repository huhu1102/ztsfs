/**
 * 
 */
package com.zt.serviceImp;



import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.zt.common.PageUtil;
import com.zt.dao.VechclerequestDao;
import com.zt.dao.VechclerequestDetailsDao;
import com.zt.dao.VehicleInfoDao;
import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.model.VechcleInfoModel;
import com.zt.po.Employee;
import com.zt.po.Vechclerequest;
import com.zt.po.VehicleInfo;
import com.zt.service.VehicleInfoService;

/**
 * @author yh
 * @date 2019年4月12日
 */
@Service("vehicleInfoService")
@CacheConfig(cacheNames = "zt_caches_vehicleInfo")
public class VehicleInfoServiceImpl implements VehicleInfoService{

	@Autowired
	private VehicleInfoDao vid;
	@Override
	@Cacheable(key="'veh_'+#Status")
	//根据车辆状态查找车辆信息
	public List<VehicleInfo> findByStatus(String Status) {
		List<VehicleInfo>list=vid.findByCarStatus(Status);
		return list;
		
	}
	@Override
	@CachePut(key="'veh_'+#Status")
	public boolean updateVehicleInfo(String carNo,long id) {
		 boolean flag=false;
		 int resutl=0;
		 try {
			 resutl=vid.updateById(carNo, id);
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
	@CacheEvict(key="'veh_'+#Status")
	public boolean deleteVehicleInfo(long id) {
		boolean flag=false;
		int result3=0;
		
		try {
			result3= vid.deleteById(id);
			 System.out.println(result3+"********************");
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
	@CachePut(key="'veh_'+#Status")
	public boolean updateVehicleInfoms(VehicleInfo vci) {
		
		boolean flag = false;
		try {
			vci	= vid.saveAndFlush(vci);
			if (null!=vci) {
				 flag = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	@Override
	@CachePut(key="'veh_'+#Status")
	public ResultObject<VehicleInfo> saveEmpoyee(VehicleInfo vci) {
		ResultObject<VehicleInfo> ro=new ResultObject<>();
		VechcleInfoModel carmodel=null; 
		 Date bydate=carmodel.getBuyedDate();
		 
		VehicleInfo vcf= vid.saveAndFlush(vci);
		if (vcf!=null) {
			ro.setSuccess(true);
			ro.setMsg("操作成功");;
		}else {
			ro.setSuccess(false);
			ro.setMsg("操作失败");
		}
		return ro;
	}

	@Override
	public ResultObject<VehicleInfo> saveVehicleInfo(VehicleInfo vci) throws BusinessRuntimeException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ResultObject<VehicleInfo> findVehicleInfo(String licensePlateNumber) throws BusinessRuntimeException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ResultObject<VehicleInfo> findByBStatus(String Status) throws BusinessRuntimeException {
		// TODO Auto-generated method stub
		return null;
	}
	//分页有条件查询
	@Override
//	@Cacheable(key="'vclf_'+#page+'_'+#size")
	public ResultPage<VehicleInfo> findSearch(String queryName, int page, int size){
		ResultPage<VehicleInfo> ro=new ResultPage<VehicleInfo>();
		Pageable pageable = PageRequest.of(page-1,size);
//		Page<VehicleInfo> pages =vid.findSearch(queryName, pageable);
		List<VehicleInfo> pagelist=vid.findAllByPage(queryName, (page-1)*size, size);
		int count=vid.countAllData(queryName);
		 if (pagelist!=null) {
//	        	int totals=(int) pages.getTotalElements();
	        	 ro.setData(pagelist);
	    	     ro.setTotal(count);
	    	     ro.setTotalPages(PageUtil.getTotalPages(count, size));
	    	     ro.setSuccess(true);
			} else {
				ro.setSuccess(false);
				throw  new BusinessRuntimeException(ResultCode.UNKNOWN_ERROR);
			}
		
		return ro;
		
	}
	
	//增加或修改车辆基础信息
	@Override
	@CacheEvict(allEntries=true)//清除缓存
	public ResultObject<VehicleInfo> saveVehicleInfos(VehicleInfo vhcf)  {
		ResultObject<VehicleInfo> ro=new ResultObject<>();
		//新建
		if(Long.valueOf(vhcf.getId())==null||vhcf.getId()==0) {
//			VechcleInfoModel carinfomodel=new VechcleInfoModel();
//			vhcf.setBuyedDate(carinfomodel.getBuyedDate());
//			vhcf.setManufacturer(carinfomodel.getManufacturer());
//			vhcf.setPlateNumber(carinfomodel.getPlateNumber());
//			vhcf.setVehicleType(carinfomodel.getVehicleType());
			vhcf.setEnabled(true);
			vhcf.setCreateDate(new Date());
			vhcf.setCarStatus(1);//车辆状态为空闲
			vid.save(vhcf);
			ro.setSuccess(true);
			ro.setMsg("保存成功");
			//修改
		}else {
			vhcf.setEnabled(true);
			vhcf.setCarStatus(1);//车辆状态为空闲
			
			vid.saveAndFlush(vhcf);
			
			
			ro.setSuccess(true);
			ro.setMsg("更新成功");
	
		}
		//id非空属于修改
		//vhcf.setEnabled(true);
		//VehicleInfo carmsg=vid.saveAndFlush(vhcf);
		/*
		 if(carmsg!=null) {
			   ro.setSuccess(true);
				ro.setMsg("操作成功");;
		   }else {
			   ro.setSuccess(false);
			   ro.setMsg("操作失败");
			throw   new BusinessRuntimeException(ResultCode.OPER_FAILED);
		   }
		   */
		
		return ro;
	}
	//删除车辆信息根据id
	@Override
	@CacheEvict(allEntries=true)//清除缓存
	public ResultObject<VehicleInfo> deletVehicleInfo(long id) throws BusinessRuntimeException {
		ResultObject<VehicleInfo> ro=new ResultObject<>(); 
		VehicleInfo carinfo=vid.findById(id);
		 if (carinfo!=null) {
			 carinfo.setEnabled(false);
			vid.saveAndFlush(carinfo);
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
	public List<VehicleInfo> findByplateNumber(String plateNumber) {
		
			List<VehicleInfo>list=vid.findByCarStatus(plateNumber);
			return list;
			
		}

	
	}
	
	
	


	
	
	
	
	

