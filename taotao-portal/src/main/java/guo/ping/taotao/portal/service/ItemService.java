package guo.ping.taotao.portal.service;

import guo.ping.taotao.pojo.TbItemParamItem;
import guo.ping.taotao.portal.pojo.ItemInfo;

public interface ItemService {
	ItemInfo getItemById(Long itemId);
	String getItemParamItemById(Long itemId);
	String getItemDescById(Long itemId);
}
