package com.taotao.web.controller;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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

       //查询数据库，根据父类id查询
       List<EUTreeNode> treeNodeList = itemCatService.getCatList(parentid);

       return treeNodeList;
   }
}
