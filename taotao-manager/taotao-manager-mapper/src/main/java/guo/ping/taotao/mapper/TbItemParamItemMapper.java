package guo.ping.taotao.mapper;

import guo.ping.taotao.pojo.TbItemParamItem;

import java.util.List;

public interface TbItemParamItemMapper {
    void insert(TbItemParamItem tbItemParamItem);
    List<TbItemParamItem> selectItemParamByItemId(Long itemId);
}
