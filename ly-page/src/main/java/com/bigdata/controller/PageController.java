package com.bigdata.controller;


import com.bigdata.service.FileService;
import com.bigdata.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PageController {

    @Autowired
    private PageService pageService;
    @Autowired
    private FileService fileService;

    @GetMapping("item/{id}.html")
    public String toPage(@PathVariable("id") Long spuId, Model model){

        model.addAllAttributes(pageService.loadData(spuId));
//        model.addAttribute("spu",spu);
//        model.addAttribute("spuDetail",spuDetail);
        //112.html不存在
        if(!fileService.exists(spuId)){
            fileService.syncCreateHtml(spuId);
        }
        return  "item";

    }



}
