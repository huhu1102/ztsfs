package com.zt.service;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.po.ProductUse;

public interface ProductedUseService {
    /*
    查询所有
     */
    ResultObject<ProductUse> findAll() throws BusinessRuntimeException;
    /*
    新增和修改
     */
    ResultObject<ProductUse> add(ProductUse use)throws BusinessRuntimeException;
    /*
    删除
     */
    ResultObject<ProductUse> delete(long id)throws BusinessRuntimeException;

    ResultObject<ProductUse> update(ProductUse color);
}
