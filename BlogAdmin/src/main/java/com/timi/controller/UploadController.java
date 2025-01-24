package com.timi.controller;

import com.timi.service.UploadService;
import com.timi.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class UploadController {
    @Autowired
    public UploadService uploadService;

    @PostMapping("/upload")
    public ResponseResult uploadImg(MultipartFile img){
        try{
            return uploadService.uploadImg(img);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("文件上传失败");
        }
    }
}
