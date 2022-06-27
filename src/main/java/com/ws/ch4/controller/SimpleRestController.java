package com.ws.ch4.controller;

import com.ws.ch4.domain.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
public class SimpleRestController {
    @GetMapping("/ajax")
    public String ajax() {
        return "ajax";
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

//    @GetMapping("/registerForm")
//    public String registerForm() {
//        return "registerForm";
//    }
//
//    @PostMapping("/registerInfo")
//    public String registerInfo() {
//        return "registerInfo";
//    }

//    @PostMapping("/send")
//    @ResponseBody
//    public Person test(@RequestBody Person p) {
//    public Person test(Person p) {
//        System.out.println("p = " + p);
//        p.setName("ABC");
//        p.setAge(p.getAge() + 10);
//
//        return p;
//    }
}