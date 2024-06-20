package com.schedule.controller;

import com.schedule.controller.main.Main;
import com.schedule.model.AppUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/classrooms/{classroomId}/students")
public class ClassroomStudentCont extends Main {

    @PostMapping("/add")
    public String addStudent(@RequestParam Long studentId, @PathVariable Long classroomId) {
        AppUser student = userRepo.getReferenceById(studentId);
        student.setClassroom(classroomRepo.getReferenceById(classroomId));
        userRepo.save(student);
        return "redirect:/classrooms/{classroomId}";
    }

    @GetMapping("/{studentId}/delete")
    public String deleteStudent(@PathVariable Long studentId, @PathVariable Long classroomId) {
        AppUser student = userRepo.getReferenceById(studentId);
        student.setClassroom(null);
        userRepo.save(student);
        return "redirect:/classrooms/{classroomId}";
    }
}
