package com.zt.serviceImp;

import com.zt.dao.ColorDao;
import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.po.Color;
import com.zt.service.ColorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service("colorService")
public class ColorServiceImp implements ColorService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    ColorDao colorDao;
    @Override
    public ResultObject<Color> findAll() throws BusinessRuntimeException {
        ResultObject<Color>  ro = new ResultObject<>();
        List<Color> clors = colorDao.findAll();
        if(clors!=null){
            ro.setData(clors);
            ro.setSuccess(true);
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
    public ResultObject<Color> add(Color color) throws BusinessRuntimeException {
        ResultObject<Color> ro = new ResultObject<>();
        if(Long.valueOf(color.getId()).equals("null")||color.getId()==0) {
            color.setCreateDate(new Date());
        }
        color.setEnabled(true);
        color = colorDao.saveAndFlush(color);
        if (color!=null&&color.getId()>0) {
            ro.setSuccess(true);
            ro.setMsg("添加成功！");
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
    public ResultObject<Color> delete(long id) throws BusinessRuntimeException {
        ResultObject<Color> ro = new ResultObject<>();
        Color color = colorDao.findById(id);
        if(color!=null){
            color.setEnabled(false);
            colorDao.saveAndFlush(color);
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
