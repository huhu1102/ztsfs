package com.zt.dao;

import com.zt.po.ContractCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;


@RepositoryRestResource(collectionResourceRel = "contractcode", path="contractcode")
public interface ContractCodeDao extends JpaRepository<ContractCode,Long> {
    @Query("select c from ContractCode c where c.enabled=true")
    List<ContractCode> findAll();

    @Query("select c from ContractCode c where c.enabled=true and c.id=?1")
    ContractCode findById(long id);
}
