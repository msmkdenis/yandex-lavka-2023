package ru.burtsev.yandexlavka2023.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.burtsev.yandexlavka2023.dto.CourierDto;
import ru.burtsev.yandexlavka2023.dto.CreateCourierDto;
import ru.burtsev.yandexlavka2023.dto.CreateCourierRequest;
import ru.burtsev.yandexlavka2023.dto.CreateCouriersResponse;
import ru.burtsev.yandexlavka2023.entity.Courier;
import ru.burtsev.yandexlavka2023.mapper.CourierMapper;
import ru.burtsev.yandexlavka2023.repository.CourierRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourierServiceImpl implements CourierService {

    private final CourierRepository courierRepository;

    @Override
    public CreateCouriersResponse saveCouriers(CreateCourierRequest courierRequest) {
        List<CreateCourierDto> createCourierDtos = courierRequest.getCouriers();
        List<Courier> couriers = createCourierDtos.stream().map(CourierMapper::toCourier).collect(Collectors.toList());
        courierRepository.saveAll(couriers);
        List<CourierDto> courierDtos = couriers.stream().map(CourierMapper::toCourierDto).collect(Collectors.toList());
        return new CreateCouriersResponse(courierDtos);
    }
}
