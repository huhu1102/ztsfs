package com.zt.serviceImp;

import com.zt.dao.ClientContactDao;
import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.po.ClientContact;
import com.zt.service.ClientContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("clientContact")
public class ClientContactServiceImp implements ClientContactService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    ClientContactDao contactDao;

    @Override
    public ResultObject<ClientContact> findAll(int page, int size, String qureyName) throws BusinessRuntimeException {
        ResultObject<ClientContact> ro = new ResultObject<>();
        PageRequest pageable = PageRequest.of(page - 1, size);
        Page<ClientContact> pages = contactDao.findAllByPage(qureyName, pageable);
        if (pages != null) {
            ro.setData(pages.getContent());
            logger.info("成功!");
            ro.setSuccess(true);
        } else {
            logger.info("查询失败！");
            ro.setSuccess(false);
            throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
        }
        return ro;
    }

    @Override
    public ResultObject<ClientContact> add(ClientContact use) throws BusinessRuntimeException {
        ResultObject<ClientContact> ro = new ResultObject<>();
        if (use.getId() == 0) {//新增
            use.setCreateDate(new Date());
        }
        use.setEnabled(true);
        //查询 是否有相同的名字
        if (contactDao.finddfByName(use.getName().trim()) > 0) {
            ro.setMsg("存放失败!");
            ro.setSuccess(false);
        } else {
            use = contactDao.saveAndFlush(use);
            if (use != null) {
                logger.info("添加成功！");
                ro.setMsg("成功！");
                ro.setSuccess(true);
            } else {
                ro.setSuccess(false);
                throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
            }

        }
        return ro;
    }

    @Override
    public ResultObject<ClientContact> update(ClientContact myContact) {
        ResultObject<ClientContact> ro = new ResultObject<>();
        myContact.setEnabled(true);
        //查询 是否有相同的名字
//        if(contactDao.finddfByNameAndId(use.getName().trim(),use.getId())>0){
//            ro.setSuccess(false);
//            ro.setMsg("名称已存在");
//        }else{
        myContact = contactDao.saveAndFlush(myContact);
        if (myContact != null) {
            ro.setMsg("成功了！");
            ro.setSuccess(true);
        } else {
            logger.info("保存失败！");
            ro.setSuccess(false);
            throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
        }
//        }
        return ro;
    }
    @Override
    @Transactional
    public ResultObject<ClientContact> delete(long id) throws BusinessRuntimeException {
        ResultObject<ClientContact> ro = new ResultObject<>();
        int num = contactDao.deleteUse(id);
        if (num > 0) {
            ro.setSuccess(true);
        } else {
            ro.setSuccess(false);
            throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
        }
        return ro;
    }
}
