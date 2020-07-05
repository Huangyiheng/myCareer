/**
 * 作者:HYH
 * 功能:前台页面控制器
 * 时间:2020年3月18日
 */
package com.hyh.controller;

import com.maxwisdom.parallel.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author HYH
 * ConfigurationProperties:使用注解获取application.yml中的
 * 属性,person为配置文件中的对象名称,自动对下面的name变量进行赋值,可以不写@Value("${name}")
 */
@Controller
@RequestMapping("/file")
public class FileController {


    @Autowired
    private FileService fileService;

    @RequestMapping("/picture")
    public void picture(HttpServletResponse response, HttpSession session, HttpServletRequest request,
                        @RequestParam(value = "fileid", required = false) long fileid) {
        fileService.picture(response, request, fileid);
    }

    @RequestMapping("/video")
    public void video(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "fileid", required = false) long fileid) {
        fileService.video(response, request, fileid);
    }
}