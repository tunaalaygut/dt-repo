package com.alaygut.prototype.controller;

import org.springframework.stereotype.Controller; 
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping({"/"})
    public String getIndex(){
        return "index";
    }

    @GetMapping("/login")
    public String getLoginPage(){
        return "login";
    }

}
