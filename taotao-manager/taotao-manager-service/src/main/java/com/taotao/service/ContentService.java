package com.taotao.service;

import com.taotao.pojo.EUDataGridResult;

public interface ContentService {
    EUDataGridResult getContentById(Long categoryId, int page, int rows);
}
