package com.taotao.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.EUDataGridResult;
import com.taotao.pojo.TbItemParamExample;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemParamServiceImpl implements ItemParamService {

    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;

    @Override
    public EUDataGridResult getItemParamList(int page, int rows) {

        //创建一个条件对象
        TbItemParamItemExample itemParamItemExample = new TbItemParamItemExample();
        //设置分页信息
        PageHelper.startPage(page, rows);
        //查询商品规格参数
        List<TbItemParamItem> itemParamItemList = itemParamItemMapper.selectByExampleWithBLOBs(itemParamItemExample);
        //创建一个easyui数据对象
        EUDataGridResult result = new EUDataGridResult();
        result.setRows(itemParamItemList);
        //获取商品总条数
        PageInfo<TbItemParamItem> pageInfo = new PageInfo<>(itemParamItemList);
        result.setTotal(pageInfo.getTotal());

        return result;
    }
}
