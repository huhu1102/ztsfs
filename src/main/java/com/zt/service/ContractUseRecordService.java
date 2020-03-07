package com.zt.service;

import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.ContractUseRecord;

public interface ContractUseRecordService {
    ResultPage<ContractUseRecord> findSearch(int page, int size);

    ResultObject<ContractUseRecord> add(ContractUseRecord contractUseRecord);

    ResultObject<ContractUseRecord> delete(long id);
}
