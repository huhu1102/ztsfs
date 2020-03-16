package com.zt.service;

import com.zt.model.ContractFileModel;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.ContractFile;

public interface ContractFileService {

    ResultPage<ContractFile> findSearch( int page, int size);

    ResultObject<ContractFile> add(ContractFileModel contractFileModel);

    ResultObject<ContractFile> update(ContractFileModel contractFileModel);

    ResultObject<ContractFile> delete(long id);
}
