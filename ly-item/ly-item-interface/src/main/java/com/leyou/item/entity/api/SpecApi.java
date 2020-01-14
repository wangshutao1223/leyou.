package com.leyou.item.entity.api;

import com.leyou.item.entity.SpecGroup;
import com.leyou.item.entity.SpecParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface SpecApi {
    @GetMapping("spec/groups/{cid}")
    public List<SpecGroup> querySpecGroups(@PathVariable("cid") Long cid);

    @GetMapping("spec/params")
    public  List<SpecParam> querySpecParam(@RequestParam(value = "gid",required = false) Long gid,
                                                           @RequestParam(value = "cid",required = false) Long cid,
                                                           @RequestParam(value = "searching",required = false) Boolean searching,
                                                           @RequestParam(value = "generic",required = false) Boolean generic);
    @PostMapping("spec/param")
    public Void insertSpecParam(@RequestBody SpecParam specParam/*,@RequestParam("numeric") Boolean numeric,
                                                @RequestParam("searching") Boolean searching,
                                                @RequestParam("generic") Boolean generic*/
    );

    /**
     * 新增分组
     */
    @PostMapping("spec/group/{cid}")
    public  Void insertGroup(@PathVariable("cid") Long cid,@RequestParam("name") String name);

    @PutMapping("spec/param")
    public  Void updateSpec(@RequestBody SpecParam specParam);

    @DeleteMapping("spec/param/{id}")
    public  Void deleteSpec(@PathVariable("id") Long id);

    @PostMapping("spec/group")
    public  Void insertSpecGroup(@RequestBody SpecGroup specGroup);

    @PutMapping("spec/group")
    public  Void updateSpecGroup(@RequestBody SpecGroup specGroup);
    @DeleteMapping("spec/group/{id}")
    public  Void deleteSpecGroup(@PathVariable("id") Long id);

}
