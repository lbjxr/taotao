package com.taotao.rest.controller;

import com.taotao.rest.pojo.CatResult;
import com.taotao.rest.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商品分类列表实现控制层
 */
@Controller
public class ItemCatController {

    @Autowired
    ItemCatService itemCatService;

    /**
     * 拼接字符串形式返回json数据
     * @param callback
     * @return
     */
    /*
    @RequestMapping(value = "/itemcat/list", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    @ResponseBody
    public String getItemCatList(String callback){

        CatResult catResult = itemCatService.getItemCatList();
        //将pojo转换成json字符串
        String json = JsonUtils.objectToJson(catResult);
        //拼装返回值
        String result = callback + "(" + json + ")";
        return result;
    }*/

    /**
     * 获取分类列表
     * @param callback
     * @return
     */
    @RequestMapping(value = "/itemcat/list")
    @ResponseBody
    public Object getItemCatList(String callback){
        CatResult catResult = itemCatService.getItemCatList();
        MappingJacksonValue jacksonValue = new MappingJacksonValue(catResult);
        jacksonValue.setJsonpFunction(callback);
        return jacksonValue;
    }
}
