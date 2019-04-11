package guo.ping.taotao.portal.controller;

import guo.ping.taotao.pojo.TbItem;
import guo.ping.taotao.portal.pojo.ItemInfo;
import guo.ping.taotao.portal.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;

	@RequestMapping("/item/{itemId}")
	public String showItem(@PathVariable Long itemId, Model model){
		ItemInfo itemInfo = itemService.getItemById(itemId);
		model.addAttribute("item", itemInfo);
		return "item";
	}
}
