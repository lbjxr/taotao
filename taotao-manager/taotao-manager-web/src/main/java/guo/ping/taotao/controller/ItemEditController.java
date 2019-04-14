package guo.ping.taotao.controller;

import guo.ping.taotao.common.pojo.TaotaoResult;
import guo.ping.taotao.pojo.TbItem;
import guo.ping.taotao.pojo.TbItemDesc;
import guo.ping.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
//@RequestMapping("/rest/page")
public class ItemEditController {

	@Autowired
	private ItemService itemService;

	@RequestMapping("/rest/item/query/item/desc/{itemId}")
	@ResponseBody
	public TaotaoResult getItemDescById(@PathVariable long itemId){
		TbItemDesc tbItemDesc = itemService.getItemDesc(itemId);
		return TaotaoResult.ok(tbItemDesc);
	}

	@RequestMapping(value = "/rest/item/update", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult updateItem(TbItem tbItem, String desc){
		itemService.updateItem(tbItem, desc);
		return TaotaoResult.ok();
	}
}
