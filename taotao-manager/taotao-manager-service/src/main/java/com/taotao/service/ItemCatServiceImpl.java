package com.taotao.service;

import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Override
    public List<TbItemCat> getCatList(long parentId) {

        TbItemCatExample tbItemCatExample = new TbItemCatExample();

        //设置查询条件
        TbItemCatExample.Criteria criteria = tbItemCatExample.createCriteria();

        //根据parentid查询子节点
        criteria.andParentIdEqualTo(parentId);

        //返回子节点列表
        List<TbItemCat> list = itemCatMapper.selectByExample(tbItemCatExample);

        return list;

    }
}
