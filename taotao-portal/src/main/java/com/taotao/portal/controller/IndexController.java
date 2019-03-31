package com.taotao.portal.controller;

import com.taotao.portal.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 门户首页控制层
 */
@Controller
public class IndexController {

    @Autowired
    private ContentService contentService;

    /**
     * 首页显示，加载大广告位的图片
     * @param model
     * @return
     */
    @RequestMapping("/index")
    public String showIndex(Model model){
        String adJson = contentService.getContentList();
        model.addAttribute("ad1", adJson);
        return "index";
    }



    @PostMapping("/httpclient/post")
    @ResponseBody
    public String testPost(){
        return "OK";
    }
}
