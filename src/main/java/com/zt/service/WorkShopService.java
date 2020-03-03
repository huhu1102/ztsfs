package com.zt.service;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.WorkShop;

public interface WorkShopService {

    /*
     * 新增
     */
    ResultObject<WorkShop> add(WorkShop workShop)throws BusinessRuntimeException;
    /*
     * 修改
     */
    ResultObject<WorkShop> update(WorkShop workShop)throws BusinessRuntimeException;
    /*
     * 分页模糊条件查询
     */
    ResultPage<WorkShop> find(String name,String code, int page, int size) throws BusinessRuntimeException;

    /*
     * 删除
     */
    ResultObject<WorkShop> delet(long id)throws BusinessRuntimeException;

}
