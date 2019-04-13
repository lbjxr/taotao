package guo.ping.taotao.controller;

import guo.ping.taotao.common.pojo.TaotaoResult;
import guo.ping.taotao.pojo.TbItemDesc;
import guo.ping.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
//@RequestMapping("/rest/page")
public class ItemEditController {

	@Autowired
	private ItemService itemService;

//	@RequestMapping("/item-edit/{_}")
	@RequestMapping(value = "/rest/page/item-edit")
	@ResponseBody
	public TaotaoResult getItemDesc(@RequestParam(value = "_") long itemId){
		System.out.println("\nitemid  " + itemId);
		TbItemDesc tbItemDesc = itemService.getItemDesc(itemId);
		return TaotaoResult.ok(tbItemDesc);
	}
}
