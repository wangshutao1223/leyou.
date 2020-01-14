package com.leyou.controller;

import com.leyou.pojo.Goods;
import com.leyou.service.SearchService;
import com.leyou.utils.SearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import utils.PageResult;

@RestController
public class SearchController {
    @Autowired
    private SearchService searchService;

    @PostMapping("page")
    public ResponseEntity<PageResult<Goods>> search(@RequestBody SearchRequest searchRequest){
       PageResult<Goods> result= searchService.search(searchRequest);
       if(null!=result&&result.getItems().size()>0){
           return  ResponseEntity.ok(result);
       }
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }



}
