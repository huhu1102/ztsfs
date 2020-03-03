package com.zt.dao;

import com.zt.po.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "uploadfile", path="uploadfile")
public interface UploadFileDao extends JpaRepository<UploadFile, String> {

    UploadFile findById(long id);
     /*
    根据合同id查找附件
     */
    @Query(value="select up.* from  zt_uploadfile up JOIN zt_contract_uploadfile cu on cu.upload_id=up.id where up.enabled=true and cu.con_id=:contractId",nativeQuery = true)
    List<UploadFile> findByContractId(@Param("contractId")long contractId);
    /*
    根据url查找附件对象
     */
    @Query("from UploadFile u where u.enabled =true and u.url=?1")
    UploadFile findByUrl(String url);
}
