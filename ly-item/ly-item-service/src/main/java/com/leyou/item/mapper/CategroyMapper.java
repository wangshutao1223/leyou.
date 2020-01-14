package com.leyou.item.mapper;

import com.leyou.item.entity.Category;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.ids.SelectByIdsMapper;

import java.util.List;

public interface CategroyMapper extends Mapper<Category>, SelectByIdListMapper<Category,Long> {

    @Select("select c.* from tb_category c,tb_category_brand cb where c.id=cb.category_id and brand_id=#{id}")
    List<Category> queryByBrandId(@Param("id") Long id);
}
