package com.taotao.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.pojo.TbContentExample;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;
import com.taotao.common.utils.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 内容管理
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    TbContentMapper contentMapper;

    @Override
    public EUDataGridResult getContentById(Long categoryId, int page, int rows) {

        TbContentExample contentExample = new TbContentExample();
        TbContentExample.Criteria criteria = contentExample.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);

        //使用分页查询
        PageHelper.startPage(page, rows);
        List<TbContent> contentList = contentMapper.selectByExample(contentExample);

        //创建EuDataGrid
        EUDataGridResult gridResult = new EUDataGridResult();
        gridResult.setRows(contentList);

        PageInfo<TbContent> pageInfo = new PageInfo<>(contentList);
        gridResult.setTotal(pageInfo.getTotal());

        return gridResult;
    }

    @Override
    public TaotaoResult insertContent(TbContent tbContent) {
        tbContent.setCreated(new Date());
        tbContent.setUpdated(new Date());

        contentMapper.insert(tbContent);

        return TaotaoResult.ok();
    }
}
