package com.schedule.controller;

import com.schedule.controller.main.Main;
import com.schedule.model.AppUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/profile")
public class ProfileCont extends Main {
    @GetMapping
    public String profile(Model model) {
        getCurrentUserAndRole(model);
        return "profile";
    }

    @PostMapping("/edit")
    public String profileFio(@RequestParam String fio) {
        AppUser user = getUser();
        user.setFio(fio);
        userRepo.save(user);
        return "redirect:/profile";
    }

    @GetMapping("/theme")
    public String theme() {
        AppUser user = getUser();
        user.setTheme(!user.isTheme());
        userRepo.save(user);
        return "redirect:/profile";
    }
}
