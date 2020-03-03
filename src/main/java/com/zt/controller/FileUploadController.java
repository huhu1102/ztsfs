package com.zt.controller;

import java.io.*;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import com.mongodb.gridfs.GridFSDBFile;
//import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
//import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import com.zt.model.ResultObject;
import com.zt.po.UploadFile;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.bson.Document;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mongodb.MongoClient;

@RestController
@RequestMapping("/files")
public class FileUploadController {
    private SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
    // 获得SpringBoot提供的mongodb的GridFS对象
    @Autowired
    private GridFsTemplate gridFsTemplate;
    @Autowired
    private MongoTemplate mongoTemplate;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    // 工程主页
//    @RequestMapping(value = "/", method = RequestMethod.GET)
//    String home(Model model) {
//        return "uploadForm";
//    }

    // 上传文件控制器
    @RequestMapping(value = "/uploadfile", method = RequestMethod.POST)
    @ResponseBody
    public String uploadfile(HttpServletRequest request) {
        String result = "error";
        try {
            /**
             * Servlet3.0新增了request.getParts()/getPart(String filename) api，
             * 用于获取使用multipart/form-data格式传递的http请求的请求体， 通常用于获取上传文件。
             */
            //获取文件需要上传到的路径
            String str = request.getSession().getServletContext().getRealPath("");
//            String filename = "test";

            Part part = request.getPart("file");
            String filename = part.getSubmittedFileName();
            // 获得文件输入流
            InputStream ins = part.getInputStream();
            // 获得文件类型
            String contenttype = part.getContentType();
            // 将文件存储到mongodb中,mongodb 将会返回这个文件的具体信息
            ObjectId store = gridFsTemplate.store(ins, filename, contenttype);

            result = store.toString();

        } catch (IOException e) {
        } catch (ServletException e) {
            e.printStackTrace();
        }
        return result;
    }

    // 下载文件控制器
    @RequestMapping(value = "/downloadfile", method = RequestMethod.GET)
    @ResponseBody
    String downloadfile(@RequestParam(name = "fname", required = true) String filename, HttpServletResponse response) {

        try {

            /**
             * 关于Query的具体用发下面的链接给的很清楚了，这里就不多说了。
             *
             * @link{http://www.baeldung.com/queries-in-spring-data-mongodb}
             */
            Query query = Query.query(Criteria.where("filename").is(filename));
            logger.info(query.toString());
            logger.info(filename);
            // 查询单个文件
//        GridFSDBFile gfsfile = gridFsTemplate.findOne(query);
            GridFSFile one = gridFsTemplate.findOne(query);
            logger.info(one.toString());
            OutputStream out = response.getOutputStream();
            StringBuilder sb =new StringBuilder();
            // 通知浏览器进行文件下载
            response.setContentType("image/jpg");
            response.setHeader("Content-Disposition", "attachment;filename=" + one.getFilename());
            response.setCharacterEncoding("utf-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        return "success";
    }


    private static final class MongoInstance {
        public final static MongoClient client;
        static {
            client = new MongoClient("192.168.0.175", 27017);
        }
    }

    public static MongoDatabase getDatabase(String databaseName) {
        return MongoInstance.client.getDatabase(databaseName);
    }

    /**
     * 上传文件
     * @param file
     * @param databaseName
     * @return
     */
    @RequestMapping(value = "/upmgfile", method = RequestMethod.POST)
    @ResponseBody
    public static String uploadFileToGridFS(File file,String databaseName) {
        InputStream in = null;
        String returnId = null;
        String pathName = file.getPath();
        String[] pathNameArray = pathName.split("\\\\");
        String[] name = pathNameArray[pathNameArray.length-1].split("\\.");
        String filename = name[0];
        String type = name[name.length-1];
        try {
            in = new FileInputStream(file);
            GridFSBucket bucket = GridFSBuckets.create(getDatabase(databaseName));
            GridFSUploadOptions options = new GridFSUploadOptions();
            //设置除filename以为的其他信息
            Document metadata = new Document();
            metadata.append("contentType", type);
            options.metadata(metadata);
            ObjectId fileId = bucket.uploadFromStream(filename+type, in,options);
            returnId = fileId.toString();
            System.out.println("文件上传成功");
        } catch (IOException e) {
            System.out.println("upload fail:" + e);
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                System.out.println("close inputstream fail:" + e);
        }
        }
        return returnId;
    }


//    @RequestMapping(value = "/file/image/{id}",produces = {MediaType.IMAGE_JPEG_VALUE,MediaType.IMAGE_PNG_VALUE})
//    public byte[] image(@PathVariable String id){
//        byte[] data = null;
//        UploadFile file = mongoTemplate.findImageById(id, UploadFile.class);
//        if(file != null){
//            data = file.getContent().getData();
//        }
//        return data;
//    }

}
