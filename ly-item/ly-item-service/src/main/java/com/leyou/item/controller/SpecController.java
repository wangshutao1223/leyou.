package com.leyou.item.controller;

import com.leyou.item.entity.SpecGroup;
import com.leyou.item.entity.SpecParam;
import com.leyou.item.mapper.SpecGroupMapper;
import com.leyou.item.service.SpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("spec")
public class SpecController {

    @Autowired
    private SpecService specService;

    private SpecGroupMapper specGroupMapper;
    /**
     * 分类参数
     * @param cid
     * @return
     */
    @GetMapping("groups/{cid}")
    public ResponseEntity<List<SpecGroup>> querySpecGroups(@PathVariable("cid") Long cid){
          List<SpecGroup> groupList= specService.querySpecGroups(cid);
          if(null!=groupList&&groupList.size()>0){
              return ResponseEntity.ok(groupList);
          }
          return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("params")
    public  ResponseEntity<List<SpecParam>> querySpecParam(@RequestParam(value = "gid",required = false) Long gid,
                                                           @RequestParam(value = "cid",required = false) Long cid,
                                                           @RequestParam(value = "searching",required = false) Boolean searching,
                                                           @RequestParam(value = "generic",required = false) Boolean generic){
      List<SpecParam> paramList= specService.querySpecParam(gid,cid,searching,generic);
      if(null!=paramList&&paramList.size()>0){
          return  ResponseEntity.ok(paramList);
      }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }
    @PostMapping("param")
    public ResponseEntity<Void> insertSpecParam(@RequestBody SpecParam specParam/*,@RequestParam("numeric") Boolean numeric,
                                                @RequestParam("searching") Boolean searching,
                                                @RequestParam("generic") Boolean generic*/
                                                 ){
        specService.insertSpecParam(specParam/*,numeric,searching,generic*/);
        return  ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 新增分组
     */
    @PostMapping("group/{cid}")
    public  ResponseEntity<Void> insertGroup(@PathVariable("cid") Long cid,@RequestParam("name") String name){
        specService.insertGroup(cid,name);
        return  ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("param")
    public  ResponseEntity<Void> updateSpec(@RequestBody SpecParam specParam){
        specService.updateSpec(specParam);
        return  ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("param/{id}")
    public  ResponseEntity<Void> deleteSpec(@PathVariable("id") Long id){
            specService.deleteSpec(id);
            return  ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PostMapping("group")
    public  ResponseEntity<Void> insertSpecGroup(@RequestBody SpecGroup specGroup){
            specService.insertSpecGroup(specGroup);
            return  ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PutMapping("group")
    public  ResponseEntity<Void> updateSpecGroup(@RequestBody SpecGroup specGroup){
            specService.updateSpecGroup(specGroup);
        return  ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("group/{id}")
    public  ResponseEntity<Void> deleteSpecGroup(@PathVariable("id") Long id){
        specService.deleteSpecGroup(id);
        return  ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
