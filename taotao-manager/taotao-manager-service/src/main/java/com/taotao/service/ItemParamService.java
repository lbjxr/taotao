package com.taotao.service;

import com.taotao.pojo.EUDataGridResult;
import com.taotao.pojo.TbItemParam;
import com.taotao.utils.TaotaoResult;

public interface ItemParamService {
    EUDataGridResult getItemParamList(int page, int rows);
    TaotaoResult getItemParamByCid(long cid);
    TaotaoResult insertItemPatam(TbItemParam itemParam);
}
