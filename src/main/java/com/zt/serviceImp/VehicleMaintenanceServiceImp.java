/**
 * 
 */
package com.zt.serviceImp;


import java.text.SimpleDateFormat;

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
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.zt.dao.VehicleInfoDao;
import com.zt.dao.VehicleMaintenanceDao;
import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;


import com.zt.po.VehicleMaintenance;
import com.zt.service.VehicleMaintenanceService;

/**
 * @author yh
 * @date 2019年4月19日
 */
@Service("vehicleMaintenanceService")
@CacheConfig(cacheNames = "zt_caches_vehiclemaintenance")
public class VehicleMaintenanceServiceImp implements VehicleMaintenanceService{
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	@Autowired
	private VehicleMaintenanceDao vrepairDao;
	@Autowired
	private VehicleInfoDao carDao;
	
	@Override
	@Cacheable(key="'vehm_'+#vehicleNumber")
	public List<VehicleMaintenance> findByvehicleNumber(String vehicleNumber) {
		List<VehicleMaintenance>vmn=vrepairDao.findByvehicleNumber(vehicleNumber);
		return vmn;
	}
	@Override
	@CachePut(key="'vehm_'+#vehicleNumber")
	public boolean updateByvehicleNumber(String drivingkilometres, String bookingMileage, String vehicleNumber) {
		boolean flag=false;
		 int resutl=0;
		 try {
			 resutl=vrepairDao.updateByvehicleNumber(drivingkilometres, bookingMileage, vehicleNumber);
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
	@CachePut(key="'vehm_'+#vehicleNumber")
	public boolean updatevehicleMaintenance(VehicleMaintenance vmt) {
		boolean flag = false;
		try {
			 vmt	= vrepairDao.saveAndFlush(vmt);
			if (null!=vmt) {
				 flag = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	@Override
	@Cacheable(key="'vehm_'+#vehicleNumber")
	public ResultPage<VehicleMaintenance> findbyPage(Integer page, Integer size, String sort) {
		ResultPage<VehicleMaintenance> rp=new ResultPage<>();
		Direction sortt=null;
		if (sort.equals("asdfa")&&sort!=null) {
		 	sortt=   Sort.Direction.DESC;
		}else {
			sortt=   Sort.Direction.ASC;
		}

		
        Pageable pageable =  PageRequest.of(page-1, size);//new PageRequest(page,size,sortt,"createDate");
	    Page<VehicleMaintenance> pages=vrepairDao.findAll(pageable);
        int totals= (int) pages.getTotalElements();
        List<VehicleMaintenance> rolelist= pages.getContent();
	     rp.setData(rolelist);
	     rp.setTotal(totals);
	     rp.setSuccess(true);
		return rp;
	}
	@Override
	@Cacheable(key="'vehm_'+#vehicleNumber")
	public Page<VehicleMaintenance> findSearch(String licensePlateNumber, Pageable pageable) {
		Page<VehicleMaintenance> vehiclemte = vrepairDao.findAll(pageable);
        return vehiclemte;

	}
	//测试方法
	@Override
	public List<VehicleMaintenance> findBymrks(String mrks) {
		List<VehicleMaintenance>vmn=vrepairDao.findByvehicleNumber(mrks);
		return vmn;
	}
	
	//下面三个方法
	@Override
	@Cacheable(key="'vclemte_'+#page+'_'+#size")
	public ResultPage<VehicleMaintenance> findbypage(String queryName, int page, int size)
			throws BusinessRuntimeException {
		ResultPage<VehicleMaintenance> ro=new ResultPage<VehicleMaintenance>();
		PageRequest pageable = new PageRequest(page-1,size);
	Page<VehicleMaintenance> pages =vrepairDao.findbypage(queryName, pageable);
		
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
	public ResultObject<VehicleMaintenance> addRepair(VehicleMaintenance vcmte) throws BusinessRuntimeException {
		ResultObject<VehicleMaintenance> ro=new ResultObject<>();
		//新增
		if(Long.valueOf(vcmte.getId())==null||vcmte.getId()==0) {
			
			vcmte.setCreateDate(new Date());
		 Date maintime=vcmte.getMaintainDate();
		
			//String s=simpleDateFormat.format(maintime);
		  
			//simpleDateFormat.parse(vcmte.getMaintainDate());
			vrepairDao.save(vcmte);
			//修改
		}else if(vcmte!=null){
			Date maintime=vcmte.getMaintainDate();
			vcmte.setMaintainDate(maintime);
			vrepairDao.saveAndFlush(vcmte);
			 ro.setSuccess(true);
			 ro.setMsg("操作成功");
		}
		
		/* if(vmtncemn!=null) {
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
	@Override
	@CacheEvict(allEntries=true)
	public ResultObject<VehicleMaintenance> deletRepair(long id) throws BusinessRuntimeException {
		ResultObject<VehicleMaintenance> ro=new ResultObject<>(); 
		VehicleMaintenance vmttt=vrepairDao.findById(id);
		 if (vmttt!=null) {
			 vmttt.setEnabled(false);
			 vrepairDao.saveAndFlush(vmttt);
			ro.setSuccess(true);
			ro.setMsg("操作成功");
		}else {
			ro.setSuccess(false);
			ro.setMsg("操作失败");
		throw	new BusinessRuntimeException(ResultCode.OPER_FAILED);
		}  
		return ro;
	}
	
	
	
	
	
	
	}
	
	


