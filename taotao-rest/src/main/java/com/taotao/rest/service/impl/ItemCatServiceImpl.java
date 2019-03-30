package com.taotao.rest.service.impl;

import com.taotao.pojo.TbItemCatExample;
import com.taotao.rest.pojo.CatNode;
import com.taotao.rest.pojo.CatResult;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.rest.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Override
    public CatResult getItemCatList() {

        //查询分类列表
        CatResult catResult = new CatResult();

        catResult.setData(getCatList(0));

        return catResult;
    }

    /**
     * 查询分类列表方法
     * 使用递归形式
     * @param parentId
     * @return
     */
    private List<?> getCatList(long parentId) {

        //构造条件查询
        TbItemCatExample itemCatExample = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = itemCatExample.createCriteria();
        criteria.andParentIdEqualTo(parentId);

        List<TbItemCat> itemCatList = itemCatMapper.selectByExample(itemCatExample);

        //返回值
        List resultList = new ArrayList();
        //计数器，解决分类过多，页面显示出界问题，临时处理方式
        int count = 0;
        //递归循环拼接json的pojo对象
        for (TbItemCat tbItemCat : itemCatList){
            //判断是否为父节点
            if (tbItemCat.getIsParent()){
                CatNode catNode = new CatNode();
                //是否是第一个节点
                if (parentId == 0) {
                    catNode.setName("<a href='/products/" + tbItemCat.getId() + ".html'>" + tbItemCat.getName() + "</a>");
                }else {
                    catNode.setName(tbItemCat.getName());
                }
                catNode.setUrl("/products/"+tbItemCat.getId()+".html");
                catNode.setItem(getCatList(tbItemCat.getId()));

                resultList.add(catNode);
                count ++;
                //列表第一层只取14个节点
                if (parentId == 0 && count > 13){
                    break;
                }
                //如果是子节点
            }else {
                resultList.add( "/products/"+tbItemCat.getId()+".html|"+tbItemCat.getName());
            }
        }
        return resultList;
    }
}
