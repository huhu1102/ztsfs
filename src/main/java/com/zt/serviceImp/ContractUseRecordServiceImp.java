package com.zt.serviceImp;


import com.zt.dao.ContractUseRecordDao;
import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.ContractUseRecord;
import com.zt.service.ContractUseRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ContractUseRecordServiceImp implements ContractUseRecordService {
    @Autowired
    ContractUseRecordDao contractUseRecordDao;
    /*
    分页查询
     */
    @Override
    public ResultPage<ContractUseRecord> findSearch(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        ResultPage<ContractUseRecord> ro = new ResultPage<>();
        Page<ContractUseRecord> pages = contractUseRecordDao.findSearch(pageable);
        if (pages.getContent()!= null) {
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

    /*
    新增
     */
    @Override
    public ResultObject<ContractUseRecord> add(ContractUseRecord contractUseRecord) {
        ResultObject<ContractUseRecord> ro = new ResultObject<>();
        if(Long.valueOf(contractUseRecord.getId()).equals("null")||contractUseRecord.getId()==0) {
            contractUseRecord.setCreateDate(new Date());
        }
        contractUseRecord.setEnabled(true);
        contractUseRecord = contractUseRecordDao.saveAndFlush(contractUseRecord);
        if (contractUseRecord!=null&&contractUseRecord.getId()>0) {
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
    public ResultObject<ContractUseRecord> delete(long id) {
        ResultObject<ContractUseRecord> ro = new ResultObject<>();
        ContractUseRecord contractUseRecord = contractUseRecordDao.findById(id);
        if(contractUseRecord!=null){
            contractUseRecord.setEnabled(false);
            contractUseRecordDao.saveAndFlush(contractUseRecord);
            ro.setSuccess(true);
            ro.setMsg("删除成功");
        }else{
            ro.setSuccess(false);
            ro.setMsg("操作失败");
            throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
        }
        return ro;
    }
}
