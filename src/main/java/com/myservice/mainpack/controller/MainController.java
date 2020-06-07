package com.myservice.mainpack.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {

    @Value("${MainTitle}")
    private String MainTitle;

    @Value("${LoginTitle}")
    private String LoginTitle;


    @GetMapping("/")
    public String getMain(Model model){
        model.addAttribute("title", MainTitle);
        return "Main";
    }


    @GetMapping("/login")
    public String loginGet(Model model){
        model.addAttribute("title", LoginTitle);
        return "users/login";
    }


}
