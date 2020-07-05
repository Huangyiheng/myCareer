package com.hyh.service;

import com.maxwisdom.parallel.dao.FilePropertiesDao;
import com.maxwisdom.parallel.entity.FileProperties;
import com.maxwisdom.parallel.utility.UtilityHyh;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
public class FileServiceimpl implements FileService {
    @Autowired
    private FilePropertiesDao filedao;
//返回图片
    public void picture(HttpServletResponse response ,HttpServletRequest request,  long fileid) {

        try {
            FileProperties picFile = filedao.findById(fileid).get();
            //读取指定路径下面的文件
            InputStream in = new FileInputStream(picFile.getPath());
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            //创建存放文件内容的数组
            byte[] buff = new byte[1024];
            //所读取的内容使用n来接收
            int n;
            //当没有读取完时,继续读取,循环
            while ((n = in.read(buff)) != -1) {
                //将字节数组的数据全部写入到输出流中
                outputStream.write(buff, 0, n);
            }
            //强制将缓存区的数据进行输出
            outputStream.flush();
            //关流
            outputStream.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
//返回视频
    public void video(HttpServletResponse response, HttpServletRequest request, long fileid )  {
        FileProperties picFile = filedao.findById(fileid).get();
        try {
            File file = new File(picFile.getPath());

            RandomAccessFile randomFile = new RandomAccessFile(file, "r");//只读模式
            long contentLength = randomFile.length();
            String range = request.getHeader("Range");
            int start = 0, end = 0;
            if(range != null && range.startsWith("bytes=")){
                String[] values = range.split("=")[1].split("-");
                start = Integer.parseInt(values[0]);
                if(values.length > 1){
                    end = Integer.parseInt(values[1]);
                }
            }
            int requestSize = 0;
            if(end != 0 && end > start){
                requestSize = end - start + 1;
            } else {
                requestSize = Integer.MAX_VALUE;
            }

            byte[] buffer = new byte[4096];
            response.setContentType("video/mp4");
            response.setHeader("Accept-Ranges", "bytes");
            response.setHeader("ETag", picFile.getName());
            response.setHeader("Last-Modified", new Date().toString());
            //第一次请求只返回content length来让客户端请求多次实际数据
            if(range == null){
                response.setHeader("Content-length", contentLength + "");
            }else{
                //以后的多次以断点续传的方式来返回视频数据
                response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);//206
                long requestStart = 0, requestEnd = 0;
                String[] ranges = range.split("=");
                if(ranges.length > 1){
                    String[] rangeDatas = ranges[1].split("-");
                    requestStart = Integer.parseInt(rangeDatas[0]);
                    if(rangeDatas.length > 1){
                        requestEnd = Integer.parseInt(rangeDatas[1]);
                    }
                }
                long length = 0;
                if(requestEnd > 0){
                    length = requestEnd - requestStart + 1;
                    response.setHeader("Content-length", "" + length);
                    response.setHeader("Content-Range", "bytes " + requestStart + "-" + requestEnd + "/" + contentLength);
                }else{
                    length = contentLength - requestStart;
                    response.setHeader("Content-length", "" + length);
                    response.setHeader("Content-Range", "bytes "+ requestStart + "-" + (contentLength - 1) + "/" + contentLength);
                }
            }
            ServletOutputStream out = response.getOutputStream();
            int needSize = requestSize;
            randomFile.seek(start);
            while(needSize > 0){
                int len = randomFile.read(buffer);
                if(needSize < buffer.length){
                    out.write(buffer, 0, needSize);
                } else {
                    out.write(buffer, 0, len);
                    if(len < buffer.length){
                        break;
                    }
                }
                needSize -= buffer.length;
            }
            randomFile.close();
            out.close();
















        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //上传文件
    public FileProperties fileUpload(MultipartFile file) {
        FileProperties fileproperties = new FileProperties();
        System.out.println(fileproperties.getUploaddir());
        //文件存储位置
        //  String filepath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\file\\";
      String filepath = "D:\\maxwisdomfile\\";
//             String filepath = "/usr/file/";
        String fileName;
        Map<String, String> error = new HashMap<String, String>();

        File filed = new File(filepath);
        if(!filed.exists()){     //判断文件路径是否存在
            filed.mkdirs();              //不存在创建新的文件
        }

        if (file == null) {
            fileproperties.setUploadstatus("error");//上传结果
            return fileproperties;
        } else {
            fileName = file.getOriginalFilename();


            File localFile = new File(filepath);
            if (!localFile.exists()) {
                localFile.mkdirs();
            }
            //路径+随机名称+文件原名称
            String random = UtilityHyh.generateRandomFilename();
            String path = filepath + random + fileName;
            System.out.println("文件存储路径:" + path);
            try {
                File server_file = new File(path);
                file.transferTo(server_file);
                fileproperties.setName(fileName);//存储文件名称至数据库对象
                fileproperties.setPath(path);//路径
                fileproperties.setLenth(file.getSize() + "");
                fileproperties.setSkeyname(random + fileName);//存储文件名
                fileproperties.setType(file.getContentType());//文件类型
                fileproperties.setCreatedtime(new Date());//上传时间
                fileproperties.setUploadstatus("success");//上传结果
                //使用
                // error.put("", "Success");
                //保存数据库
                filedao.save(fileproperties);
            } catch (Exception e) {
                if (file != null) {

                    fileproperties.setName(fileName);//存储文件名称至数据库对象
                    fileproperties.setLenth(file.getSize() + "");
                    fileproperties.setType(file.getContentType());//文件类型
                    fileproperties.setPath(path);//路径
                    fileproperties.setSkeyname(UtilityHyh.generateRandomFilename() + fileName);//存储文件名
                    fileproperties.setCreatedtime(new Date());//上传时间
                    fileproperties.setUploadstatus("error");//上传结果
                }
                filedao.save(fileproperties);
                error.put("error", "Upload fail");
                System.out.println("文件上传异常");
            }
        }

        return fileproperties;
    }
    /*删除文件*/
    public boolean filedelete(String path) {

        File file = new File(path);// 读取

        if (file.exists()&&file.isFile()) {// 判断存在并且是文件
            file.delete();
            System.out.println("文件已经被成功删除");
            return true;
        }else if (!file.exists()){
            return true;
        }else{
            return  false;

        }
    }




}
