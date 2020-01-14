package com.leyou.item.entity.api;

import com.leyou.item.entity.Category;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface CategroyApi {
    @GetMapping("category/list")
    public List<Category> queryByParentId(@RequestParam("pid") Long id);

    /**
     * 回显
     * @param id
     * @return
     */
    @GetMapping("category/bid/{bid}")
    public  List<Category> queryByBrandId(@PathVariable("bid") Long id);

    /**
     *根据商品ID查名称
     */

    @GetMapping("category/names")
    public List<String> queryNameByIds(@RequestParam("ids") List<Long> ids);
}
