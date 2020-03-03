package com.zt.serviceImp;

import com.zt.common.UsersUtils;
import com.zt.dao.EmployeeDao;
import com.zt.dao.RowMaterialDao;
import com.zt.dao.RowMaterialOutRecievingDao;
import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.RowMaterial;
import com.zt.po.RowMaterialInRecieving;
import com.zt.po.RowMaterialOutRecieving;
import com.zt.service.RowMaterialOutRecievingService;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RowMaterialOutRecievingServiceImp implements RowMaterialOutRecievingService {

    @Autowired
    RowMaterialOutRecievingDao outRecievingDao;
    @Autowired
    EmployeeDao employeeDao;
    @Autowired
    RowMaterialDao rowMaterialDao;
    /*
     * 分页查询
     */
    @Override
    public ResultPage<RowMaterialOutRecieving> findSearch(String queryName, int page, int size) throws BusinessRuntimeException {
        ResultPage<RowMaterialOutRecieving> ro = new ResultPage<>();

        Pageable pageable = PageRequest.of(page-1,size);
        Page<RowMaterialOutRecieving> pages = outRecievingDao.findSearch(queryName,pageable);

        if (pages!=null) {
            int totals=(int) pages.getTotalElements();
            ro.setData(pages.getContent());
            ro.setTotal(totals);
            ro.setTotalPages(pages.getTotalPages());
            ro.setSuccess(true);
        } else {
            ro.setSuccess(false);
            throw new BusinessRuntimeException(ResultCode.UNKNOWN_ERROR);
        }

        return ro;
    }
    /*
     * 撤回
     */
    @Override
    public ResultObject<RowMaterialOutRecieving> withdraw(long id) {
        ResultObject<RowMaterialOutRecieving> ro= new ResultObject<>();
        RowMaterialOutRecieving outRec = outRecievingDao.findById(id);
        outRec.setVerifyStatus(2);
        outRec = outRecievingDao.saveAndFlush(outRec);
		float quantity = outRec.getQuantity();
		
		RowMaterial material = outRec.getMaterail();
		
		material.setQuantity(material.getQuantity()+quantity);
		material = rowMaterialDao.saveAndFlush(material);
		if(outRec!=null&&material!=null) {
			ro.setSuccess(true);
			ro.setMsg("撤销成功");
			
		}else {
			ro.setSuccess(false);
			ro.setMsg("撤销失败");
			throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
		}
        
        
        
        return ro;
    }
    /*
     * 新建
     */
	@Override
	public ResultObject<RowMaterialOutRecieving> addNew(long id, float quaity, String notes)
			throws BusinessRuntimeException {
        ResultObject<RowMaterialOutRecieving> ro= new ResultObject<>();
        RowMaterialOutRecieving rowMaterialOutRecieving = new RowMaterialOutRecieving();
		RowMaterial rowMaterial = rowMaterialDao.findById(id);
		rowMaterialOutRecieving.setName(rowMaterial.getMaterialName());
		rowMaterialOutRecieving.setUnitId(rowMaterial.getUnitId());
		rowMaterialOutRecieving.setUnitName(rowMaterial.getUnitName());
		rowMaterialOutRecieving.setQuantity(quaity);
		rowMaterialOutRecieving.setMaterailId(id);
		rowMaterialOutRecieving.setMaterail(rowMaterial);
		rowMaterialOutRecieving.setVerifyStatus(1);
		rowMaterialOutRecieving.setEnable(true);
		rowMaterialOutRecieving.setNotes(notes);
		
		long empId = UsersUtils.getCurrentHr().getEmpId();
		rowMaterialOutRecieving.setOperatorId(empId);
		rowMaterialOutRecieving.setOperatorName(employeeDao.findById(empId).getName());
		rowMaterialOutRecieving.setOperator(employeeDao.findById(empId));
		rowMaterialOutRecieving.setCreateDate(new Date());
		
		rowMaterialOutRecieving = outRecievingDao.save(rowMaterialOutRecieving);
		if(rowMaterialOutRecieving!=null) {
			ro.setSuccess(true);
			ro.setMsg("新增成功");
			//修改库存
			
			rowMaterial.setQuantity(rowMaterial.getQuantity()-quaity);
			rowMaterial = rowMaterialDao.saveAndFlush(rowMaterial);
			if(rowMaterial!=null) {
				ro.setSuccess(true);
				ro.setMsg("库存修改成功");
			}else {
				ro.setSuccess(false);
				ro.setMsg("库存修改失败");
				throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
			}
		}else {
			ro.setSuccess(false);
			ro.setMsg("新增失败");
			throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
		}

        return ro;
	}
}
