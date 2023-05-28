package ru.burtsev.yandexlavka2023.service;

import ru.burtsev.yandexlavka2023.dto.CourierDto;
import ru.burtsev.yandexlavka2023.dto.CreateCourierRequest;
import ru.burtsev.yandexlavka2023.dto.CreateCouriersResponse;

public interface CourierService {

    CreateCouriersResponse saveCouriers(CreateCourierRequest courierRequest);

    CourierDto getCourierById(Long courierId);

}
