/**
 * 
 */
package com.zt.service;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.VehicleAllot;
import com.zt.po.VehicleMaintenance;
import com.zt.vo.CarAssignedModel;

/**
 * @author yh
 * @date 2019年4月28日
 */
public interface VehicleAllotService {
	public ResultPage<VehicleAllot> findbypage(String queryName, int page, int size) throws BusinessRuntimeException;
    
     //消息通知
    public  ResultObject<VehicleAllot> addvcalt(VehicleAllot vat)throws BusinessRuntimeException;
	/**
	 * @param id
	 * @return
	 */
	public ResultObject<VehicleAllot> deletvcalt(long id)throws BusinessRuntimeException;

	/**
	 * @param assignedModel
	 * @return
	 */
	public ResultObject<VehicleAllot> addAssigned(CarAssignedModel assignedModel)throws BusinessRuntimeException;

	/**
	 * @param allot
	 * @return
	 */
	public ResultObject<VehicleAllot> update(VehicleAllot allot)throws BusinessRuntimeException ;

	/**
	 * @param assignedModel
	 * @return
	 */
	public ResultObject<VehicleAllot> refuse(CarAssignedModel assignedModel)throws BusinessRuntimeException;
	
	
	
}
