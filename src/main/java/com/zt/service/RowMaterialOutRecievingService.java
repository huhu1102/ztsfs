package com.zt.service;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.RowMaterialOutRecieving;

public interface RowMaterialOutRecievingService  {

    /*
     * 分页模糊条件查询
     */
    public ResultPage<RowMaterialOutRecieving> findSearch(String queryName, int page, int size) throws BusinessRuntimeException;


    /*
     * 撤销出库记录
     */
    public ResultObject<RowMaterialOutRecieving> withdraw(long id)throws BusinessRuntimeException;

    /*
     * 新建
     */
    public ResultObject<RowMaterialOutRecieving> addNew(long id, float quaity, String notes)throws BusinessRuntimeException;
}
