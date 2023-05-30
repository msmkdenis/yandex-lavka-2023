package ru.burtsev.yandexlavka2023.couriers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.burtsev.yandexlavka2023.couriers.entity.WorkingHour;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface WorkingHourRepository extends JpaRepository<WorkingHour, Long> {

    Optional<WorkingHour> findWorkingHourByStartTimeAndEndTime(LocalTime startTime, LocalTime endTime);

    Set<WorkingHour> findAllByStartTimeEndTimeIn(List<String> startTimeEndTime);
}
