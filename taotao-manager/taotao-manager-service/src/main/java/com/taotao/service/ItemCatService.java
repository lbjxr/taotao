package com.taotao.service;


import com.taotao.common.pojo.EUTreeNode;

import java.util.List;

public interface ItemCatService {

    List<EUTreeNode> getCatList(long parentId);
}
