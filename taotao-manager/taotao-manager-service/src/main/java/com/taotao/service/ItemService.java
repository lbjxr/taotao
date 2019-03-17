package com.taotao.service;

import com.taotao.pojo.EUDataGridResult;
import com.taotao.pojo.TbItem;
import com.taotao.utils.TaotaoResult;

import java.util.List;

public interface ItemService {

    TbItem getItemById(long itemid);

    EUDataGridResult getItemByList(int page, int rows);

    List<TbItem> getItemByName(String name);

    TaotaoResult createItem(TbItem item, String desc) throws Exception;

}
