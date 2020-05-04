package com.chen.utils;

import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;

/**
 * 上传文件的工具类
 */
public class UploadUtils {
    public static  String fileName(String path, MultipartFile multipartFile) {
        //文件名
        String fileName;
        //文件上传的路径
        File file = new File(path);
        //判断该路径是否存在，不存在创建
        if (!file.exists()){
            file.mkdirs();
        }
        //获取上传文件的名称
        fileName = multipartFile.getOriginalFilename();
        //截取文件名，因为部分浏览器上传的是绝对路径
        int index = fileName.lastIndexOf("\\");
        if (index !=-1){
            fileName = fileName.substring(index+1);
        }
        //设置文件名为唯一值,避免文件同名的现象
        fileName = CommonUtils.uuid()+"-"+fileName;
//        //校验文件名称的扩展名,是否为jpg或png
//        if(!fileName.toLowerCase().endsWith(".jpg")|| !fileName.toLowerCase().endsWith(".png")){
//            System.out.println("上传的图片名称扩展名必须是jpg或png");
//            throw new UserException("上传的图片名称扩展名必须是jpg或png");
//        }
        //完成文件上传
        try {
            multipartFile.transferTo(new File(path, fileName));
            return fileName;
        } catch (IOException e) {
            System.out.println("上传文件失败！");
            return null;
        }


    }
}
