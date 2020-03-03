package com.zt.serviceImp;

import java.awt.Image;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.zt.dao.UploadFileDao;
import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.po.UploadFile;
import com.zt.service.UploadFileService;
import org.apache.poi.util.IOUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

@Service
public class UploadFileServiceImp implements UploadFileService {
    @Autowired
    GridFsTemplate gridFsTemplate;
    @Autowired
    UploadFileDao uploadFileDao;
    @Autowired
    private MongoDbFactory mongoDbFactory;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String IMAGE = "image";
    private static final String FILE = "file";
    private static final String URL ="http://localhost:8082/uploadfile/file/image/";

    /*
    新增附件文件
     */
    @Override
    public String addFile(HttpServletRequest request) throws IOException, ServletException {
        UploadFile uploadFile = new UploadFile();
        StringBuilder url = new StringBuilder();
        DBObject metaData = new BasicDBObject();
        Part part = request.getPart("file");
        String filename = part.getSubmittedFileName(); //           getOriginalFilename();
        // 把时间戳作为文件名存入mongodb
        String fileName = String.valueOf(System.currentTimeMillis());
        ObjectId storeId;

        try {
            InputStream inputStream = part.getInputStream();
            String conType=filename.substring(filename.lastIndexOf(".")+1);
            String reg = "\\.(jpg|gif|bmp|jpeg)";
            String fileType;
            if(conType.matches(reg)) {
                fileType=IMAGE;
            }else {
                fileType=FILE;
            }
            storeId = gridFsTemplate.store(inputStream, fileName, fileType, metaData);
            url.append(URL).append(storeId);
            uploadFile.setName(filename);
            uploadFile.setCreatedTime(new Date());
            uploadFile.setSize(part.getSize());
            uploadFile.setContentType(fileType);
            uploadFile.setEnabled(true);
            uploadFile.setUrl(url.toString());
            uploadFile.setEnabled(true);
            uploadFile = uploadFileDao.saveAndFlush(uploadFile);
            if(uploadFile!=null){
                logger.info("图片添加成功~~");
            }


        } catch (IOException e) {
            throw new RuntimeException();
        }
        return url.toString(); //该storeId 为上传的图片的Id
    }

    //查看某个文件
    @Override
    public void findAll(String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
//    	String fileId="5da92d2694b2db00d4fcba61";
        GridFSFile gridFSFile = this.getById(id);
//        String  type="jpg";
        if (gridFSFile != null) {
            // mongo-java-driver3.x以上的版本就变成了这种方式获取
            GridFSBucket bucket = GridFSBuckets.create(mongoDbFactory.getDb());
            GridFSDownloadStream gridFSDownloadStream = bucket.openDownloadStream(gridFSFile.getObjectId());
            GridFsResource gridFsResource = new GridFsResource(gridFSFile,gridFSDownloadStream);
            String fileName = gridFSFile.getFilename().replace(",", "");
            //处理中文文件名乱码
            if (request.getHeader("User-Agent").toUpperCase().contains("MSIE") ||
                    request.getHeader("User-Agent").toUpperCase().contains("TRIDENT")
                    || request.getHeader("User-Agent").toUpperCase().contains("EDGE")) {
                fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
            } else {
                //非IE浏览器的处理：
                fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
            }
            response.setHeader("Content-Disposition", "inline;filename=\"" + fileName + "\"");
            IOUtils.copy(gridFsResource.getInputStream(), response.getOutputStream());
            System.out.println(response.toString());
        }

    }

    @Override
    public List<UploadFile> findByContractId(long contractId) throws Exception {
        return uploadFileDao.findByContractId(contractId);
    }

    /**
     * 下载
     *
     * @param fileId   文件id
     * @param response
     * @return
     */
    @Override
    public void downloadFile(String fileId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Query query = Query.query(Criteria.where("_id").is(fileId));
        // 查询单个文件
        GridFSFile gfsFile = gridFsTemplate.findOne(query);
        GridFSBucket bucket = GridFSBuckets.create(mongoDbFactory.getDb());
        GridFSDownloadStream gridFSDownloadStream = bucket.openDownloadStream(gfsFile.getObjectId());

        GridFsResource gridFsResource=new GridFsResource(gfsFile,gridFSDownloadStream);
        String fileName = gfsFile.getFilename().replace(",", "");
        //处理中文文件名乱码
        if (request.getHeader("User-Agent").toUpperCase().contains("MSIE") ||
                request.getHeader("User-Agent").toUpperCase().contains("TRIDENT")
                || request.getHeader("User-Agent").toUpperCase().contains("EDGE")) {
            fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
        } else {
            //非IE浏览器的处理：
            fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
        }
        // 通知浏览器进行文件下载
        response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
        IOUtils.copy(gridFsResource.getInputStream(),response.getOutputStream());
    }


    private GridFSFile getById(String fileId){
        Query query = Query.query(Criteria.where("_id").is(fileId));
        GridFSFile gfsFile = gridFsTemplate.findOne(query);
        return gfsFile;
    }
    /*
    删除
     */
    @Override
    public ResultObject<UploadFile> deleteUpload(long id) throws Exception {
        ResultObject<UploadFile> ro = new ResultObject<>();
        UploadFile byId = uploadFileDao.findById(id);
        if (byId!=null) {
            byId.setEnabled(false);
            uploadFileDao.saveAndFlush(byId);
            ro.setSuccess(true);
            ro.setMsg("操作成功");
        }else {
            ro.setSuccess(false);
            ro.setMsg("操作失败");
            throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
        }
        return ro;
    }
}
