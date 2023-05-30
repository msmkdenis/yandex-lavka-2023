package ru.burtsev.yandexlavka2023.couriers.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.burtsev.yandexlavka2023.couriers.entity.WorkingHour;
import ru.burtsev.yandexlavka2023.couriers.service.WorkingHourService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/workinghour")
public class WorkingHourController {

    private final WorkingHourService workingHourService;

    @GetMapping("/{workingId}")
    WorkingHour getById(@PathVariable Long workingId) {
        return workingHourService.getWorkingHourById(workingId);
    }
}
