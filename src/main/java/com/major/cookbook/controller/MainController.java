package com.major.cookbook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class MainController {
    @GetMapping("/")
    public String getIndex() {
        return new String("index");
    }
    @GetMapping("/landing")
    public String getLanding() {
        return "http://localhost:3000";
    }
    
}
