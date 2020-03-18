package com.zt.serviceImp;

import com.zt.common.PinyinUtils;
import com.zt.dao.ContractCodeDao;
import com.zt.model.*;
import com.zt.po.ContractCode;
import com.zt.service.ContractCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ContractCodeServiceImp implements ContractCodeService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ContractCodeDao contractCodeDao;

    /**
     * 查询所有
     * @return
     * @throws BusinessRuntimeException
     */
    @Override
    public ResultObject<ContractCode> findAllcodes() throws BusinessRuntimeException {
        ResultObject<ContractCode> ro = new ResultObject<>();
        List<ContractCode> contractCode = contractCodeDao.findAllcodes();
        if(contractCode!=null){
            ro.setData(contractCode);
            ro.setSuccess(true);
            logger.info("查询成功");
        }else{
            ro.setSuccess(false);
            logger.info("查询失败");
            ro.setMsg("查询成功！");
            throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
        }
        return ro;
    }

    /**
     * 新增
     * @param contractCode
     * @return
     * @throws BusinessRuntimeException
     */
    @Override
    public ResultObject<ContractCode> add(ContractCode contractCode) throws BusinessRuntimeException {
        ResultObject<ContractCode> ro = new ResultObject<>();
        if(Long.valueOf(contractCode.getId()).equals("null")||contractCode.getId()==0) {
            contractCode.setCreateDate(new Date());
        }
        contractCode.setCode( PinyinUtils.getPingYin(PinyinUtils.cleanChar(contractCode.getCodeName())));
        contractCode.setEnabled(true);
        ContractCode byName = contractCodeDao.findByName(contractCode.getCodeName());
        if(byName!=null){
            ro.setSuccess(false);
            ro.setMsg("流程名称重复,请确认后重新添加");
        }else{
            contractCode = contractCodeDao.saveAndFlush(contractCode);
            if (contractCode!=null&&contractCode.getId()>0) {
                ro.setSuccess(true);
                ro.setMsg("保存成功！");
            }else {
                ro.setSuccess(false);
                ro.setMsg("操作失败");
                throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
            }
        }
        return ro;
    }

    /**
     * 删除
     * @param id
     * @return
     * @throws BusinessRuntimeException
     */
    @Override
    public ResultObject<ContractCode> delete(long id) throws BusinessRuntimeException {
        ResultObject<ContractCode> ro = new ResultObject<>();
        ContractCode contractCode = contractCodeDao.findById(id);
        if(null!=contractCode){
            contractCode.setEnabled(false);
            contractCode=contractCodeDao.saveAndFlush(contractCode);
            ro.setSuccess(true);
            ro.setMsg("已删除");
        }else{
            ro.setSuccess(false);
            ro.setMsg("操作失败");
            throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
        }
        return ro;
    }

}
