package com.zt.common;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zt.dao.EmployeeDao;
import com.zt.dao.MidProductInRecievingDao;
import com.zt.dao.MidProductOutRecievingDao;
import com.zt.dao.RowMaterialInRecievingDao;
import com.zt.dao.RowMaterialOutRecievingDao;
import com.zt.po.MidProductInRecieving;
import com.zt.po.MidProductOutRecieving;
import com.zt.po.MiddleProduct;
import com.zt.po.RowMaterial;
import com.zt.po.RowMaterialInRecieving;
import com.zt.po.RowMaterialOutRecieving;

/**
 * @author wl
 * @date 2019年7月1日
 */
@Component
public class MidProductCreateRecieving {
	
	  @Autowired 
	  MidProductInRecievingDao inRecievingDao;
	  
	  @Autowired
	  MidProductOutRecievingDao outRecievingDao;
	  
	  @Autowired 
	  EmployeeDao empDao;
	  
	  //半成品的入库记录
	  public int createMiddleProductInRecieving(MiddleProduct midPro,float num) { 
		  int result = 0; 
		  if(num!=0) {
			  MidProductInRecieving midProRec = new MidProductInRecieving();
			  midProRec.setCreateDate(new Date());
			  midProRec.setName(midPro.getName()); 
			  midProRec.setUnitId(midPro.getUnitId());
			  midProRec.setUnitName(midPro.getUnitName());
			  midProRec.setMidproduct(midPro);
			  midProRec.setQuantity(num); 
			  midProRec.setMidProcutId(midPro.getId());
			  //获取当前登录的员工id 
			  long empId = UsersUtils.getCurrentHr().getEmpId();
			  midProRec.setOperator(empDao.findById(empId));
			  midProRec.setOperatorId(empId);
			  midProRec.setOperatorName(empDao.findById(empId).getName());
			  midProRec.setEnable(true); 
			  midProRec.setVerifyStatus(1);
			  
			  midProRec = inRecievingDao.save(midProRec);
			  if(midProRec!=null) { 
				  result = 1; 
			  } 
		  }
		  return result; 
	  }
	  //半成品的出库记录
	  public int createMiddleProductOutRecieving(MiddleProduct midPro,float num) { 
		  int result = 0; 
		  if(num!=0) {
			  MidProductOutRecieving midProRec = new MidProductOutRecieving();
			  midProRec.setCreateDate(new Date());
			  midProRec.setName(midPro.getName()); 
			  midProRec.setUnitId(midPro.getUnitId());
			  midProRec.setUnitName(midPro.getUnitName());
			  midProRec.setMidproduct(midPro);
			  midProRec.setQuantity(num); 
			  midProRec.setMidProcutId(midPro.getId());
			  //获取当前登录的员工id 
			  long empId = UsersUtils.getCurrentHr().getEmpId();
			  midProRec.setOperator(empDao.findById(empId));
			  midProRec.setOperatorId(empId);
			  midProRec.setOperatorName(empDao.findById(empId).getName());
			  midProRec.setEnable(true); 
			  midProRec.setVerifyStatus(1);
			  
			  midProRec = outRecievingDao.save(midProRec);
			  if(midProRec!=null) { 
				  result = 2; 
			  } 
		  }
		  return result; 
	  }
	  
	  
}