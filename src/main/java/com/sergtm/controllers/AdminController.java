package com.sergtm.controllers;

import com.sergtm.treeView.Root;
import com.sergtm.treeView.TreeNode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @RequestMapping(path = "treeView.json", method=GET)
    @ResponseBody
    public Root treeView(){
        TreeNode users = new TreeNode("Users", true);
        return new Root("Root", true, Arrays.asList(users));
    }

    @RequestMapping(path = "adminPage", method=GET)
    public String getAdminPage(Model model){
        return "redirect:/static/adminPage.html";
    }
}
