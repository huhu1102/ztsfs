package com.zt.service;

import com.zt.model.ContractModel;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.Contract;
import com.zt.vo.ProductModel;

public interface ContractService {


    ResultPage<Contract> findSearch(String contractName, String clientName, String empName, String createDateStart, String createDateEnd, String startDateStart, String startDateEnd,
                                    String endDateStart, String endDateEnd, String signDateStart,
                                    String signDateEnd ,Integer status, int page, int size);


    ResultObject<Contract> add(ContractModel contractModel);

    ResultObject<Contract> update(ContractModel contractModel);

    ResultObject<Contract> delete(long id);
}
