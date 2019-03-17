package com.taotao.controller;

import com.taotao.pojo.EUDataGridResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import com.taotao.utils.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * 根据商品ID获取商品数据
     * @param itemId
     * @return
     */
    @RequestMapping("/item/{itemId}")
    @ResponseBody
    public TbItem getItemById(@PathVariable Long itemId){

        TbItem item = itemService.getItemById(itemId);

        return item;
    }

    /**
     * 获取商品列表，由于展示
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/item/list")
    @ResponseBody
    public EUDataGridResult getItemList(Integer page, Integer rows){

        EUDataGridResult result = itemService.getItemByList(page, rows);
        return result;
    }

    /**
     * 根据商品名模糊查询商品
     * @param name
     * @return
     */
    @RequestMapping("/item/show")
    @ResponseBody
    public List<TbItem> getItemByNmae(@RequestParam(value = "title") String name){
        List<TbItem> item = itemService.getItemByName(name);
        System.out.print("\n=========方法被执行=============\n");
        System.out.print("item=" + item.get(0).getTitle() + "\n");
        return item;
    }

    /**
     * 商品添加方法
     * @param item
     * @return
     */
    @PostMapping(value = "/item/save")
    @ResponseBody
    public TaotaoResult createItem(TbItem item, String desc) throws Exception {
        TaotaoResult taotaoResult = itemService.createItem(item, desc);
        return taotaoResult;
    }
}
