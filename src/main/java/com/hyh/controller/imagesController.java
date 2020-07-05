package com.hyh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;

@Controller
@RequestMapping("/images")
public class imagesController {


    /*返回后台管理页面*/
    @RequestMapping("/back")
    public String back() {
        return "back/images";
    }


    @RequestMapping("stream")
    public void getStreamData(HttpServletResponse response) {
        File file = new File("C:\\avi_vedio\\test.MP4");
        ServletOutputStream out = null;
        try {
            FileInputStream instream = new FileInputStream(file);
            byte[] b = new byte[1024];
            int length = 0;
            BufferedInputStream buf = new BufferedInputStream(instream);
            out = response.getOutputStream();
            BufferedOutputStream bot = new BufferedOutputStream(out);
            while ((length = buf.read(b)) != -1) {
                bot.write(b, 0, b.length);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
