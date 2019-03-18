package com.taotao.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.*;
import com.taotao.utils.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ItemParamServiceImpl implements ItemParamService {

    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;

    @Autowired
    private TbItemParamMapper itemParamMapper;

    @Override
    public EUDataGridResult getItemParamList(int page, int rows) {
        //创建一个条件对象
        TbItemParamExample itemParamExample = new TbItemParamExample();
        //设置分页信息
        PageHelper.startPage(page, rows);
        //查询商品规格参数
        List<TbItemParam> itemParamList = itemParamMapper.selectByExampleWithBLOBs(itemParamExample);
        //创建一个easyui数据对象
        EUDataGridResult result = new EUDataGridResult();
        result.setRows(itemParamList);
        //获取商品总条数
        PageInfo<TbItemParam> pageInfo = new PageInfo<>(itemParamList);
        result.setTotal(pageInfo.getTotal());

        return result;
    }

    /**
     * 商品规格的参数模板
     * @param cid
     * @return
     */
    @Override
    public TaotaoResult getItemParamByCid(long cid) {

        TbItemParamExample itemParamExample = new TbItemParamExample();
        TbItemParamExample.Criteria  criteria = itemParamExample.createCriteria();
        criteria.andItemCatIdEqualTo(cid);
        List<TbItemParam> itemParamList = itemParamMapper.selectByExampleWithBLOBs(itemParamExample);

        //判断是否查到结果
        if (itemParamList != null && itemParamList.size() > 0){ return  TaotaoResult.ok(itemParamList.get(0)); }

        return TaotaoResult.ok();
    }

    /**
     * 保存商品参数
     * @param itemParam
     * @return
     */
    @Override
    public TaotaoResult insertItemPatam(TbItemParam itemParam) {
        //补全数据
        itemParam.setCreated(new Date());
        itemParam.setUpdated(new Date());

        itemParamMapper.insert(itemParam);
        return  TaotaoResult.ok();
    }
}
