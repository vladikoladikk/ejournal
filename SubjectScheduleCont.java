package com.schedule.controller;

import com.schedule.controller.main.Main;
import com.schedule.model.Schedule;
import com.schedule.model.enums.DayOfWeek;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/subjects/{subjectId}/schedules")
public class SubjectScheduleCont extends Main {

    @PostMapping("/add")
    public String add(Model model, @RequestParam int position, @RequestParam DayOfWeek dayOfWeek, @PathVariable Long subjectId) {
        if (scheduleRepo.findBySubject_Classroom_IdAndPositionAndDayOfWeek(subjectRepo.getReferenceById(subjectId).getClassroom().getId(), position, dayOfWeek) != null) {
            model.addAttribute("message", "По такому порядковому номеру и дню недели уже имеется урок");

            AddAttributesSubject(model, subjectId);

            return "subject";
        }
        scheduleRepo.save(new Schedule(position, dayOfWeek, subjectRepo.getReferenceById(subjectId)));
        return "redirect:/subjects/{subjectId}";
    }

    @PostMapping("/{scheduleId}/edit")
    public String edit(Model model, @RequestParam int position, @RequestParam DayOfWeek dayOfWeek, @PathVariable Long subjectId, @PathVariable Long scheduleId) {
        if (scheduleRepo.findBySubject_Classroom_IdAndPositionAndDayOfWeek(subjectRepo.getReferenceById(subjectId).getClassroom().getId(), position, dayOfWeek) != null) {
            model.addAttribute("message", "По такому порядковому номеру и дню недели уже имеется урок");

            AddAttributesSubject(model, subjectId);

            return "subject";
        }

        Schedule schedule = scheduleRepo.getReferenceById(scheduleId);
        schedule.set(position, dayOfWeek);
        scheduleRepo.save(schedule);

        return "redirect:/subjects/{subjectId}";
    }

    @GetMapping("/{scheduleId}/delete")
    public String delete(@PathVariable Long subjectId, @PathVariable Long scheduleId) {
        scheduleRepo.deleteById(scheduleId);
        return "redirect:/subjects/{subjectId}";
    }


}
