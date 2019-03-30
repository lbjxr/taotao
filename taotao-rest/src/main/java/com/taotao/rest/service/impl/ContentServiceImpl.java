package com.taotao.rest.service.impl;

import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.mapper.TbContentMapper;
import com.taotao.rest.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    private TbContentMapper contentMapper;
    @Override
    public List<TbContent> getContentList(long contentCid) {

        TbContentExample contentExample = new TbContentExample();
        TbContentExample.Criteria criteria = contentExample.createCriteria();
        criteria.andCategoryIdEqualTo(contentCid);

        List<TbContent> contentList = contentMapper.selectByExample(contentExample);

        return contentList;
    }
}
