package com.taotao.search.controller;

import com.taotao.search.service.ItemService;
import guo.ping.taotao.common.pojo.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 发布服务
 */
@Controller
@RequestMapping("/manager")
public class ItemController {

	@Autowired
	private ItemService itemService;

	@RequestMapping("/importall")
	@ResponseBody
	public TaotaoResult importAllItems(){
		TaotaoResult result = itemService.importAllItems();
		return result;
	}
}
