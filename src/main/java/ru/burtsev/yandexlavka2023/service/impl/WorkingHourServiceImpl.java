package ru.burtsev.yandexlavka2023.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.burtsev.yandexlavka2023.entity.WorkingHour;
import ru.burtsev.yandexlavka2023.repository.WorkingHourRepository;
import ru.burtsev.yandexlavka2023.service.WorkingHourService;

@RequiredArgsConstructor
@Service
public class WorkingHourServiceImpl implements WorkingHourService {

    private final WorkingHourRepository workingHourRepository;

    @Override
    public WorkingHour getWorkingHourById(Long id) {
        return workingHourRepository.findById(id).get();
    }
}
