package com.leyou.item.service;

import com.leyou.item.entity.Category;
import com.leyou.item.mapper.CategroyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategroyService {

    @Autowired
    private CategroyMapper categroyMapper;

    public List<Category> queryByParentId(Long id) {
        Category category = new Category();
        category.setParentId(id);
        return categroyMapper.select(category);
    }

    public List<Category> queryByBrandId(Long id) {
         return categroyMapper.queryByBrandId(id);
    }

    public List<String> queryNameByIds(List<Long> asList) {
        //74，75,76
        //select* from tb_Category where in(74,75,76);
        List<String> strings = new ArrayList<String>();
        List<Category> categoryList = this.categroyMapper.selectByIdList(asList);
        categoryList.forEach(t->{
            strings.add(t.getName());
        });
        return strings;
    }
}
