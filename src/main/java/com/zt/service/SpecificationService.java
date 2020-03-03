package com.zt.service;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.Specification;

public interface SpecificationService {

    /*
    分页查询
     */
    ResultPage<Specification> findSearch(String queryName,int page,int size) throws BusinessRuntimeException;

    /*
    新建和更新
     */
    ResultObject<Specification> create(Specification specification) throws BusinessRuntimeException;
    /*
    删除
     */
    ResultObject<Specification> delete(long id) throws BusinessRuntimeException;

}

