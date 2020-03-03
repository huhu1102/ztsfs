package com.zt.serviceImp;

import com.zt.common.UsersUtils;
import com.zt.dao.*;
import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.ReturnVisit;
import com.zt.service.ReturnVisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author wl
 * @date 2019年5月9日
 */
@Service("returnvisitService")
//@CacheConfig(cacheNames = "zt_caches_returnvisitservice")
public class ReturnVisitServiceImp implements ReturnVisitService {
    @Autowired
    ReturnVisitDao returnVisitDao;
    @Autowired
    ShippingBillDao billDao;
    @Autowired
    EmployeeDao employeeDao;
    @Autowired
    ShippingRequestDetailsDao shippingRequestDetailsDao;
    @Autowired
    SalesPlanDao salesPlanDao;

    /*
     * 分页模糊查询
     */
    @Override
//	@Cacheable(key="'return_'+#queryName+'_'+#page+'_'+#size")
    public ResultPage<ReturnVisit> findSearch(String queryName, int page, int size) throws BusinessRuntimeException {
        ResultPage<ReturnVisit> ro = new ResultPage<ReturnVisit>();
        if (queryName == null) {
            queryName = "";
        }
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ReturnVisit> pages = returnVisitDao.findSearch(queryName, pageable);
        if (pages != null) {
            ro.setData(pages.getContent());
            ro.setTotal(pages.getTotalElements());
            ro.setTotalPages(pages.getTotalPages());
            ro.setSuccess(true);
        } else {
            ro.setSuccess(false);
            throw new BusinessRuntimeException(ResultCode.UNKNOWN_ERROR);
        }
        return ro;
    }

    /**
     * 新建和更新
     * <p>
     * 需要修改---11-20 whl
     */
    @Override
    @Transactional
    public ResultObject<ReturnVisit> savePosition(ReturnVisit returnVisit) throws BusinessRuntimeException {
        ResultObject<ReturnVisit> ro = new ResultObject<>();
        ReturnVisit old = returnVisitDao.findById(returnVisit.getId());
        if (returnVisit.getId() == 0) {
            returnVisit.setCreateDate(new Date());
        } else {
            if (null == old.getCreateDate()) {
                returnVisit.setCreateDate(new Date());
            } else {
                returnVisit.setCreateDate(old.getCreateDate());
            }
        }
        returnVisit.setEnabled(true);
        Long billId = returnVisit.getBillId();
        if (billId != null && billId != 0) {
            returnVisit.setShippingBill(billDao.findById((long) returnVisit.getBillId()));
        }
        long empId = UsersUtils.getCurrentHr().getEmpId();
        if (empId != 0) {
            returnVisit.setRecorderId(empId);
            returnVisit.setRecorder(employeeDao.findById(empId));
        }
        returnVisit = returnVisitDao.saveAndFlush(returnVisit);
        if (returnVisit != null) {
            Integer integer = returnVisitDao.findBillId(returnVisit.getBillId());
            if(integer==null||integer==0){
                integer=1;
            }
            int changeStatus = billDao.changeStatus(integer, billId);
            if (changeStatus > 0) {
                ro.setSuccess(true);
                ro.setMsg("更新成功");
            } else {
                ro.setMsg("更新失败");
                ro.setSuccess(false);
            }
        }else{
            ro.setSuccess(false);
            ro.setMsg("操作失败！");
        }
        return ro;
    }

    /*
     * 删除
     */
    @Override
    @Transactional
    public ResultObject<ReturnVisit> deletDel(long id) throws BusinessRuntimeException {
        ResultObject<ReturnVisit> ro = new ResultObject<>();
        ReturnVisit returnVisit = returnVisitDao.findById(id);
        returnVisit.setEnabled(false);
        returnVisit= returnVisitDao.saveAndFlush(returnVisit);
        if (returnVisit != null) {
            Integer integer = returnVisitDao.findBillId(returnVisit.getBillId());
            int changeStatus;
            Long billId = returnVisit.getBillId();
            if(integer==null||integer==0){
                integer=1;
            }
            changeStatus = billDao.changeStatus(integer, billId);
            if (changeStatus > 0) {
                ro.setSuccess(true);
                ro.setMsg("删除成功");
            } else {
                ro.setMsg("删除失败！");
                ro.setSuccess(false);
            }
        } else {
            ro.setSuccess(false);
            ro.setMsg("操作失败，请重试");
        }
        return ro;
    }

    @Override
    public ResultPage<ReturnVisit> findByBillId(long billId) {
        ResultPage<ReturnVisit> ro = new ResultPage<>();
        List<ReturnVisit> list = returnVisitDao.findByBillId(billId);
        if (list != null) {
            ro.setData(list);
            ro.setTotal(list.size());
            ro.setSuccess(true);
        } else {
            ro.setSuccess(false);
            throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
        }
        return ro;
    }

}
