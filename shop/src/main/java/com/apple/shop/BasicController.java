package com.apple.shop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BasicController {
    @GetMapping("/")
    String hello(){
        return "redirect:/list/page/1";
    }

    @GetMapping("/about")
    @ResponseBody
    String about(){
        return "피싱사이트에요";
    }



}
