package com.schedule.controller.main;

import com.schedule.model.AppUser;
import com.schedule.model.Lesson;
import com.schedule.model.Score;
import com.schedule.model.Subject;
import com.schedule.model.enums.DayOfWeek;
import com.schedule.model.enums.Role;
import com.schedule.model.enums.ScoreType;
import com.schedule.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.List;

public class Main {
    @Autowired
    protected UserRepo userRepo;
    @Autowired
    protected SubjectRepo subjectRepo;
    @Autowired
    protected ClassroomRepo classroomRepo;
    @Autowired
    protected LessonRepo lessonRepo;
    @Autowired
    protected ScheduleRepo scheduleRepo;
    @Autowired
    protected ScoreRepo scoreRepo;

    public static long ONE_DAY = 1000 * 60 * 60 * 24;

    protected void AddAttributesSubject(Model model, Long subjectId) {
        getCurrentUserAndRole(model);

        Subject subject = subjectRepo.getReferenceById(subjectId);
        model.addAttribute("subject", subject);

        if (getUser().getRole() == Role.ADMIN) {
            model.addAttribute("classrooms", classroomRepo.findAll());
            model.addAttribute("teachers", userRepo.findAllByRole(Role.MANAGER));
            model.addAttribute("dayOfWeeks", DayOfWeek.values());
        }

        List<Lesson> lessons = subject.getLessons();
        String[] lessonString = new String[lessons.size()];
        int[] lessonValues = new int[lessons.size()];

        ScoreType[] scoreTypes = ScoreType.values();

        String[] scoreTypesString = new String[scoreTypes.length];
        int[] scoreTypeValues = new int[scoreTypes.length];

        for (int i = 0; i < scoreTypes.length; i++) {
            scoreTypesString[i] = scoreTypes[i].getName();
        }

        if (getUser().getRole() == Role.USER) {
            Long studentId = getUser().getId();

            for (int i = 0; i < lessons.size(); i++) {
                lessonString[i] = lessons.get(i).getDate() + " | " + lessons.get(i).getPosition();
                Score score = lessons.get(i).checkScore(studentId);
                if (score == null) {
                    lessonValues[i] = 0;
                    scoreTypeValues[0] = scoreTypeValues[0] + 1;
                } else {
                    lessonValues[i] = score.getType().getScore();
                    switch (score.getType()) {
                        case N -> scoreTypeValues[0] = scoreTypeValues[0] + 1;
                        case SCORE0 -> scoreTypeValues[1] = scoreTypeValues[1] + 1;
                        case SCORE1 -> scoreTypeValues[2] = scoreTypeValues[2] + 1;
                        case SCORE2 -> scoreTypeValues[3] = scoreTypeValues[3] + 1;
                        case SCORE3 -> scoreTypeValues[4] = scoreTypeValues[4] + 1;
                        case SCORE4 -> scoreTypeValues[5] = scoreTypeValues[5] + 1;
                        case SCORE5 -> scoreTypeValues[6] = scoreTypeValues[6] + 1;
                        case SCORE6 -> scoreTypeValues[7] = scoreTypeValues[7] + 1;
                        case SCORE7 -> scoreTypeValues[8] = scoreTypeValues[8] + 1;
                        case SCORE8 -> scoreTypeValues[9] = scoreTypeValues[9] + 1;
                        case SCORE9 -> scoreTypeValues[10] = scoreTypeValues[10] + 1;
                        case SCORE10 -> scoreTypeValues[11] = scoreTypeValues[11] + 1;

                    }
                }
            }
        } else {
            for (int i = 0; i < lessons.size(); i++) {
                lessonString[i] = lessons.get(i).getDate() + " | " + lessons.get(i).getPosition();
                lessonValues[i] = lessons.get(i).getAvgScore();
            }

            for (Lesson i : lessons) {
                for (Score j : i.getScores()) {
                    switch (j.getType()) {
                        case N -> scoreTypeValues[0] = scoreTypeValues[0] + 1;
                        case SCORE0 -> scoreTypeValues[1] = scoreTypeValues[1] + 1;
                        case SCORE1 -> scoreTypeValues[2] = scoreTypeValues[2] + 1;
                        case SCORE2 -> scoreTypeValues[3] = scoreTypeValues[3] + 1;
                        case SCORE3 -> scoreTypeValues[4] = scoreTypeValues[4] + 1;
                        case SCORE4 -> scoreTypeValues[5] = scoreTypeValues[5] + 1;
                        case SCORE5 -> scoreTypeValues[6] = scoreTypeValues[6] + 1;
                        case SCORE6 -> scoreTypeValues[7] = scoreTypeValues[7] + 1;
                        case SCORE7 -> scoreTypeValues[8] = scoreTypeValues[8] + 1;
                        case SCORE8 -> scoreTypeValues[9] = scoreTypeValues[9] + 1;
                        case SCORE9 -> scoreTypeValues[10] = scoreTypeValues[10] + 1;
                        case SCORE10 -> scoreTypeValues[11] = scoreTypeValues[11] + 1;
                        
                    }
                }
            }

        }

        model.addAttribute("lessonString", lessonString);
        model.addAttribute("lessonValues", lessonValues);
        model.addAttribute("scoreTypesString", scoreTypesString);
        model.addAttribute("scoreTypeValues", scoreTypeValues);

    }

    protected void getCurrentUserAndRole(Model model) {
        model.addAttribute("role", getRole());
        model.addAttribute("user", getUser());
        model.addAttribute("theme", getTheme());
    }

    protected AppUser getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if ((!(auth instanceof AnonymousAuthenticationToken)) && auth != null) {
            UserDetails userDetail = (UserDetails) auth.getPrincipal();
            return userRepo.findByUsername(userDetail.getUsername());
        }
        return null;
    }

    private String getRole() {
        AppUser user = getUser();
        if (user == null) return "NOT";
        return user.getRole().name();
    }

    private String getTheme() {
        AppUser user = getUser();
        if (user == null) return "light";
        if (user.isTheme()) return "light";
        else return "dark";
    }

    public static String getDate() {
        return LocalDateTime.now().toString().substring(0, 10);
    }

    public static float round(double value) {
        long factor = (long) Math.pow(10, 2);
        value = value * factor;
        long tmp = Math.round(value);
        return (float) tmp / factor;
    }
}