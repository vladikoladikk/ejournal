package com.schedule.repo;

import com.schedule.model.Schedule;
import com.schedule.model.enums.DayOfWeek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepo extends JpaRepository<Schedule, Long> {
    Schedule findBySubject_Classroom_IdAndPositionAndDayOfWeek(Long classroomId, int position, DayOfWeek dayOfWeek);
}