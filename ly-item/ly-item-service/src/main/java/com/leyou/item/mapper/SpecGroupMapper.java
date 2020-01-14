package com.leyou.item.mapper;

import com.leyou.item.entity.SpecGroup;
import org.apache.ibatis.annotations.Insert;
import tk.mybatis.mapper.common.Mapper;

public interface SpecGroupMapper extends Mapper<SpecGroup> {
    @Insert("insert into tb_spec_group (cid,name) values(#{cid},#{name})")
    void insertGroup(Long cid, String name);
}
