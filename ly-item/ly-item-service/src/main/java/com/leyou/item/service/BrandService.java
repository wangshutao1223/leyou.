package com.leyou.item.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leyou.item.entity.Brand;
import com.leyou.item.mapper.BrandMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import utils.PageResult;

import java.util.List;

@Service
public class BrandService {

    @Autowired
    private BrandMapper brandMapper;

    public PageResult<Brand> pageQuery(Integer page, Integer rows, String sortBy, Boolean desc, String key) {
       //一般需要两条SQL
        //select count(*) from brand
        //select * from brand limit 0,5
        //开启分页助手
        PageHelper.startPage(page,rows);
        //查询条件
        Example example = new Example(Brand.class);
        if(StringUtils.isNoneBlank(key)){
            //获取criteria对象
            Example.Criteria criteria = example.createCriteria();
            criteria.andLike("name","%"+key+"%");
            //name like %key%

        }
        //排序
        if(StringUtils.isNotBlank(sortBy)){
            //order by sortBy desc
            example.setOrderByClause(sortBy+ (desc?" desc":" asc"));

        }
        //查询
       Page<Brand> brandPage =(Page<Brand>) brandMapper.selectByExample(example);
        return new PageResult<>(brandPage.getTotal(),new Long(brandPage.getPages()),brandPage.getResult());
    }
    //新增
    @Transactional
    public void addBrand(Brand brand, List<Long> cids) {
        //插入tb_brand表
            brandMapper.insertSelective(brand);
            //插入中间表tb_category_brand
            for(Long i:cids){
                brandMapper.insertBrandCategory(i,brand.getId());
            }

    }

    @Transactional
    public void updateBrand(Brand brand, List<Long> cids) {
        //更新tb_brand表
        this.brandMapper.updateByPrimaryKey(brand);
        //删除关系表tb_catagory_brand 然后再添加
        this.brandMapper.deleteCategoryBrand(brand.getId());
        //添加关系表
        cids.forEach(t->{
            brandMapper.insertBrandCategory(t,brand.getId());
        });
    }

    public List<Brand> queryBrandByCategory(Long id) {
    return     brandMapper.queryBrandByCategory(id);

    }
}
