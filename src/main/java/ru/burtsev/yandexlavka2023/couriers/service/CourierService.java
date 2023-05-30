package ru.burtsev.yandexlavka2023.couriers.service;

import ru.burtsev.yandexlavka2023.couriers.dto.CourierDto;
import ru.burtsev.yandexlavka2023.couriers.dto.CreateCourierRequest;
import ru.burtsev.yandexlavka2023.couriers.dto.CreateCouriersResponse;
import ru.burtsev.yandexlavka2023.couriers.dto.GetCouriersResponse;

public interface CourierService {

    CreateCouriersResponse saveCouriers(CreateCourierRequest courierRequest);

    CourierDto getCourierById(Long courierId);

    GetCouriersResponse getCouriers(Integer offset, Integer limit);

    void deleteCourierById(Long courierId);
}
