package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.common.pojo.*;
import com.taotao.common.utils.IDUtils;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.*;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 商品管理service
 */

@Service
public class ItemserverImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;

    @Autowired
    private TbItemDescMapper itemDescMapper;

    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;

    @Override
    public TbItem getItemById(long itemid) {

        //根据id查询
        //TbItem tbItem = itemMapper.selectByPrimaryKey(itemid);

        //跟据条件查询
        TbItemExample example = new TbItemExample();
        //创建一个条件对象
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(itemid);
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

        //查询商品列表
        TbItemExample tbItemExample = new TbItemExample();

        //设置分页信息
        PageHelper.startPage(page, rows);

        //查询商品数据
        List<TbItem> tbItemList = itemMapper.selectByExample(tbItemExample);

        //创建一个easyui数据对象
        EUDataGridResult result = new EUDataGridResult();
        result.setRows(tbItemList);

        //获取商品总条数
        PageInfo<TbItem> pageInfo = new PageInfo<>(tbItemList);
        result.setTotal(pageInfo.getTotal());

        return result;
    }

    /**
     * 根据商品名称模糊查询
     * @param name
     * @return
     */

    @Override
    public List<TbItem> getItemByName(String name) {

        TbItemExample example = new TbItemExample();

        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andTitleLike("%"+name+"%");
        List<TbItem> list = itemMapper.selectByExample(example);

        //判断是否为空
        if(list != null && list.size() > 0){
            return list;
        }
        return null;
    }

    /**
     * 商品添加
     * @param item
     * @param desc
     * @param itemParams
     * @return
     * @throws Exception
     */
    @Override
    public TaotaoResult createItem(TbItem item, String desc, String itemParams) throws Exception{

        //补全item
        //生成商品id
        Long itemId = IDUtils.genItemId();
        item.setId(itemId);
        item.setStatus((byte) 1);
        item.setCreated(new Date());
        item.setUpdated(new Date());

        //把商品信息插入数据表
        itemMapper.insert(item);

        //保证事务，调用insertItemDesc,失败后抛出异常
        TaotaoResult result = insertItemDesc(itemId, desc);
        if (200 != result.getStatus()){
            throw new Exception();
        }
        //添加规格参数
        result = insertItemParamItem(itemId, itemParams);
        if (200 != result.getStatus()){ throw new Exception(); }

        return TaotaoResult.ok();
    }

    /**
     * 添加商品描述
     * @param itemID
     * @param desc
     * @return
     */
    private TaotaoResult insertItemDesc(Long itemID, String desc){
        TbItemDesc itemDesc = new TbItemDesc();

        itemDesc.setItemId(itemID);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(new Date());

        itemDescMapper.insert(itemDesc);
        return TaotaoResult.ok();
    }

    private TaotaoResult insertItemParamItem(long itemId, String itemParams){
        //创建pojo
        TbItemParamItem itemParamItem = new TbItemParamItem();
        itemParamItem.setItemId(itemId);
        itemParamItem.setParamData(itemParams);
        itemParamItem.setCreated(new Date());
        itemParamItem.setUpdated(new Date());

        itemParamItemMapper.insert(itemParamItem);

        return TaotaoResult.ok();
    }
}
