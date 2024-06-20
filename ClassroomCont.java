package com.schedule.controller;

import com.schedule.controller.main.Main;
import com.schedule.model.AppUser;
import com.schedule.model.Classroom;
import com.schedule.model.enums.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/classrooms")
public class ClassroomCont extends Main {

    @GetMapping
    public String classrooms(Model model) {
        getCurrentUserAndRole(model);
        model.addAttribute("classrooms", classroomRepo.findAll());
        return "classrooms";
    }

    @GetMapping("/{classroomId}")
    public String classroom(Model model, @PathVariable Long classroomId) {
        getCurrentUserAndRole(model);
        model.addAttribute("classroom", classroomRepo.getReferenceById(classroomId));
        model.addAttribute("students", userRepo.findAllByRole(Role.USER));
        return "classroom";
    }

    @PostMapping("/add")
    public String addClassroom(@RequestParam String name) {
        classroomRepo.save(new Classroom(name));
        return "redirect:/classrooms";
    }

    @PostMapping("/{classroomId}/edit")
    public String editClassroom(@RequestParam String name, @PathVariable Long classroomId) {
        Classroom classroom = classroomRepo.getReferenceById(classroomId);
        classroom.setName(name);
        classroomRepo.save(classroom);
        return "redirect:/classrooms";
    }

    @GetMapping("/{classroomId}/delete")
    public String deleteClassroom(@PathVariable Long classroomId) {
        Classroom classroom = classroomRepo.getReferenceById(classroomId);
        for (AppUser student : classroom.getStudents()) {
            student.setClassroom(null);
            userRepo.save(student);
        }
        classroomRepo.deleteById(classroomId);
        return "redirect:/classrooms";
    }
}
