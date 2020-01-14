package com.bigdata;

import com.leyou.LySearchService;
import com.leyou.client.GoodsClient;
import com.leyou.item.entity.bo.SpuBo;
import com.leyou.pojo.Goods;
import com.leyou.repository.GoodsRepository;
import com.leyou.service.IndexService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import utils.PageResult;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LySearchService.class)
public class IndexText {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private IndexService indexService;
    //建库建表
    @Test
    public  void  init(){
        elasticsearchTemplate.createIndex(Goods.class);
        elasticsearchTemplate.putMapping(Goods.class);
    }

    @Test
    public  void loadData(){
        Integer page=1;
        while (true){
            //使用feignClient调用商品微服务
            PageResult<SpuBo> pageResult = goodsClient.querySpuByPage(null, null, page, 50);

            if(pageResult==null){
                break;
            }

            page++;

            //获取商品list
            List<SpuBo> items = pageResult.getItems();

            List<Goods> list = new ArrayList<Goods>();

            for (SpuBo spuBo:items){
                //spubo ->goods
                Goods goods=indexService.bulidGoods(spuBo);
                list.add(goods);
            }
            //保存到索引库
            goodsRepository.saveAll(list);
        }
    }

}
