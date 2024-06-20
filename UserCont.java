package com.schedule.controller;

import com.schedule.controller.main.Main;
import com.schedule.model.AppUser;
import com.schedule.model.enums.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserCont extends Main {

    @GetMapping
    public String users(Model model) {
        getCurrentUserAndRole(model);
        model.addAttribute("users", userRepo.findAll());
        model.addAttribute("roles", Role.values());
        return "users";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id, @RequestParam Role role) {
        AppUser user = userRepo.getReferenceById(id);
        user.setRole(role);
        if (user.getRole() != Role.USER) {
            user.setClassroom(null);
        }
        userRepo.save(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        userRepo.deleteById(id);
        return "redirect:/users";
    }
}