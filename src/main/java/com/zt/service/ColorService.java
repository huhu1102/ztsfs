package com.zt.service;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.po.Color;

public interface ColorService {
    /*
    查询所有
     */
    ResultObject<Color> findAll() throws BusinessRuntimeException;
    /*
    新增和修改
     */
    ResultObject<Color> add(Color color)throws BusinessRuntimeException;
    /*
    删除
     */
    ResultObject<Color> delete(long id)throws BusinessRuntimeException;
}
