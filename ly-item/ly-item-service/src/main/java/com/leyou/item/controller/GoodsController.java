package com.leyou.item.controller;

import com.leyou.item.entity.Sku;
import com.leyou.item.entity.Spu;
import com.leyou.item.entity.SpuDetail;
import com.leyou.item.entity.bo.SpuBo;
import com.leyou.item.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utils.PageResult;

import java.util.List;

@RestController
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    //key=&saleable=true&page=1&rows=5
    @GetMapping("spu/page")
    public ResponseEntity<PageResult<SpuBo>> querySpuByPage(@RequestParam(value = "key",required = false) String key,
                                                            @RequestParam(value = "saleable",required = false) String saleable,
                                                            @RequestParam(value = "page",required = false) Integer page,
                                                            @RequestParam(value = "rows",required = false) Integer rows){
           PageResult<SpuBo> pageResult= goodsService.querySpuByPage(key,saleable,page,rows);
        if(pageResult!=null && pageResult.getItems().size()>0){
            return ResponseEntity.ok(pageResult);
        }
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping
    public  ResponseEntity<Void> saveGoods(@RequestBody SpuBo spuBo){
             goodsService.saveGoods(spuBo);
            return  ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @GetMapping("spu/detail/{spuId}")
    public  ResponseEntity<SpuDetail> querySpuDetailBySpuId(@PathVariable("spuId") Long id){
        SpuDetail spuDetail= goodsService.querySpuDetailBySpuId(id);
        if(null!=spuDetail){
            return  ResponseEntity.ok(spuDetail);
        }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
     }

     //sku/list?id=195
    @GetMapping("sku/list")
    public  ResponseEntity<List<Sku>> querySkuBySpuId(@RequestParam("id") Long id){
       List<Sku> skuList= goodsService.querySkuBySpuId(id);
        if(skuList!=null && skuList.size()>0){
            return ResponseEntity.ok(skuList);
        }
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    /**
     * 修改
     * @return
     */
    @PutMapping("goods")
    public  ResponseEntity<Void> updateGoods(@RequestBody SpuBo spuBo){
        goodsService.updateGoods(spuBo);
        return  ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 根据spuid查询spu
     * @param spuId
     * @return
     */
    @GetMapping("spu/{id}")
    public  ResponseEntity<Spu> querySpuById(@PathVariable("id") Long spuId){
       Spu spu= goodsService.querySpuById(spuId);
       if (spu==null){
           return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
       }
       return ResponseEntity.ok(spu);
    }
}
