package com.taotao.controller;

import com.taotao.pojo.EUDataGridResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/item/{itemId}")
    @ResponseBody
    public TbItem getItemById(@PathVariable Long itemId){

        TbItem item = itemService.getItemById(itemId);

        return item;
    }

    @RequestMapping("/item/list")
    @ResponseBody
    public EUDataGridResult getItemList(Integer page, Integer rows){

        EUDataGridResult result = itemService.getItemByList(page, rows);

        return result;
    }

    @RequestMapping("/item/show")
    @ResponseBody
    public List<TbItem> getItemByNmae(@RequestParam(value = "title") String name){
        List<TbItem> item = itemService.getItemByName(name);
        System.out.printf("\n=========方法被执行=============\n");
        System.out.printf("item="+item.get(0).getTitle()+"\n");
        return item;
    }
}
