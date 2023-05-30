package ru.burtsev.yandexlavka2023.couriers.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.burtsev.yandexlavka2023.couriers.entity.WorkingHour;
import ru.burtsev.yandexlavka2023.couriers.repository.WorkingHourRepository;
import ru.burtsev.yandexlavka2023.couriers.service.WorkingHourService;

@RequiredArgsConstructor
@Service
public class WorkingHourServiceImpl implements WorkingHourService {

    private final WorkingHourRepository workingHourRepository;

    @Override
    public WorkingHour getWorkingHourById(Long id) {
        return workingHourRepository.findById(id).get();
    }
}
