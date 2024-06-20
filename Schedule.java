package com.schedule.model;

import com.schedule.model.enums.DayOfWeek;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Schedule implements Serializable {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    private int position;
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @ManyToOne
    private Subject subject;

    public Schedule(int position, DayOfWeek dayOfWeek, Subject subject) {
        this.position = position;
        this.dayOfWeek = dayOfWeek;
        this.subject = subject;
    }

    public void set(int position, DayOfWeek dayOfWeek) {
        this.position = position;
        this.dayOfWeek = dayOfWeek;
    }
}