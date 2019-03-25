package com.taotao.controller;

import com.taotao.pojo.EUDataGridResult;
import com.taotao.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/content/query")
public class ContentController {

    @Autowired
    ContentService contentService;

    /**
     * 根据节点id获取内容列表
     * @param categoryId
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public EUDataGridResult getContentListById(Long categoryId, int page, int rows){
        EUDataGridResult result = contentService.getContentById(categoryId, page, rows);
        return result;
    }
}
