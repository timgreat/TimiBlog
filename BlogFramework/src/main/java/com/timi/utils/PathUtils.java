package com.timi.utils;

import org.junit.jupiter.api.DisplayNameGenerator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class PathUtils {
    public static String genetateFilePath(String fileName){
        //根据日期生成路径
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy/MM/dd");
        String datePath=simpleDateFormat.format(new Date());
        //生成uuid
        String uuid= UUID.randomUUID().toString().replaceAll("-","");
        //文件后缀
        String fileType=fileName.substring(fileName.lastIndexOf("."));
        return new StringBuilder().append(datePath).append(uuid).append(fileType).toString();
    }
}
