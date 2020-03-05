package com.zt.serviceImp;

import com.zt.common.Message;
import com.zt.common.MessageUtil;
import com.zt.common.Utils;

import com.zt.dao.ClientDao;
import com.zt.dao.ContractDao;
import com.zt.dao.EmployeeDao;
import com.zt.dao.UploadFileDao;
import com.zt.model.*;
import com.zt.po.Contract;
import com.zt.po.Employee;
import com.zt.po.UploadFile;
import com.zt.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.util.Map;

@Service
public class ContractServiceImp implements ContractService {

    @Autowired
    ContractDao contractDao;
    @Autowired
    UploadFileDao uploadFileDao;
    @Autowired
    Message message;
    @Autowired
    MessageUtil msgUtil;
    @Autowired
    ClientDao clientDao;
    @Autowired
    EmployeeDao employeeDao;


    /**
     *
     * @param contractName
     * @param clientName
     * @param empName
     * @param createDateStart
     * @param createDateEnd
     * @param startDateStart
     * @param startDateEnd
     * @param endDateStart
     * @param endDateEnd
     * @param status
     * @param page
     * @param size
     * @return
     */
    @Override
    public ResultPage<Contract> findSearch(String contractName, String clientName, String empName, String createDateStart, String createDateEnd, String startDateStart, String startDateEnd,
                                           String endDateStart, String endDateEnd, String signDateStart,
                                           String signDateEnd ,Integer status, int page, int size) {
        ResultPage<Contract> ro = new ResultPage<>();
        Pageable pageable = PageRequest.of(page - 1, size);
        Map<String, String> stringStringMap = Utils.updateTime(createDateStart, createDateEnd,startDateStart,startDateEnd,endDateStart,endDateEnd,signDateStart, signDateEnd);
        createDateStart = stringStringMap.get("0");
        createDateEnd = stringStringMap.get("1");
        startDateStart = stringStringMap.get("2");
        startDateEnd = stringStringMap.get("3");
        endDateStart = stringStringMap.get("4");
        endDateEnd = stringStringMap.get("5");
        signDateStart = stringStringMap.get("6");
        signDateEnd = stringStringMap.get("7");

        Page<Contract> pages = contractDao.findSearch(contractName, clientName, empName, createDateStart, createDateEnd, startDateStart, startDateEnd, endDateStart, endDateEnd,signDateStart,signDateEnd, status, pageable);

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
    新增方法
     */
    @Override
    public ResultObject<Contract> add(ContractModel contractModel) {
        ResultObject<Contract> ro = new ResultObject<>();
        ContractModel mo = new ContractModel();
        Contract contract = mo.v2p(contractModel);
        List<UploadFile> uploadFileList = new ArrayList<>();
        String uploadIds = contractModel.getUploadIds();
        String[] split = uploadIds.trim().split(",");
        if(split.length>0){
            for (int i = 0,n = split.length; i < n; i++) {
                UploadFile u = uploadFileDao.findById(Long.parseLong(split[i]));
                uploadFileList.add(u);
            }
        }
        contract.setEnabled(true);
        contract.setCreateDate(new Date());
        contract.setCliente(clientDao.findById(contract.getCliId()));
        Employee employee = employeeDao.findById(contract.getEmpId());
        contract.setEmployee(employee);
        contract = contractDao.saveAndFlush(contract);
        if(contract!=null){
            ro.setSuccess(true);
            ro.setMsg("新建合同成功");
            //发送消息
            int e = msgUtil.sendMsg("新建一个合同", "", "Contract", contract.getId(), message.UserIds());
            if(e>0) {
                ro.setSuccess(true);
                ro.setMsg("发送消息成功");
            }else {
                ro.setSuccess(false);
                ro.setMsg("发送消息失败");
            }
        }else{
            ro.setSuccess(false);
            ro.setMsg("新建合同失败");
            throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
        }
        return ro;
    }
    /*
    修改方法
     */
    @Override
    public ResultObject<Contract> update(ContractModel contractModel) {
        ResultObject<Contract> ro = new ResultObject<>();
        ContractModel mo = new ContractModel();
        Contract contract = mo.v2p(contractModel);
        List<UploadFile> uploadFileList = new ArrayList<>();
        String uploadIds = contractModel.getUploadIds();
        String[] split = uploadIds.trim().split(",");
        if(split.length>0){
            for (int i = 0,n = split.length; i < n; i++) {
                UploadFile u = uploadFileDao.findById(Long.parseLong(split[i]));
                uploadFileList.add(u);
            }
        }
        contract.setEnabled(true);
        contract.setCreateDate(new Date());
        contract.setCliente(clientDao.findById(contract.getCliId()));
        Employee employee = employeeDao.findById(contract.getEmpId());
        contract.setEmployee(employee);
        contract = contractDao.saveAndFlush(contract);
        if(contract!=null){
            ro.setSuccess(true);
            ro.setMsg("修改合同成功");
            //发送消息
            int e = msgUtil.sendMsg("修改一个合同", "", "Contract", contract.getId(), message.UserIds());
            if(e>0) {
                ro.setSuccess(true);
                ro.setMsg("发送消息成功");
            }else {
                ro.setSuccess(false);
                ro.setMsg("发送消息失败");
            }
        }else{
            ro.setSuccess(false);
            ro.setMsg("修改合同失败");
            throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
        }

        return ro;
    }
    /*
    删除方法
     */
    @Override
    public ResultObject<Contract> delete(long id) {
        ResultObject<Contract> ro = new ResultObject<>();
        Contract contract = contractDao.findById(id);
        if (contract != null) {
            contract.setEnabled(false);
            contractDao.saveAndFlush(contract);
            ro.setSuccess(true);
            ro.setMsg("删除合同成功");
            //发消息
            int e = msgUtil.sendMsg("删除一个合同", "", "Contract", contract.getId(), message.UserIds());
            if(e>0) {
                ro.setSuccess(true);
                ro.setMsg("发送消息成功");
            }else {
                ro.setSuccess(false);
                ro.setMsg("发送消息失败");
            }
        }else {
            ro.setSuccess(false);
            ro.setMsg("删除失败");
        }
        return ro;
    }
}
