package com.hyh.service;

import com.maxwisdom.parallel.entity.FileProperties;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface FileService {
    public void picture(HttpServletResponse response, HttpServletRequest request, long fileid);

    public void video(HttpServletResponse response, HttpServletRequest request, long fileid);
    public boolean filedelete(String path) ;
    public FileProperties fileUpload(MultipartFile file) ;
}
