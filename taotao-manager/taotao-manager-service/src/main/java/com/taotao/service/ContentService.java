package com.taotao.service;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.pojo.TbContent;
import com.taotao.common.utils.TaotaoResult;

public interface ContentService {
    EUDataGridResult getContentById(Long categoryId, int page, int rows);
    TaotaoResult insertContent(TbContent tbContent);
}
