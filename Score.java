package com.schedule.model;

import com.schedule.model.enums.ScoreType;
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
public class Score implements Serializable {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ScoreType type;

    @ManyToOne
    private Lesson lesson;
    @ManyToOne
    private AppUser student;

    public Score(ScoreType type, Lesson lesson, AppUser student) {
        this.type = type;
        this.lesson = lesson;
        this.student = student;
    }
}