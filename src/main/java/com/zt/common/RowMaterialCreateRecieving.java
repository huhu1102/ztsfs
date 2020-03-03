package com.zt.common;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zt.dao.EmployeeDao;
import com.zt.dao.RowMaterialInRecievingDao;
import com.zt.dao.RowMaterialOutRecievingDao;
import com.zt.po.RowMaterial;
import com.zt.po.RowMaterialInRecieving;
import com.zt.po.RowMaterialOutRecieving;

/**
 * @author wl
 * @date 2019年7月1日
 */
@Component
public class RowMaterialCreateRecieving {
	
	  @Autowired 
	  RowMaterialInRecievingDao inRecievingDao;
	  
	  @Autowired
	  RowMaterialOutRecievingDao outRecievingDao;
	  
	  @Autowired 
	  EmployeeDao empDao;
	  
	  //原材料入库记录
	  public int createRowMaterialInRecieving(RowMaterial rowMaterial,float num) {
		  int result = 0; 
		  if(num!=0) {
			  
			  RowMaterialInRecieving rowMaterialRec = new RowMaterialInRecieving(); 
			  rowMaterialRec.setCreateDate(new Date());
			  rowMaterialRec.setName(rowMaterial.getMaterialName());
			  rowMaterialRec.setUnitId(rowMaterial.getUnitId());
			  rowMaterialRec.setUnitName(rowMaterial.getUnitName());
			  rowMaterialRec.setMaterailId(rowMaterial.getId());
			  rowMaterialRec.setMaterail(rowMaterial); 
			  rowMaterialRec.setQuantity(num); 
			  //获取当前登录的员工id 
			  long empId = UsersUtils.getCurrentHr().getEmpId();
			  rowMaterialRec.setOperator(empDao.findById(empId));
			  rowMaterialRec.setOperatorId(empId);
			  rowMaterialRec.setOperatorName(empDao.findById(empId).getName());
			  rowMaterialRec.setEnable(true);
			  rowMaterialRec.setVerifyStatus(1);
			  
			  rowMaterialRec = inRecievingDao.save(rowMaterialRec);
			  if(rowMaterialRec!=null) {
				  result= 1; 
			  } 
		  }
		  return result;
	  }
	  /*
	   * 原材料出库记录
	   */
	  public int createRowMaterialOutRecieving(RowMaterial rowMaterial,float num) {
		  int result = 0; 
		  if(num!=0) {
			  
			  RowMaterialOutRecieving rowMaterialRec = new RowMaterialOutRecieving(); 
			  rowMaterialRec.setCreateDate(new Date());
			  rowMaterialRec.setName(rowMaterial.getMaterialName());
			  rowMaterialRec.setUnitId(rowMaterial.getUnitId());
			  rowMaterialRec.setUnitName(rowMaterial.getUnitName());
			  rowMaterialRec.setMaterailId(rowMaterial.getId());
			  rowMaterialRec.setMaterail(rowMaterial); 
			  rowMaterialRec.setQuantity(num); 
			  //获取当前登录的员工id 
			  long empId = UsersUtils.getCurrentHr().getEmpId();
			  rowMaterialRec.setOperator(empDao.findById(empId));
			  rowMaterialRec.setOperatorId(empId);
			  rowMaterialRec.setOperatorName(empDao.findById(empId).getName());
			  rowMaterialRec.setEnable(true);
			  rowMaterialRec.setVerifyStatus(1);
			  
			  rowMaterialRec = outRecievingDao.save(rowMaterialRec);
			  if(rowMaterialRec!=null) {
				  result= 2; 
			  } 
		  }
		  return result;
	  }
	  
	  
}