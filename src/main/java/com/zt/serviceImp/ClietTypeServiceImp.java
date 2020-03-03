package com.zt.serviceImp;

import com.zt.dao.ClientTypeDao;
import com.zt.dao.ProductedUseDao;
import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.po.ClientType;
import com.zt.po.ProductUse;
import com.zt.service.ClientTypeService;
import com.zt.service.ProductedUseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("clientType")
public class ClietTypeServiceImp implements ClientTypeService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    ClientTypeDao clientTypeDao;
    @Override
    public ResultObject<ClientType> findAll() throws BusinessRuntimeException {
        ResultObject<ClientType> ro=new ResultObject<>();
        List<ClientType>list= clientTypeDao.findAll();
        if(list!=null){
            ro.setData(list);
            logger.info("成功");
            ro.setSuccess(true);
        }else{
            logger.info("查询失败！");
            ro.setSuccess(false);
            throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
        }
        return ro;
    }

    @Override
    public ResultObject<ClientType> add(ClientType use) throws BusinessRuntimeException {
        ResultObject<ClientType> ro=new ResultObject<>();
         if(use.getId()==0){//新增
             use.setCreateDate(new Date());
         }
         use.setEnabled(true);
         //查询 是否有相同的名字

         if(clientTypeDao.finddfByName(use.getName().trim())>0){
             ro.setMsg("失败!");
             ro.setSuccess(false);
         }else{
             use=clientTypeDao.saveAndFlush(use);
             if(use!=null){
                 logger.info("添加成功！");
                 ro.setMsg("添加成功！");
                 ro.setSuccess(true);
             }else{
                 ro.setSuccess(false);
                 throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
             }

         }
        return ro;
    }

    @Override
    public ResultObject<ClientType> update(ClientType use) {
        ResultObject<ClientType> ro=new ResultObject<>();
        use.setEnabled(true);
        //查询 是否有相同的名字
        if(clientTypeDao.finddfByNameAndId(use.getName().trim(),use.getId())>0){
            ro.setSuccess(false);
            ro.setMsg("名称已存在");
        }else{
            use=clientTypeDao.saveAndFlush(use);
            if(use!=null){
                ro.setMsg("成功！");
                ro.setSuccess(true);
            }else{
                logger.info("保存失败！");
                ro.setSuccess(false);
                throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
            }
        }
        return ro;

    }

    @Override
    @Transactional
    public ResultObject<ClientType> delete(long id) throws BusinessRuntimeException {
        ResultObject<ClientType> ro=new ResultObject<>();
        int num= clientTypeDao.deleteUse(id);
        if(num>0){
            ro.setSuccess(true);
        }else{
            ro.setSuccess(false);
            throw  new  BusinessRuntimeException(ResultCode.OPER_FAILED);
        }
        return ro;
    }
}
