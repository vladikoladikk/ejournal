package com.schedule.model;

import com.schedule.model.enums.DayOfWeek;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Subject implements Serializable {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String name;

    @ManyToOne
    private Classroom classroom;
    @ManyToOne
    private AppUser teacher;
    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private List<Schedule> schedules = new ArrayList<>();
    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private List<Lesson> lessons = new ArrayList<>();

    public Subject(String name, Classroom classroom, AppUser teacher) {
        this.name = name;
        this.classroom = classroom;
        this.teacher = teacher;
    }

    public void set(String name, Classroom classroom, AppUser teacher) {
        this.name = name;
        this.classroom = classroom;
        this.teacher = teacher;
    }

    public List<Lesson> getLessons() {
        lessons.sort(Comparator.comparing(Lesson::getDate));
        return lessons;
    }

    public List<Schedule> getSchedules() {
        List<Schedule> res = new ArrayList<>();

        DayOfWeek[] dayOfWeeks = DayOfWeek.values();

        for (int i = 0; i < dayOfWeeks.length; i++) {
            int finalI = i;
            List<Schedule> temp = new ArrayList<>(schedules.stream().filter(schedule -> schedule.getDayOfWeek() == dayOfWeeks[finalI]).toList());
            temp.sort(Comparator.comparing(Schedule::getPosition));
            res.addAll(temp);
        }

        return res;
    }

    public Map<DayOfWeek, List<Schedule>> getSchedulesPart1() {
        Map<DayOfWeek, List<Schedule>> map = new TreeMap<>();

        DayOfWeek[] dayOfWeeks = DayOfWeek.values();

        for (int i = 0; i < dayOfWeeks.length - 3; i++) {
            int finalI = i;
            List<Schedule> temp = new ArrayList<>(schedules.stream().filter(schedule -> schedule.getDayOfWeek() == dayOfWeeks[finalI]).toList());
            temp.sort(Comparator.comparing(Schedule::getPosition));
            map.put(dayOfWeeks[i], temp);
        }

        return map;
    }

    public Map<DayOfWeek, List<Schedule>> getSchedulesPart2() {
        Map<DayOfWeek, List<Schedule>> map = new TreeMap<>();

        DayOfWeek[] dayOfWeeks = DayOfWeek.values();

        for (int i = 3; i < dayOfWeeks.length; i++) {
            int finalI = i;
            List<Schedule> temp = new ArrayList<>(schedules.stream().filter(schedule -> schedule.getDayOfWeek() == dayOfWeeks[finalI]).toList());
            temp.sort(Comparator.comparing(Schedule::getPosition));
            map.put(dayOfWeeks[i], temp);
        }

        return map;
    }
}