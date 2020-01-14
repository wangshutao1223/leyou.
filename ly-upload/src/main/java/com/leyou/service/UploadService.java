package com.leyou.service;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Service
public class UploadService {
    @Autowired
    private FastFileStorageClient client;
    public String uploadImage(MultipartFile file) {
//        //创建一个file对象
//        File f = new File("D:\\upload");
//        if(!f.exists()){
//            //创建文件夹
//            f.mkdirs();
//        }
//        //保存图片
//        try {
//            file.transferTo(new File(f,file.getOriginalFilename()));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return "http://image.leyou.com"+file.getOriginalFilename();

        // 上传并且生成缩略图
//        StorePath storePath = this.storageClient.uploadImageAndCrtThumbImage(
//                new FileInputStream(file), file.length(), "png", null);

        String url=null;
        //获取浏览器传过来的图片
        String originalFilename = file.getOriginalFilename();
        //获取图片后缀
        String ext = StringUtils.substringAfter(originalFilename, ".");//png
        //上传
        try {
            StorePath storePath =client.uploadFile(file.getInputStream(),file.getSize(),ext,null);
            storePath.getFullPath();
            url="http://image.leyou.com/"+storePath.getFullPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
            return url;
    }
}
