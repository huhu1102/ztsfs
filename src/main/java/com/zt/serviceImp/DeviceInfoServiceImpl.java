/**
 * 
 */
package com.zt.serviceImp;

import java.util.Date;
import java.util.List;

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

import com.zt.common.PageUtil;
import com.zt.dao.DeviceInfoDao;
import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.DeviceInfo;

import com.zt.service.DeviceInfoService;

/**
 * @author yh
 * @date 2019年4月18日
 */
@Service("deviceInfoService")
@CacheConfig(cacheNames = "zt_caches_deviceinfo")
public class DeviceInfoServiceImpl implements DeviceInfoService{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private DeviceInfoDao dif;
	@Override
	@Cacheable(key="'dev_'+#deviceStatus")
	public List<DeviceInfo> findBydeviceStatus(String deviceStatus) {
		List<DeviceInfo> difo=dif.findBydeviceStatus(deviceStatus);
		return difo;
	}

	@Override
	@CachePut(key="'dev_'+#deviceStatus+#deviceName")
	public boolean updatedeviceinfo(String deviceStatus, String deviceName) {
		boolean flag=false;
		int result=0;
		try {
			 result=dif.updateBydeviceName(deviceStatus, deviceName);
			 if(result>0){
		  flag=true;
			 }
		 } catch (Exception e) {
				e.printStackTrace();
			}
		return flag;
		
	}

	@Override
	@CachePut(key="'dev_'+#deif")
	public boolean update(DeviceInfo deif) {
		boolean flag = false;
		try {
			deif	= dif.saveAndFlush(deif);
			if (null!=deif) {
				 flag = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	@Cacheable(key="'dev_'+#page+#page+#size")
	public ResultPage<DeviceInfo> findbyPage(Integer page, Integer size, String sort) {
		ResultPage<DeviceInfo> rp=new ResultPage<>();

        Pageable pageable =  PageRequest.of(page-1, size);//new PageRequest(page,size,sortt,"createDate");
	    Page<DeviceInfo> pages=dif.findAll(pageable);
        int totals=   (int) pages.getTotalElements();
        List<DeviceInfo> rolelist= pages.getContent();
	     rp.setData(rolelist);
	     rp.setTotal(totals);
	     rp.setSuccess(true);
		return rp;
	}

	@Override
	@Cacheable(key="'dev_'+#pageable+#deviceName")
	public Page<DeviceInfo> findSearch(String deviceName, Pageable pageable) {
		 Page<DeviceInfo> deviceInfoPage = dif.findSearch(deviceName,pageable);
	        return deviceInfoPage;
	}
	//新增三个方法

	@Override
	@Cacheable(key="'devf_'+#deviceStatus")
	public ResultPage<DeviceInfo> findDeviceInfo(String deviceName, int page, int size)
			throws BusinessRuntimeException {
		ResultPage<DeviceInfo> ro=new ResultPage<DeviceInfo>();
		PageRequest pageable = new PageRequest(page-1,size);

		List<DeviceInfo> pagelist=dif.findAllByPage(deviceName, (page-1)*size, size);
		 if (pagelist!=null) {
	        	 ro.setData(pagelist);
	    	     ro.setTotal(dif.countAllData(deviceName));
	    	     ro.setTotalPages(PageUtil.getTotalPages(dif.countAllData(deviceName), size));
	    	     ro.setSuccess(true);
			} else {
				ro.setSuccess(false);
				throw  new BusinessRuntimeException(ResultCode.UNKNOWN_ERROR);
			}
		
		return ro;
	}

	@Override
	@CacheEvict(allEntries=true)//清除缓存
	public ResultObject<DeviceInfo> addDeviceInfo(DeviceInfo difsss) throws BusinessRuntimeException {
		ResultObject<DeviceInfo> ro=new ResultObject<>();
		if(difsss.getId()==0) {
			difsss.setCreateDate(new Date());
		}
		difsss.setEnabled(true);
		DeviceInfo ars=dif.saveAndFlush(difsss);
		 if(ars!=null) {
			   ro.setSuccess(true);
			   logger.info("成功！");
		   }else {
			   ro.setSuccess(false);
			   ro.setMsg("操作失败");
			throw   new BusinessRuntimeException(ResultCode.OPER_FAILED);
		   }
		
		return ro;
	}

	@Override
	@CacheEvict(allEntries=true)//清除缓存
	public ResultObject<DeviceInfo> deletDeviceInfo(long id) throws BusinessRuntimeException {
		ResultObject<DeviceInfo> ro=new ResultObject<>(); 
		DeviceInfo acris=dif.findById(id);
		 if (acris!=null) {
			 acris.setEnabled(false);
			 dif.saveAndFlush(acris);
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
