package com.taotao.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.EUDataGridResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品管理service
 */

@Service
public class ItemserverImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;

    @Override
    public TbItem getItemById(long itemid) {

        //根据id查询
        //TbItem tbItem = itemMapper.selectByPrimaryKey(itemid);

        //跟据条件查询
        TbItemExample example = new TbItemExample();
        //创建一个条件对象
        TbItemExample.Criteria criteria = example.createCriteria();
        List<TbItem> list = itemMapper.selectByExample(example);

        //判断返回结果
        if (list != null && list.size() > 0){
            TbItem item = list.get(0);
            return item;
        }

        return null;
    }

    /**
     * 商品列表
     * @param page
     * @param rows
     * @return
     */

    @Override
    public EUDataGridResult getItemByList(int page, int rows) {

        TbItemExample example = new TbItemExample();

        PageHelper.startPage(page, rows);

        List<TbItem> list = itemMapper.selectByExample(example);

        PageInfo<TbItem> pageInfo = new PageInfo<>(list);

        EUDataGridResult result = new EUDataGridResult();

        result.setRows(list);
        result.setTotal(pageInfo.getTotal());

        return result;
    }

}
