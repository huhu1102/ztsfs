package com.zt.service;

import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.Product;
import com.zt.po.SalesPlan;

public interface SalesPlanService {

    ResultPage<Object> find(String clientName, String productName, String empName, String start, String end, Integer status,int page, int size)throws BusinessRuntimeException;


    /*
     * 产品的修改
     */
    ResultObject<SalesPlan> add(SalesPlan salesPlan)throws BusinessRuntimeException;
    /*
     * 产品的新增
     */
    ResultObject<SalesPlan> update(SalesPlan salesPlan)throws BusinessRuntimeException;
    /*
     * 删除
     */
    ResultObject<SalesPlan> delete(long id)throws BusinessRuntimeException;

    /*
    撤销
     */
    ResultObject<SalesPlan> annul(long id)throws BusinessRuntimeException;

    ResultPage<Object> findForPlan(String clientName, String productName, String empName, String start, String end, Integer status, int page, int size);

    ResultPage<SalesPlan> findForShipping(String clientName, String productName, String empName, String start, String end, Integer status, int page, int size);

    ResultObject<SalesPlan> findStatus()throws BusinessRuntimeException;

    ResultObject<Product> findClient(long salesPlanId)throws BusinessRuntimeException;

    ResultObject<SalesPlan> batchEdit(String ids, int important);

    ResultPage<Object> findForNoPlan(String clientName, String productName, String empName, String start, String end, Integer status, int page, int size);

}
