package com.schedule.controller;

import com.schedule.controller.main.Main;
import com.schedule.model.AppUser;
import com.schedule.model.Subject;
import com.schedule.model.enums.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/subjects")
public class SubjectCont extends Main {

    @GetMapping
    public String subjects(Model model) {
        getCurrentUserAndRole(model);
        AppUser user = getUser();

        List<Subject> subjects = new ArrayList<>();
        switch (user.getRole()) {
            case ADMIN: {
                subjects = subjectRepo.findAll();
                model.addAttribute("classrooms", classroomRepo.findAll());
                model.addAttribute("teachers", userRepo.findAllByRole(Role.MANAGER));
                break;
            }
            case MANAGER: {
                subjects = user.getSubjects();
                break;
            }
            case USER: {
                if (user.getClassroom() != null) {
                    subjects = user.getClassroom().getSubjects();
                }
                break;
            }
        }

        model.addAttribute("subjects", subjects);
        return "subjects";
    }

    @GetMapping("/{subjectId}")
    public String subject(Model model, @PathVariable Long subjectId) {
        AddAttributesSubject(model, subjectId);
        return "subject";
    }

    @PostMapping("/add")
    public String add(@RequestParam String name, @RequestParam Long teacherId, @RequestParam Long classroomId) {
        subjectRepo.save(new Subject(name, classroomRepo.getReferenceById(classroomId), userRepo.getReferenceById(teacherId)));
        return "redirect:/subjects";
    }

    @PostMapping("/{subjectId}/edit")
    public String edit(@RequestParam String name, @RequestParam Long teacherId, @RequestParam Long classroomId, @PathVariable Long subjectId) {
        Subject subject = subjectRepo.getReferenceById(subjectId);
        subject.set(name, classroomRepo.getReferenceById(classroomId), userRepo.getReferenceById(teacherId));
        subjectRepo.save(subject);
        return "redirect:/subjects/{subjectId}";
    }

    @PostMapping("/{subjectId}/delete")
    public String delete(@PathVariable Long subjectId) {
        subjectRepo.deleteById(subjectId);
        return "redirect:/subjects/{subjectId}";
    }
}
