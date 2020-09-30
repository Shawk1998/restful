package com.test.restful;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("testBean")
public class TestBean {

    @RequestMapping("testDemo")
    public String TestDemo(){
        return "hello";
    }
    @GetMapping("/test")
    public String testGetMapping(Model model){
        model.addAttribute("msg","测试@GetMapper注解");
        return "sucess";
    }
    @PostMapping("/test")
    public String testPostMapping(Model model) {
        model.addAttribute("msg","测试@PostMapping注解");
        return "sucess";
    }

}
