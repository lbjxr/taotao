package guo.ping.taotao.rest.service.impl;

import guo.ping.taotao.common.pojo.TaotaoResult;
import guo.ping.taotao.mapper.TbItemDescMapper;
import guo.ping.taotao.mapper.TbItemMapper;
import guo.ping.taotao.mapper.TbItemParamItemMapper;
import guo.ping.taotao.pojo.TbItem;
import guo.ping.taotao.pojo.TbItemDesc;
import guo.ping.taotao.pojo.TbItemParam;
import guo.ping.taotao.pojo.TbItemParamItem;
import guo.ping.taotao.rest.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper tbItemMapper;
	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	@Autowired
	private TbItemParamItemMapper tbItemParamItemMapper;

	/**
	 * 获取商品基本信息
	 * @param id
	 * @return
	 */
	@Override
	public TaotaoResult getItemBaseInfo(Long id) {
		TbItem item = tbItemMapper.selectByPrimaryKey(id);
		return TaotaoResult.ok(item);
	}

	/**
	 * 根据商品id查询商品描述
	 * @param itemId
	 * @return
	 */
	@Override
	public TaotaoResult getItemDescByItemId(Long itemId) {
		TbItemDesc tbItemDesc = tbItemDescMapper.selectItemDescByPrimaryKey(itemId);

		return TaotaoResult.ok(tbItemDesc);
	}

	/**
	 * 根据商品id查询商品规格参数
	 * @param itemId
	 * @return
	 */
	@Override
	public TaotaoResult getItemParamByItemId(Long itemId) {
		TbItemParamItem tbItemParamItem = tbItemParamItemMapper.selectItemParamByItemId(itemId);

		return TaotaoResult.ok(tbItemParamItem);
	}
}
