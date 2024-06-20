package com.schedule.controller;

import com.schedule.controller.main.Main;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexCont extends Main {
    @GetMapping("/index")
    public String index1() {
        return "redirect:/profile";
    }

    @GetMapping("/")
    public String index2() {
        return "redirect:/profile";
    }
}