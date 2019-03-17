package com.taotao.controller;

import com.taotao.pojo.EUDataGridResult;
import com.taotao.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商品参数控制层
 */
@Controller
public class ItemParamController {
    @Autowired
    ItemParamService itemParamService;

    @RequestMapping(value = "/item/param/list")
    @ResponseBody
    public EUDataGridResult getItemParamList(Integer page, Integer rows){
        EUDataGridResult result = itemParamService.getItemParamList(page, rows);
        return  result;
    }

}
