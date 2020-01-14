package com.leyou.item.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leyou.item.entity.*;
import com.leyou.item.entity.bo.SpuBo;
import com.leyou.item.mapper.*;
import com.netflix.discovery.converters.Auto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import utils.PageResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class GoodsService {

    @Autowired
    private AmqpTemplate amqpTemplate;


    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SpuDetailMapper spuDetailMapper;

    @Autowired
    private CategroyService categroyService;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private SkuMapper skuMapper;


    @Autowired
    private StockMapper stockMapper;


    public PageResult<SpuBo> querySpuByPage(String key, String saleable, Integer page, Integer rows) {

        //开启分页
        PageHelper.startPage(page,rows);

        //查询对象
        Example example=new Example(Spu.class);

        Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(key)){
            criteria.andLike("title","%"+key+"%");
        }
        if(null!=saleable){
            criteria.andEqualTo("saleable",saleable);
        }
        Page<Spu> spuPage = (Page<Spu>)this.spuMapper.selectByExample(example);
        List<Spu> result = spuPage.getResult();

        List<SpuBo> spuBos = new ArrayList<>();
        for (Spu spu:result){
            SpuBo spuBo = new SpuBo();
            BeanUtils.copyProperties(spu,spuBo);

            //获取分类名称
              List<String> names= this.categroyService.queryNameByIds(Arrays.asList(spu.getCid1(),spu.getCid2(),spu.getCid3()));
            //拼接
            String join = StringUtils.join(names, "/");
            //分类名称
            spuBo.setCname(join);

            //根据品牌id查询品牌
            Brand brand = brandMapper.selectByPrimaryKey(spu.getBrandId());
            spuBo.setBname(brand.getName());

            spuBos.add(spuBo);
        }
        return new PageResult<>(spuPage.getTotal(),new Long(spuPage.getPages()),spuBos);


    }

    @Transactional
    public void saveGoods(SpuBo spuBo) {
        spuBo.setSaleable(true);
        spuBo.setValid(true);
        spuBo.setCreateTime(new Date());
        //保存到数据库
        this.spuMapper.insertSelective(spuBo);

        Long id = spuBo.getId();

        SpuDetail spuDetail = spuBo.getSpuDetail();
        spuDetail.setSpuId(id);
        //保存到spuDetail表
        this.spuDetailMapper.insert(spuDetail);

        List<Sku> skus = spuBo.getSkus();
        //保存sku和库存
        saveSkus(spuBo,skus);

        //发送消息
        sendMassage(id,"insert"); //router.key=item.insert
    }

    private void saveSkus(SpuBo spuBo, List<Sku> skus) {
        for(Sku s:skus){
             s.setSpuId(spuBo.getId());
             s.setCreateTime(new Date());
             s.setLastUpdateTime(new Date());
             //保存
             this.skuMapper.insertSelective(s);

            Stock stock = new Stock();
            stock.setSkuId(s.getId());
            stock.setStock(s.getStock());
            this.stockMapper.insert(stock);
        }
    }

    public SpuDetail querySpuDetailBySpuId(Long id) {
        return this.spuDetailMapper.selectByPrimaryKey(id);
    }

    public List<Sku> querySkuBySpuId(Long id) {
        Sku sku = new Sku();
        sku.setSpuId(id);
        List<Sku> skuList = skuMapper.select(sku);

        for (Sku s:skuList){
            Long id1 = s.getId();
            Stock stock = this.stockMapper.selectByPrimaryKey(id1);
            s.setStock(stock.getStock());
        }
        return  skuList;
    }

    @Transactional
    public void updateGoods(SpuBo spuBo) {
        spuBo.setLastUpdateTime(new Date());
        //更新spu表
        this.spuMapper.updateByPrimaryKey(spuBo);
        //更新spuDetail表
        this.spuDetailMapper.updateByPrimaryKey(spuBo.getSpuDetail());

        //先查询sku
        Long id = spuBo.getId();
       //this.skuMapper.selectByPrimaryKey(id);
        Sku sku = new Sku();
        sku.setSpuId(id);

        List<Sku> skuList = this.skuMapper.select(sku);
        //select * from tb_sku where spu_id=196
        for (Sku sku1:skuList){
            //删除sku  stock
            this.stockMapper.deleteByPrimaryKey(sku1.getId());
            this.skuMapper.delete(sku1);

        }
        //新增
        saveSkus(spuBo,spuBo.getSkus());

        //发送消息
        this.sendMassage(id,"update");

    }

    public Spu querySpuById(Long spuId) {
       return this.spuMapper.selectByPrimaryKey(spuId);
    }

    //发送消息
    public  void  sendMassage(Long id,String type){
        this.amqpTemplate.convertAndSend("item."+type,id);
    }

}
