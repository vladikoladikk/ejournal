package com.schedule.repo;

import com.schedule.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepo extends JpaRepository<Lesson, Long> {
    Lesson findByDateAndPositionAndSubject_Id(String date, int position, Long subjectId);
}