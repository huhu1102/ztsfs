package com.zt.dao;

import com.zt.po.ContractSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "contractSchedule", path="contractSchedule")
public interface ContractScheduleDao extends JpaRepository<ContractSchedule,Long> {

    @Query("SELECT  c from ContractSchedule c where c.enabled=true")
    ContractSchedule findByContractCodeId(long contractCodeId);

    @Query("select c  from ContractSchedule c where c.enabled=true and c.id=?1")
    ContractSchedule findById(long id);
    @Query("SELECT  c from ContractSchedule c where c.enabled=true and c.contractCodeId=?1")
    List<ContractSchedule> findByCodeId(long id);
    @Query("SELECT  c from ContractSchedule c where c.enabled=true and c.contractId=?1")
    List<ContractSchedule> findByContractId(long contractId);
}
