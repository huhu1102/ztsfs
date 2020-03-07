package com.zt.dao;


import com.zt.po.ContractFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "contractFile", path="contractFile")
public interface ContractFileDao extends JpaRepository<ContractFile,Long> {

    /*
    自定义分页查询
     */
    @Query(value="select * from zt_contractfile c where c.enabled=true",nativeQuery=true)
    Page<ContractFile> findSearch(Pageable pageable);

    @Query("select c from ContractFile c where c.id=?1 and c.enabled=true")
    ContractFile findById(long id);
}
