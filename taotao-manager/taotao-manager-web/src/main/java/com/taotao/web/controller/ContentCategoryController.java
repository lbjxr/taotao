package com.taotao.web.controller;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.service.ContentCategoryService;
import com.taotao.common.utils.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {

    @Autowired
    ContentCategoryService categoryService;

    /**
     * 内容分类管理展示
     * @param parentId
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public List<EUTreeNode> getContentCategoryList(@RequestParam(value = "id", defaultValue = "0") Long parentId){

        List<EUTreeNode> treeNodeList = categoryService.getCategoryList(parentId);
        return treeNodeList;
    }

    /**
     * 内容分类的增加
     * @param parentId
     * @param name
     * @return
     */
    @RequestMapping("/create")
    @ResponseBody
    public TaotaoResult insertContentCategory(Long parentId, String name){
        TaotaoResult taotaoResult = categoryService.insertCategory(parentId, name);
        return taotaoResult;
    }

    /**
     * 内容分类节点删除
     * @param parentId
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public TaotaoResult delteContentCategory(@RequestParam(value = "parentId", defaultValue = "0")Long parentId, Long id){
        System.out.println("parentId= " + parentId);
        System.out.println("id = " + id);
        TaotaoResult taotaoResult = categoryService.deleteCategoryById(parentId, id);

        return taotaoResult;
    }

    @RequestMapping("/update")
    @ResponseBody
    public TaotaoResult updateContenCategoryNameById(Long id, String name){
        TaotaoResult taotaoResult = categoryService.updateCategoryNameById(id, name);
        return taotaoResult;
    }
}
