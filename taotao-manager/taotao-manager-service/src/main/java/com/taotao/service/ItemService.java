package com.taotao.service;

import com.taotao.pojo.EUDataGridResult;
import com.taotao.pojo.TbItem;

public interface ItemService {

    TbItem getItemById(long itemid);

    EUDataGridResult getItemByList(int page, int rows);

}
