package guo.ping.taotao.portal.service.impl;

import guo.ping.taotao.common.pojo.TaotaoResult;
import guo.ping.taotao.common.utils.HttpClientUtil;
import guo.ping.taotao.pojo.TbItem;
import guo.ping.taotao.portal.pojo.ItemInfo;
import guo.ping.taotao.portal.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${ITME_INFO_URL}")
	private String ITME_INFO_URL;

	@Override
	public ItemInfo getItemById(Long itemId) {

		try {
			//从rest层获取商品信息
			String json = HttpClientUtil.doGet(REST_BASE_URL + ITME_INFO_URL + itemId);
			if (!StringUtils.isBlank(json)){
				TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, ItemInfo.class);
				if (taotaoResult.getStatus() == 200){
					ItemInfo itemInfo = (ItemInfo) taotaoResult.getData();
					return itemInfo;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
