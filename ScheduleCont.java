package com.schedule.controller;

import com.schedule.controller.main.Main;
import com.schedule.model.AppUser;
import com.schedule.model.enums.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/schedules")
public class ScheduleCont extends Main {

    @GetMapping
    public String schedules(Model model) {
        getCurrentUserAndRole(model);

        AppUser user = getUser();
        if (user.getRole() == Role.USER) {
            model.addAttribute("classroom", user.getClassroom());
        } else {
            model.addAttribute("classrooms", classroomRepo.findAll());
        }

        return "schedules";
    }

    @GetMapping("/search")
    public String schedules(Model model, @RequestParam Long classroomId) {
        getCurrentUserAndRole(model);
        model.addAttribute("classroomId", classroomId);
        model.addAttribute("classrooms", classroomRepo.findAll());
        model.addAttribute("classroom", classroomRepo.getReferenceById(classroomId));
        return "schedules";
    }


}
