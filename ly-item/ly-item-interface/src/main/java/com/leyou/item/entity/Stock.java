package com.leyou.item.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 库存表
 */
@Data
@Table(name = "tb_stock")
public class Stock {
    @Id
    private Long skuId;
    private Integer seckillStock;// 秒杀可用库存
    private Integer seckillTotal;// 已秒杀数量
    private Integer stock;// 正常库存

}