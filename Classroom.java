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
public class Classroom implements Serializable {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "classroom")
    private List<AppUser> students = new ArrayList<>();
    @OneToMany(mappedBy = "classroom", cascade = CascadeType.ALL)
    private List<Subject> subjects = new ArrayList<>();

    public Classroom(String name) {
        this.name = name;
    }

    public Map<DayOfWeek, List<Schedule>> getSchedulesPart1() {
        Map<DayOfWeek, List<Schedule>> map = new TreeMap<>();

        DayOfWeek[] dayOfWeeks = DayOfWeek.values();

        List<Schedule> schedules = new ArrayList<>();
        for (Subject i : subjects){
            schedules.addAll(i.getSchedules());
        }

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

        List<Schedule> schedules = new ArrayList<>();
        for (Subject i : subjects){
            schedules.addAll(i.getSchedules());
        }

        for (int i = 3; i < dayOfWeeks.length; i++) {
            int finalI = i;
            List<Schedule> temp = new ArrayList<>(schedules.stream().filter(schedule -> schedule.getDayOfWeek() == dayOfWeeks[finalI]).toList());
            temp.sort(Comparator.comparing(Schedule::getPosition));
            map.put(dayOfWeeks[i], temp);
        }

        return map;
    }
}