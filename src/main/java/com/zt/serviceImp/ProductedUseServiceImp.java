package com.zt.serviceImp;

import com.zt.dao.ProductedUseDao;
import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.po.ProductUse;
import com.zt.service.ProductedUseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("productUse")
public class ProductedUseServiceImp implements ProductedUseService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    ProductedUseDao productedUseDao;
    @Override
    public ResultObject<ProductUse> findAll() throws BusinessRuntimeException {
        ResultObject<ProductUse> ro=new ResultObject<>();
        List<ProductUse>list= productedUseDao.findAll();
        if(list!=null){
            ro.setData(list);
            logger.info("查询成功");
            ro.setSuccess(true);
        }else{
            logger.info("查询失败！");
            ro.setSuccess(false);
            throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
        }
        return ro;
    }

    @Override
    public ResultObject<ProductUse> add(ProductUse use) throws BusinessRuntimeException {
        ResultObject<ProductUse> ro=new ResultObject<>();
         if(use.getId()==0){//新增
             use.setCreateDate(new Date());
         }
         use.setEnabled(true);
         //查询 是否有相同的名字

         if(productedUseDao.finddfByName(use.getName().trim())>0){
             ro.setSuccess(false);
             ro.setMsg("该字段已存在!");
         }else{
             use=productedUseDao.saveAndFlush(use);
             if(use!=null){
                 logger.info("保存成功！");
                 ro.setMsg("保存成功！");
                 ro.setSuccess(true);
             }else{
                 ro.setSuccess(false);
                 throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
             }

         }
        return ro;
    }

    @Override
    public ResultObject<ProductUse> update(ProductUse use) {
        ResultObject<ProductUse> ro=new ResultObject<>();

        use.setEnabled(true);
        //查询 是否有相同的名字

        if(productedUseDao.finddfByNameAndId(use.getName().trim(),use.getId())>0){
            ro.setSuccess(false);
            ro.setMsg("该字段已存在!");
        }else{
            use=productedUseDao.saveAndFlush(use);
            if(use!=null){
                ro.setMsg("保存成功！");
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
    public ResultObject<ProductUse> delete(long id) throws BusinessRuntimeException {
        ResultObject<ProductUse> ro=new ResultObject<>();
        int num= productedUseDao.deleteUse(id);
        if(num>0){
            ro.setSuccess(true);
        }else{
            ro.setSuccess(false);
            throw  new  BusinessRuntimeException(ResultCode.OPER_FAILED);
        }
        return ro;
    }
}
