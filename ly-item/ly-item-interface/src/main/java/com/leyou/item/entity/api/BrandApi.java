package com.leyou.item.entity.api;

import com.leyou.item.entity.Brand;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utils.PageResult;

import java.util.List;

public interface BrandApi {

    @GetMapping("brand/page")
    public PageResult<Brand> pageQuery(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                                       @RequestParam(value = "rows",defaultValue = "5") Integer rows,
                                                       @RequestParam(value = "sortBy",required = false) String sortBy,
                                                       @RequestParam(value = "desc",required = false) Boolean desc,
                                                       @RequestParam(value = "key",required = false) String key);
    @PostMapping
    public  Void addBrand(Brand brand, @RequestParam("cids") List<Long> cids);

    @PutMapping
    public  Void updateBrand(Brand brand,@RequestParam("cids") List<Long> cids);


    /**
     * 查询分类品牌
     */
    @GetMapping("brand/cid/{cid}")
    public  List<Brand> queryBrandByCategory(@PathVariable("cid") Long id);
}
