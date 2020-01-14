package com.leyou.item.entity.bo;

import com.leyou.item.entity.Sku;
import com.leyou.item.entity.Spu;
import com.leyou.item.entity.SpuDetail;
import lombok.Data;

import javax.persistence.Transient;
import java.util.List;

@Data
public class SpuBo extends Spu {
    @Transient
    private  String cname;//分类名称
    @Transient
    private String bname;//品牌名称

    @Transient
    private List<Sku> skus;

    @Transient
    private SpuDetail spuDetail;

}
