package guo.ping.taotao.service;

import guo.ping.taotao.common.pojo.EasyUIDataGridResult;
import guo.ping.taotao.common.pojo.TaotaoResult;
import guo.ping.taotao.pojo.TbItem;
import guo.ping.taotao.pojo.TbItemDesc;

/**
 * 商品查询service
 */
public interface ItemService {
    TbItem getItemById(Long id);
    EasyUIDataGridResult getItemList(int page, int rows);
    TaotaoResult createItem(TbItem item, String desc, String itemParam);
    TbItemDesc getItemDesc(Long itemId);
    TaotaoResult updateItem(TbItem item, String desc);
}
