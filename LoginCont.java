package com.schedule.controller;

import com.schedule.controller.main.Main;
import com.schedule.model.AppUser;
import com.schedule.model.enums.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginCont extends Main {
    @GetMapping
    public String login(Model model) {
        if (userRepo.findAll().isEmpty()) {
            AppUser user = new AppUser("admin", "admin", "admin");
            user.setRole(Role.ADMIN);
            userRepo.save(user);
        }
        getCurrentUserAndRole(model);
        return "login";
    }
}
