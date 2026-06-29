package com.sergtm.controllers;

import com.sergtm.treeView.Root;
import com.sergtm.treeView.TreeNode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping
    public String getAdminPage(){
        return "redirect:/static/adminPage.html";
    }

    @GetMapping(path = "treeView")
    @ResponseBody
    public Root treeView(){
        TreeNode users = new TreeNode("Users", true);
        return new Root("Root", true, Arrays.asList(users));
    }
}
