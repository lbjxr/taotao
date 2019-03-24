package com.taotao.service;

import com.taotao.pojo.EUTreeNode;
import com.taotao.pojo.TbContentCategory;
import com.taotao.utils.TaotaoResult;

import java.util.List;

public interface ContentCategoryService {

    List<EUTreeNode> getCategoryList(Long parentId);
    TaotaoResult insertCategory(Long parentId, String name);
    TaotaoResult deleteCategoryById(Long parentId, Long id);
    TaotaoResult updateCategoryNameById(Long id, String name);
}
