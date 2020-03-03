package com.zt.serviceImp;

import com.zt.dao.WorkShopDao;
import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.Employee;
import com.zt.po.WorkShop;
import com.zt.service.WorkShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Date;

public class WorkShopServiceImp implements WorkShopService {

    @Autowired
    WorkShopDao workShopDao;
    /*
    新增
     */
    @Override
    public ResultObject<WorkShop> add(WorkShop workShop) throws BusinessRuntimeException {
        ResultObject<WorkShop> ro = new ResultObject<>();

        if(Long.valueOf(workShop.getId())==null||workShop.getId()==0) {
            workShop.setCreateDate(new Date());
        }
        workShop.setEnabled(true);
        workShop = workShopDao.save(workShop);
        if(workShop!=null){
            ro.setSuccess(true);
            ro.setMsg("新增成功");
        }else{
            ro.setSuccess(false);
            ro.setMsg("新增失败");

        }

        return ro;
    }
    /*
    修改
     */
    @Override
    public ResultObject<WorkShop> update(WorkShop workShop) throws BusinessRuntimeException {
        ResultObject<WorkShop> ro = new ResultObject<>();
        workShop.setEnabled(true);
        WorkShop pro = workShopDao.saveAndFlush(workShop);
        if (pro!=null) {
            ro.setSuccess(true);
            ro.setMsg("操作成功");
        }else {
            ro.setSuccess(false);
            ro.setMsg("操作失败");
        }
        return ro;
    }
    /*
    分页模糊查询
     */
    @Override
    public ResultPage<WorkShop> find(String name,String code, int page, int size) throws BusinessRuntimeException {
        ResultPage<WorkShop> ro = new ResultPage<>();
        PageRequest pageable =PageRequest.of(page-1,size);
        Page<WorkShop> pages = workShopDao.find(name, code, pageable);

        if (pages!=null&&pages.getContent()!=null) {
            ro.setData(pages.getContent());
            ro.setTotal(pages.getTotalElements());
            ro.setSuccess(true);
        } else {
            ro.setSuccess(false);
            throw  new BusinessRuntimeException(ResultCode.UNKNOWN_ERROR);
        }

        return ro;
    }
    /*
    删除
     */
    @Override
    public ResultObject<WorkShop> delet(long id) throws BusinessRuntimeException {
        ResultObject<WorkShop> ro=new ResultObject<>();
        WorkShop acris=workShopDao.findById(id);
        if (acris!=null) {
            acris.setEnabled(false);
            workShopDao.saveAndFlush(acris);
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
