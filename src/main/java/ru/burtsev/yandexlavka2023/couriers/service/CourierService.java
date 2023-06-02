package ru.burtsev.yandexlavka2023.couriers.service;

import ru.burtsev.yandexlavka2023.couriers.dto.CourierDto;
import ru.burtsev.yandexlavka2023.couriers.dto.CreateCourierRequest;
import ru.burtsev.yandexlavka2023.couriers.dto.CreateCouriersResponse;
import ru.burtsev.yandexlavka2023.couriers.dto.GetCouriersResponse;
import ru.burtsev.yandexlavka2023.couriers.entity.Courier;
import ru.burtsev.yandexlavka2023.couriers.repository.CourierFullInfo;

import java.util.List;
import java.util.Set;

public interface CourierService {

    CreateCouriersResponse saveCouriers(CreateCourierRequest courierRequest);

    CourierDto findCourierById(Long courierId);

    GetCouriersResponse findCouriers(Integer offset, Integer limit);

    void deleteCourierById(Long courierId);

    CourierFullInfo findCourierShortInfo(Long courierId);

    List<Courier> findCouriersOrThrow(Set<Long> courierIds);
}
