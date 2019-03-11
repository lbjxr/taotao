package com.taotao.controller;

import com.taotao.pojo.EUTreeNode;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemCat;
import com.taotao.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品分类管理
 */

@Controller
@RequestMapping("/item/cat")
public class ItemCatController {

   @Autowired
   private ItemCatService itemCatService;

   @SuppressWarnings({"rawtype", "unchecked"})
   @RequestMapping("/list")
   @ResponseBody
   //如果id为null时使用默认值，也就是parentid为0的分类列表
   public List categoryList(@RequestParam(value="id",defaultValue = "0") Long parentid) throws Exception{

       List catList = new ArrayList();

       //查询数据库
       List<TbItemCat> tbItemList = itemCatService.getCatList(parentid);
       for(TbItemCat tbItemCat : tbItemList){
           Map node = new HashMap<>();

           node.put("id", tbItemCat.getId());
           node.put("text",tbItemCat.getName());
           //如果是父节点state设置成关闭状态，如果是子节点设置成open状态
           node.put("state", tbItemCat.getIsParent() ? "closed" : "open");
           catList.add(node);
       }

       return catList;
   }
}
