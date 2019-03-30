package com.taotao.service;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.pojo.TbItem;
import com.taotao.common.utils.TaotaoResult;

import java.util.List;

public interface ItemService {

    TbItem getItemById(long itemid);

    EUDataGridResult getItemByList(int page, int rows);

    List<TbItem> getItemByName(String name);

    TaotaoResult createItem(TbItem item, String desc, String itemParams) throws Exception;

}
