package com.zt.dao;

import com.zt.po.ContractUseRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "contractUseRecord", path="contractUseRecord")
public interface ContractUseRecordDao extends JpaRepository<ContractUseRecord,Long> {

    /*
    自定义分页查询
     */
    @Query(value="select * from zt_contractuserecord c where c.enabled=true",nativeQuery=true)
    Page<ContractUseRecord> findSearch(Pageable pageable);

    @Query("select c from ContractUseRecord c where c.id=?1 and c.enabled=true")
    ContractUseRecord findById(long id);

}
