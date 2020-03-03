package com.zt.controller;

import com.zt.model.ResultObject;
import com.zt.po.UploadFile;
import com.zt.service.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/uploadfile")
public class UploadFileController {

    @Autowired
    UploadFileService uploadFileService;
    /*上传*/
    @RequestMapping(value = "/file/upload",method = RequestMethod.POST)
    public String addFile(HttpServletRequest request) throws IOException, ServletException {
        return uploadFileService.addFile(request);
    }
    //查看某个文件
    @GetMapping(value = "/file/image/{id}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public void findAll(@PathVariable String id,HttpServletRequest request,  HttpServletResponse response) throws IOException {
        uploadFileService.findAll(id, request, response);
    }
    /*
    根据合同id查找附件信息
     */
    @RequestMapping(value = "/findbycontract")
    public List<UploadFile> findByContractId(long contractId) throws Exception{
        return uploadFileService.findByContractId(contractId);
    }

   /*
   下载
    */
    @RequestMapping(value = "/downfile")
    public void downloadFile(@RequestParam(name = "file_id") String fileId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        uploadFileService.downloadFile(fileId, request, response);
    }
    /*
    删除附件方法
     */
    @RequestMapping(value="/delete",method=RequestMethod.GET)
    public ResultObject<UploadFile> deletEmp(long id) throws Exception {
        return uploadFileService.deleteUpload(id);
    }
}
