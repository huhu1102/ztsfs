package com.zt.serviceImp;


import com.zt.dao.ContractScheduleDao;
import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.po.ContractCode;
import com.zt.po.ContractSchedule;
import com.zt.service.ContractScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ContractScheduleServiceImp implements ContractScheduleService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    ContractScheduleDao contractScheduleDao;
    /*
    查询所有
     */
    @Override
    public ResultObject<ContractSchedule> findAll() throws BusinessRuntimeException {
        ResultObject<ContractSchedule> ro = new ResultObject<>();
        List<ContractSchedule> contractSchedulesList = contractScheduleDao.findAll();
        if(contractSchedulesList!=null){
            ro.setData(contractSchedulesList);
            ro.setSuccess(true);
            logger.info("查询成功");
        }else{
            ro.setSuccess(false);
            logger.info("查询失败");
            throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
        }
        return ro;
    }
    /*
    新增
     */
    @Override
    public ResultObject<ContractSchedule> add(ContractSchedule contractSchedule) throws BusinessRuntimeException {
        ResultObject<ContractSchedule> ro = new ResultObject<>();
        if(Long.valueOf(contractSchedule.getId()).equals("null")||contractSchedule.getId()==0) {
            contractSchedule.setCreateDate(new Date());
        }
        contractSchedule.setEnabled(true);
        contractSchedule = contractScheduleDao.saveAndFlush(contractSchedule);
        if (contractSchedule!=null&&contractSchedule.getId()>0) {
            ro.setSuccess(true);
            ro.setMsg("操作成功！");
        }else {
            ro.setSuccess(false);
            ro.setMsg("操作失败");
            throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
        }
        return ro;
    }
    /*
    删除
     */
    @Override
    public ResultObject<ContractSchedule> delete(long id) throws BusinessRuntimeException {
        ResultObject<ContractSchedule> ro = new ResultObject<>();
        ContractSchedule contractSchedule = contractScheduleDao.findById(id);
        if(contractSchedule!=null){
            contractSchedule.setEnabled(false);
            contractScheduleDao.saveAndFlush(contractSchedule);
            ro.setSuccess(true);
            ro.setMsg("删除成功");
        }else{
            ro.setSuccess(false);
            ro.setMsg("操作失败");
            throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
        }
        return ro;
    }

    @Override
    public ResultObject<ContractSchedule> findByContractId(long id) {
        ResultObject<ContractSchedule> ro = new ResultObject<>();
        List<ContractSchedule> schedulelist = contractScheduleDao.findByCodeId(id);
        if (null != schedulelist) {
           ro.setData(schedulelist);
           ro.setSuccess(true);
        }else{
            ro.setSuccess(false);
            throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
        }
        return ro;
      }
    }
