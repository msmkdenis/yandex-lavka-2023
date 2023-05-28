package ru.burtsev.yandexlavka2023.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.burtsev.yandexlavka2023.entity.WorkingHour;
import ru.burtsev.yandexlavka2023.repository.WorkingHourRepository;

@RequiredArgsConstructor
@Service
public class WorkingHourServiceImpl implements WorkingHourService{

    private final WorkingHourRepository workingHourRepository;

    @Override
    public WorkingHour getWorkingHourById(Long id) {
        return workingHourRepository.findById(id).get();
    }
}
