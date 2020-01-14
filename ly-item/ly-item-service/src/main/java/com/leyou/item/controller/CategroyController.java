package com.leyou.item.controller;

import com.leyou.item.entity.Brand;
import com.leyou.item.entity.Category;
import com.leyou.item.service.CategroyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategroyController {

    @Autowired
    private CategroyService categroyService;

    @GetMapping("list")
    public ResponseEntity<List<Category>> queryByParentId(@RequestParam("pid") Long id){
          List<Category> categoryList=  categroyService.queryByParentId(id);

          if(null!=categoryList&&categoryList.size()>0){
                return ResponseEntity.ok(categoryList);
          }
          return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    /**
     * 回显
     * @param id
     * @return
     */
    @GetMapping("bid/{bid}")
    public  ResponseEntity<List<Category>> queryByBrandId(@PathVariable("bid") Long id){
          List<Category> categoryList=  categroyService.queryByBrandId(id);
          if(categoryList!=null&&categoryList.size()>0){
              return  ResponseEntity.ok(categoryList);
          }
          return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    /**
     *根据商品ID查名称
     */

    @GetMapping("names")
    public ResponseEntity<List<String>> queryNameByIds(@RequestParam("ids") List<Long> ids){

    List<String> list=  categroyService.queryNameByIds(ids);

    if(list!=null&&list.size()>0){
        return  ResponseEntity.ok(list);
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
