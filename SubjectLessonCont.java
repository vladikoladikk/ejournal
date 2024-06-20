package com.schedule.controller;

import com.schedule.controller.main.Main;
import com.schedule.model.Lesson;
import com.schedule.model.Schedule;
import com.schedule.model.Score;
import com.schedule.model.enums.ScoreType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Controller
@RequestMapping("/subjects/{subjectId}/lessons")
public class SubjectLessonCont extends Main {

    @GetMapping("/{lessonId}")
    public String lesson(Model model, @PathVariable Long subjectId, @PathVariable Long lessonId) {
        getCurrentUserAndRole(model);
        model.addAttribute("lesson", lessonRepo.getReferenceById(lessonId));
        model.addAttribute("scoreTypes", ScoreType.values());
        return "lesson";
    }

    @PostMapping("/{lessonId}/homework")
    public String homework(@RequestParam String homework, @PathVariable Long subjectId, @PathVariable Long lessonId) {
        Lesson lesson = lessonRepo.getReferenceById(lessonId);
        lesson.setHomework(homework);
        lessonRepo.save(lesson);
        return "redirect:/subjects/{subjectId}/lessons/{lessonId}";
    }

    @PostMapping("/{lessonId}/score/{studentId}")
    public String score(@RequestParam ScoreType type, @PathVariable Long subjectId, @PathVariable Long lessonId, @PathVariable Long studentId) {
        Lesson lesson = lessonRepo.getReferenceById(lessonId);
        Score score = lesson.checkScore(studentId);

        if (score == null) {
            score = new Score(type, lesson, userRepo.getReferenceById(studentId));
        } else {
            score.setType(type);
        }

        scoreRepo.save(score);

        return "redirect:/subjects/{subjectId}/lessons/{lessonId}";
    }

    @PostMapping("/add")
    public String add(Model model, @RequestParam String date, @RequestParam int position, @PathVariable Long subjectId) {
        try {
            // Проверка на прошедшую дату
            Date selectedDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            if (selectedDate.before(new Date())) {
                throw new Exception("Selected date is in the past");
            }

            if (lessonRepo.findByDateAndPositionAndSubject_Id(date, position, subjectId) != null) throw new Exception();

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(selectedDate);

            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

            boolean flag = true;

            for (Schedule schedule : subjectRepo.getReferenceById(subjectId).getSchedules()) {
                if (schedule.getDayOfWeek().getDay() == dayOfWeek && schedule.getPosition() == position) {
                    flag = false;
                    break;
                }
            }

            if (flag) throw new Exception();
        } catch (Exception e) {
            model.addAttribute("message", "Некорректные данные");

            AddAttributesSubject(model, subjectId);

            return "subject";
        }

        lessonRepo.save(new Lesson(date, position, subjectRepo.getReferenceById(subjectId)));
        return "redirect:/subjects/{subjectId}";
    }

    @PostMapping("/{lessonId}/edit")
    public String edit(Model model, @RequestParam String date, @RequestParam int position, @PathVariable Long subjectId, @PathVariable Long lessonId) {
        try {
            // Проверка на прошедшую дату
            Date selectedDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            if (selectedDate.before(new Date())) {
                throw new Exception("Selected date is in the past");
            }

            if (lessonRepo.findByDateAndPositionAndSubject_Id(date, position, subjectId) != null) throw new Exception();

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(selectedDate);

            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

            boolean flag = true;

            for (Schedule schedule : subjectRepo.getReferenceById(subjectId).getSchedules()) {
                if (schedule.getDayOfWeek().getDay() == dayOfWeek && schedule.getPosition() == position) {
                    flag = false;
                    break;
                }
            }

            if (flag) throw new Exception();
        } catch (Exception e) {
            model.addAttribute("message", "Некорректные данные");

            AddAttributesSubject(model, subjectId);

            return "subject";
        }

        Lesson lesson = lessonRepo.getReferenceById(lessonId);
        lesson.set(date, position);
        lessonRepo.save(lesson);
        return "redirect:/subjects/{subjectId}";
    }

    @GetMapping("/{lessonId}/delete")
    public String delete(@PathVariable Long subjectId, @PathVariable Long lessonId) {
        lessonRepo.deleteById(lessonId);
        return "redirect:/subjects/{subjectId}";
    }
}