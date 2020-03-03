package com.zt.service;


import com.zt.model.ResultObject;
import com.zt.po.UploadFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface UploadFileService {

    String addFile(HttpServletRequest request) throws IOException, ServletException;

    void findAll(String id, HttpServletRequest request, HttpServletResponse response) throws IOException;

    void downloadFile(String fileId, HttpServletRequest request, HttpServletResponse response) throws Exception;

    ResultObject<UploadFile> deleteUpload(long id)throws Exception;

    List<UploadFile> findByContractId(long contractId)throws Exception;
}
