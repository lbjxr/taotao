package com.taotao.service.impl;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.service.ItemCatService;
import com.taotao.mapper.TbItemCatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Override
    public List<EUTreeNode> getCatList(long parentId) {

        TbItemCatExample tbItemCatExample = new TbItemCatExample();

        //设置查询条件
        TbItemCatExample.Criteria criteria = tbItemCatExample.createCriteria();
        //根据parentid查询子节点
        criteria.andParentIdEqualTo(parentId);

        //返回子节点列表
        List<TbItemCat> list = itemCatMapper.selectByExample(tbItemCatExample);
        List<EUTreeNode> resultList = new ArrayList<>();

        //把列表转换成treeNodeList
        for(TbItemCat tbItemCat : list){
            EUTreeNode node = new EUTreeNode();
            node.setId(tbItemCat.getId());
            node.setText(tbItemCat.getName());
            node.setState(tbItemCat.getIsParent() ? "closed" : "open");

            resultList.add(node);
        }

        return resultList;
    }
}
