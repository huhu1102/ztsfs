package com.zt.serviceImp;

import com.zt.dao.ClientDao;
import com.zt.dao.ContractFileDao;
import com.zt.model.*;
import com.zt.po.Client;
import com.zt.po.ContractFile;
import com.zt.service.ContractFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ContractFileServiceImp implements ContractFileService {
    @Autowired
    ContractFileDao contractFileDao;
    @Autowired
    ClientDao clientDao;
    /*
    分页查询
     */
    @Override
    public ResultPage<ContractFile> findSearch(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        ResultPage<ContractFile> ro = new ResultPage<>();
        Page<ContractFile> pages = contractFileDao.findSearch(pageable);
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
    添加方法
     */
    @Override
    public ResultObject<ContractFile> add(ContractFileModel contractFileModel) {
        ResultObject<ContractFile> ro = new ResultObject<>();
        ContractFileModel mo =  new ContractFileModel();
        ContractFile contractFile = mo.v2p(contractFileModel);
        if(Long.valueOf(contractFile.getId()).equals("null")||contractFile.getId()==0) {
            contractFile.setCreateDate(new Date());
        }
        contractFile.setEnabled(true);
        Client client = clientDao.findById(contractFile.getClientId());
        contractFile.setClient(client);
        contractFile = contractFileDao.saveAndFlush(contractFile);
        if (contractFile!=null&&contractFile.getId()>0) {
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
    修改
     */
    @Override
    public ResultObject<ContractFile> update(ContractFileModel contractFileModel) {
        ResultObject<ContractFile> ro = new ResultObject<>();
        ContractFileModel mo =  new ContractFileModel();
        ContractFile contractFile = mo.v2p(contractFileModel);
        if(Long.valueOf(contractFile.getId()).equals("null")||contractFile.getId()==0) {
            contractFile.setCreateDate(new Date());
        }
        contractFile.setEnabled(true);
        Client client = clientDao.findById(contractFile.getClientId());
        contractFile.setClient(client);
        contractFile = contractFileDao.saveAndFlush(contractFile);
        if (contractFile!=null&&contractFile.getId()>0) {
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
    public ResultObject<ContractFile> delete(long id) {
        ResultObject<ContractFile> ro = new ResultObject<>();
        ContractFile contractFile = contractFileDao.findById(id);
        if(contractFile!=null){
            contractFile.setEnabled(false);
            contractFileDao.saveAndFlush(contractFile);
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
