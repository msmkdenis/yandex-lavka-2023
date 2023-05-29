package ru.burtsev.yandexlavka2023.service;

import ru.burtsev.yandexlavka2023.dto.CourierDto;
import ru.burtsev.yandexlavka2023.dto.CreateCourierRequest;
import ru.burtsev.yandexlavka2023.dto.CreateCouriersResponse;
import ru.burtsev.yandexlavka2023.dto.GetCouriersResponse;
import ru.burtsev.yandexlavka2023.entity.Courier;

import java.util.List;

public interface CourierService {

    CreateCouriersResponse saveCouriers(CreateCourierRequest courierRequest);

    CourierDto getCourierById(Long courierId);

    GetCouriersResponse getCouriers(Integer offset, Integer limit);

    void deleteCourierById(Long courierId);
}
