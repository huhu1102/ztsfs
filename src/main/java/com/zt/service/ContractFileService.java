package com.zt.service;

import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.ContractFile;

public interface ContractFileService {

    ResultPage<ContractFile> findSearch( int page, int size);

    ResultObject<ContractFile> add(ContractFile contractFile);

    ResultObject<ContractFile> delete(long id);
}
