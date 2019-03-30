package com.taotao.web.controller;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;
import com.taotao.common.utils.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/content")
public class ContentController {

    @Autowired
    ContentService contentService;

    /**
     * 根据节点id获取内容列表
     * @param categoryId
     * @param page
     * @param rows
     * @return·
     */
    @RequestMapping("/query/list")
    @ResponseBody
    public EUDataGridResult getContentListById(Long categoryId, int page, int rows){
        EUDataGridResult result = contentService.getContentById(categoryId, page, rows);
        return result;
    }

    @RequestMapping("/save")
    @ResponseBody
    public TaotaoResult insertContentByForm(TbContent tbContent){
        TaotaoResult result = contentService.insertContent(tbContent);
        return result;
    }
}
