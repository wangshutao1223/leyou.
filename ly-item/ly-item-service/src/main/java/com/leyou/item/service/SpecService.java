package com.leyou.item.service;

import com.leyou.item.entity.SpecGroup;
import com.leyou.item.entity.SpecParam;
import com.leyou.item.mapper.SpecGroupMapper;
import com.leyou.item.mapper.SpecParamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecService {
    @Autowired
    private SpecGroupMapper specGroupMapper;

    @Autowired
    private SpecParamMapper specParamMapper;

    /**
     * 分类参数
     * @param cid
     * @return
     */
    public List<SpecGroup> querySpecGroups(Long cid) {
        //select * from spec_group where cid=76
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(cid);
        //通过分类的id查询规格参数
        List<SpecGroup> specGroupList = specGroupMapper.select(specGroup);

        //查询规格参数里面的规格组
        specGroupList.forEach(t->{
            Long id1 = t.getId();//1

            SpecParam specParam = new SpecParam();
            specParam.setGroupId(id1);
            //通过规格组的id查询规格参数表
            List<SpecParam> specParams = this.specParamMapper.select(specParam);
            //select * from spec_param where group_id=1
            //规格参数表封装到规格组
            t.setSpecParams(specParams);
        });



        return specGroupList;
    }

    public List<SpecParam> querySpecParam(Long gid,Long cid,Boolean searching,Boolean generic) {
        SpecParam specParam=new SpecParam();
        specParam.setGroupId(gid);
        specParam.setCid(cid);
        specParam.setSearching(searching);
        specParam.setGeneric(generic);
       return  this.specParamMapper.select(specParam);
    }



    public void insertSpecParam(SpecParam specParam/*, Boolean numeric, Boolean searching, Boolean generic*/) {
           /* specParam.setNumeric(numeric);
            specParam.setSearching(searching);
            specParam.setGeneric(generic);*/
            this.specParamMapper.insert(specParam);
    }


    public void insertGroup(Long cid,String name) {
        this.specGroupMapper.insertGroup(cid,name);
    }

    public void updateSpec(SpecParam specParam) {
        this.specParamMapper.updateByPrimaryKey(specParam);
    }

    public void deleteSpec(Long id) {
        this.specParamMapper.deleteByPrimaryKey(id);
    }

    public void insertSpecGroup(SpecGroup specGroup) {
        this.specGroupMapper.insertSelective(specGroup);
    }

    public void updateSpecGroup(SpecGroup specGroup) {
        this.specGroupMapper.updateByPrimaryKey(specGroup);
    }

    public void deleteSpecGroup(Long id) {
        this.specGroupMapper.deleteByPrimaryKey(id);
    }
}
