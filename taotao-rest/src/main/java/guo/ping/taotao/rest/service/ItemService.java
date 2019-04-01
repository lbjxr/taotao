package guo.ping.taotao.rest.service;

import guo.ping.taotao.common.pojo.TaotaoResult;

public interface ItemService {

	TaotaoResult getItemBaseInfo(Long id);
	TaotaoResult getItemDescByItemId(Long ItemId);
	TaotaoResult getItemParamByItemId(Long ItemId);

}
