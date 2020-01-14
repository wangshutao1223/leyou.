package com.leyou.item.entity.api;

import com.leyou.item.entity.Sku;
import com.leyou.item.entity.Spu;
import com.leyou.item.entity.SpuDetail;
import com.leyou.item.entity.bo.SpuBo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utils.PageResult;

import java.util.List;

public interface GoodsApi {
    @GetMapping("spu/page")
    public PageResult<SpuBo> querySpuByPage(@RequestParam(value = "key",required = false) String key,
                                            @RequestParam(value = "saleable",required = false) String saleable,
                                            @RequestParam(value = "page",required = false) Integer page,
                                            @RequestParam(value = "rows",required = false) Integer rows);

    //发送http://item-service/spu/page
    //发送http://127.0.0.1:9081/spu/page

    @PostMapping
    public Void saveGoods(@RequestBody SpuBo spuBo);


    @GetMapping("spu/detail/{spuId}")
    public SpuDetail querySpuDetailBySpuId(@PathVariable("spuId") Long id);

    //sku/list?id=195
    @GetMapping("sku/list")
    public List<Sku> querySkuBySpuId(@RequestParam("id") Long id);

    /**
     * 修改
     * @return
     */
    @PutMapping("goods")
    public  Void updateGoods(@RequestBody SpuBo spuBo);

    /**
     * 根据spuiD查询spu
     */

    @GetMapping("spu/{id}")
    public  Spu querySpuById(@PathVariable("id") Long spuId);
}
