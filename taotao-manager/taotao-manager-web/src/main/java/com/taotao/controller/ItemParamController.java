package com.taotao.controller;

import com.taotao.pojo.EUDataGridResult;
import com.taotao.pojo.TbItemParam;
import com.taotao.service.ItemParamService;
import com.taotao.utils.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 商品参数控制层
 */
@Controller
@RequestMapping("/item/param")
public class ItemParamController {
    @Autowired
    ItemParamService itemParamService;

    /**
     * 商品参数列表显示
     * @param page
     * @param rows
     * @return
     */
    @GetMapping(value = "/list")
    @ResponseBody
    public EUDataGridResult getItemParamList(Integer page, Integer rows){
        EUDataGridResult result = itemParamService.getItemParamList(page, rows);
        return  result;
    }

    /**
     * 根据cid查询商品参数
     * @param itemCatId
     * @return
     */
    @GetMapping("/query/itemcatid/{itemCatId}")
    @ResponseBody
    public TaotaoResult getItemParamByCid(@PathVariable long itemCatId){
        TaotaoResult result = itemParamService.getItemParamByCid(itemCatId);
        return result;
    }

    @PostMapping("/save/{cid}")
    @ResponseBody
    public TaotaoResult insertItemParam(@PathVariable long cid, String paramData){
        TbItemParam tbItemParam = new TbItemParam();
        tbItemParam.setItemCatId(cid);
        tbItemParam.setParamData(paramData);

        TaotaoResult result = itemParamService.insertItemPatam(tbItemParam);
        return result;
    }
}
