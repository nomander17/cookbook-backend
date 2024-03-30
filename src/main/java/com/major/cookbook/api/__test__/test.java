package com.major.cookbook.api.__test__;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api")
public class test {
    @GetMapping("/hello")
    public String getHelloWorld() {
        return "Hello World";
    }
}