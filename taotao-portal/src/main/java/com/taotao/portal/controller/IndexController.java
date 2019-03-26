package com.taotao.portal.controller;

import com.taotao.common.utils.TaotaoResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class IndexController {

    @RequestMapping("/index")
    public String showIndex(){
        return "index";
    }

    @PostMapping("/httpclient/post")
    @ResponseBody
    public String testPost(){
        return "OK";
    }
}
