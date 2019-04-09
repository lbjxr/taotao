package com.taotao.search.service.impl;

import com.taotao.search.mapper.ItemMapper;
import com.taotao.search.pojo.Item;
import com.taotao.search.service.ItemService;
import guo.ping.taotao.common.pojo.TaotaoResult;
import guo.ping.taotao.common.utils.ExceptionUtil;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemMapper itemMapper;

	@Autowired
	private SolrServer solrServer;

	@Override
	public TaotaoResult importAllItems() {
		//查询商品列表
		List<Item> itemList = itemMapper.getItemList();
		//把商品信息写入索引库
		try {
			for (Item item : itemList){
				//创建一个SolrInputDocu对象
				SolrInputDocument document = new SolrInputDocument();
				document.setField("id", item.getId());
				document.setField("item_title", item.getTitle());
				document.setField("item_sell_point", item.getSell_point());
				document.setField("item_price", item.getPrice());
				document.setField("item_image", item.getImage());
				document.setField("item_category_name", item.getCategory_name());
				document.setField("item_desc", item.getItem_des());

				//写入索引库
				solrServer.add(document);
			}
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}

		return TaotaoResult.ok();
	}
}
