package com.schedule.model;

import com.schedule.model.enums.ScoreType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Lesson implements Serializable {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String date;
    private int position;
    private String homework = "";

    @ManyToOne
    private Subject subject;
    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL)
    private List<Score> scores = new ArrayList<>();

    public Lesson(String date, int position, Subject subject) {
        this.date = date;
        this.position = position;
        this.subject = subject;
    }

    public void set(String date, int position) {
        this.date = date;
        this.position = position;
    }

    public int getAvgScore() {
        if (scores.isEmpty()) return 0;
        return scores.stream().reduce(0, (i, score) -> i + score.getType().getScore(), Integer::sum) / scores.size();
    }

    public Score checkScore(Long studentId) {
        for (Score score : scores) {
            if (score.getStudent().getId().equals(studentId)) {
                return score;
            }
        }
        return null;
    }

    public ScoreType checkScoreType(ScoreType type, Long studentId) {
        for (Score score : scores) {
            if (score.getStudent().getId().equals(studentId) && score.getType().equals(type)) {
                return score.getType();
            }
        }
        return null;
    }
}