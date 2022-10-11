package com.wjy.controller;

import com.wjy.common.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * @Author : 王金云
 * @create 2022/7/25 16:13
 */
@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {
    @Value("${ddining.imgpath}")
    private String imgpath;

    @PostMapping("/upload")
    public Message<String> upload(MultipartFile file){
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString();

        File dir = new File(imgpath);
        if (!dir.exists()){
            dir.mkdirs();
        }

        try {
            file.transferTo(new File(imgpath+fileName+suffix));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return Message.success(fileName+suffix);
    }
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){
        FileInputStream fileInputStream = null;
        ServletOutputStream outputStream = null;
        try {
            fileInputStream = new FileInputStream(new File(imgpath+name));
            outputStream = response.getOutputStream();
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = fileInputStream.read(bytes))!=-1){
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
                try {
                    if (fileInputStream!=null)
                        fileInputStream.close();

                    if (outputStream!=null)
                        outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }


    }


}
