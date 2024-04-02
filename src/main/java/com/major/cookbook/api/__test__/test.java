package com.major.cookbook.api.__test__;

import org.springframework.web.bind.annotation.RestController;

import com.major.cookbook.util.Hash;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api")
public class test {
    @GetMapping("/hello")
    public String getHelloWorld() {
        return "Hello World";
    }
    @GetMapping("/hash")
    public String getHash(@RequestParam String password) {
        return Hash.getHash(password);
    }
    
}