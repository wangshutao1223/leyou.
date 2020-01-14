package com.bigdata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

@Service
public class FileService {

    @Value("${ly.thymeleaf.destPath}")
    private String destPath;

    @Autowired
    private PageService pageService;

    @Autowired
    private TemplateEngine templateEngine; //模板引擎

    //判断D:/nginx-1.14.2/html/item 有没有112.html..
    public boolean exists(Long spuId) {

        File file = new File(destPath);
        if(!file.exists()){
            //创建文件夹
            file.mkdirs();

        }
        return  new File(file,spuId+".html").exists();
    }

    public void syncCreateHtml(Long spuId) {
        //创建上下文对象
        Context context = new Context();
        //放入数据
        context.setVariables(pageService.loadData(spuId));

        //创建一个文件对象
      File file= new File(destPath,spuId+".html");
      //输出流
        try {
            PrintWriter printWriter = new PrintWriter(file,"utf-8");
            //产生静态文件
            templateEngine.process("item",context,printWriter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteHtml(Long id) {
        File file = new File(destPath, id + ".html");
        file.deleteOnExit();
    }
}
