package com.taotao.service;

import com.taotao.pojo.EUTreeNode;
import com.taotao.pojo.TbItemCat;

import java.util.List;

public interface ItemCatService {

    List<EUTreeNode> getCatList(long parentId);
}
