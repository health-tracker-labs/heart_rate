package com.sergtm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping("/")
    public String home(Model model){
        return "redirect:/static/index.html";
    }
}