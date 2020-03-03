/**
 * 
 */
package com.zt.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.Bidding;
import com.zt.po.DeviceInfo;


/**
 * @author yh
 * @date 2019年4月18日
 */
public interface DeviceInfoService {
  public List<DeviceInfo>findBydeviceStatus(String  deviceStatus);
  public  boolean update(DeviceInfo  deif);
  public boolean updatedeviceinfo(String deviceStatus,String deviceName);
  
  ResultPage<DeviceInfo> findbyPage(Integer page,Integer size,String sort);
  Page<DeviceInfo> findSearch(String deviceName,Pageable pageable);
  
  public ResultPage<DeviceInfo> findDeviceInfo(String deviceName, int page, int size) throws BusinessRuntimeException;
  
  
  public  ResultObject<DeviceInfo> addDeviceInfo(DeviceInfo difsss)throws BusinessRuntimeException;
	/**
	 * @param id
	 * @return
	 */
	public ResultObject<DeviceInfo> deletDeviceInfo(long id)throws BusinessRuntimeException;
}
