package guo.ping.taotao.rest.service.impl;

import guo.ping.taotao.common.pojo.TaotaoResult;
import guo.ping.taotao.common.utils.JsonUtils;
import guo.ping.taotao.mapper.TbItemDescMapper;
import guo.ping.taotao.mapper.TbItemMapper;
import guo.ping.taotao.mapper.TbItemParamItemMapper;
import guo.ping.taotao.pojo.TbItem;
import guo.ping.taotao.pojo.TbItemDesc;
import guo.ping.taotao.pojo.TbItemParam;
import guo.ping.taotao.pojo.TbItemParamItem;
import guo.ping.taotao.rest.dao.JedisClient;
import guo.ping.taotao.rest.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper tbItemMapper;
	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	@Autowired
	private TbItemParamItemMapper tbItemParamItemMapper;

	@Value("${REDIS_ITEM_KEY}")
	private String REDIS_ITEM_KEY;
	@Value("${REDIS_ITEM_EXPIRE}")
	private Integer REDIS_ITEM_EXPIRE;

	@Autowired
	private JedisClient jedisClient;

	/**
	 * 获取商品基本信息
	 * @param id
	 * @return
	 */
	@Override
	public TaotaoResult getItemBaseInfo(Long id) {

		try {
			//判断redis是否存在
			String json = jedisClient.get(REDIS_ITEM_KEY + ":" + id + ":base");
			if (!StringUtils.isBlank(json)){
				//将字符串转换成Item对象
				TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
				return TaotaoResult.ok(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//缓存不存在时，去数据库查询
		TbItem item = tbItemMapper.selectByPrimaryKey(id);

		try {
			//将item对象转换成字符串，并存到缓存中
			jedisClient.set(REDIS_ITEM_KEY + ":" + id + ":base", JsonUtils.objectToJson(item));
			//设置key的过期时间
			jedisClient.expire(REDIS_ITEM_KEY + ":" + id + ":base", REDIS_ITEM_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.ok(item);
	}

	/**
	 * 根据商品id查询商品描述
	 * @param itemId
	 * @return
	 */
	@Override
	public TaotaoResult getItemDescByItemId(Long itemId) {

		try {
			String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":desc");
			if (!StringUtils.isBlank(json)){
				TbItemDesc tbItemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
				return TaotaoResult.ok(tbItemDesc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		TbItemDesc tbItemDesc = tbItemDescMapper.selectItemDescByPrimaryKey(itemId);
		try {
			jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":desc", JsonUtils.objectToJson(tbItemDesc));
			jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":base", REDIS_ITEM_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return TaotaoResult.ok(tbItemDesc);
	}

	/**
	 * 根据商品id查询商品规格参数
	 * @param itemId
	 * @return
	 */
	@Override
	public TaotaoResult getItemParamByItemId(Long itemId) {

		try {
			String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":param");
			if (!StringUtils.isBlank(json)){
				TbItemParam tbItemParam = JsonUtils.jsonToPojo(json, TbItemParam.class);
				return TaotaoResult.ok(tbItemParam);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<TbItemParamItem> tbItemParamItemList = tbItemParamItemMapper.selectItemParamByItemId(itemId);

		if (tbItemParamItemList != null && tbItemParamItemList.size() > 0) {
			TbItemParamItem paramItem = tbItemParamItemList.get(0);
			try {
				jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":param", JsonUtils.objectToJson(paramItem));
				jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":param", REDIS_ITEM_EXPIRE);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return TaotaoResult.ok(tbItemParamItemList);
		}
		return TaotaoResult.build(400, "无此类商品规格");
	}
}
