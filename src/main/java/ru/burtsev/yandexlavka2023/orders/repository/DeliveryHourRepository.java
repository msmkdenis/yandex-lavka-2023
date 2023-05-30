package ru.burtsev.yandexlavka2023.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.burtsev.yandexlavka2023.orders.entity.DeliveryHour;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface DeliveryHourRepository extends JpaRepository<DeliveryHour, Long> {

    Optional<DeliveryHour> findWorkingHourByStartTimeAndEndTime(LocalTime startTime, LocalTime endTime);

    Set<DeliveryHour> findAllByStartTimeEndTimeIn(List<String> startTimeEndTime);
}
