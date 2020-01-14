package com.bigdata.client;

import com.leyou.item.entity.api.CategroyApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("item-service")
public interface CategoryClient extends CategroyApi {
}
