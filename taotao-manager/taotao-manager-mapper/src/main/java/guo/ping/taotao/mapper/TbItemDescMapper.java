package guo.ping.taotao.mapper;

import guo.ping.taotao.pojo.TbItemDesc;

public interface TbItemDescMapper {

    void insert(TbItemDesc tbItemDesc);

    TbItemDesc selectItemDescByPrimaryKey(Long itemId);

    void update(TbItemDesc tbItemDesc);
}
