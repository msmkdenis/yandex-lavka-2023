package ru.burtsev.yandexlavka2023.couriers.service;

import ru.burtsev.yandexlavka2023.couriers.dto.*;

public interface CourierService {

    CreateCouriersResponse saveCouriers(CreateCourierRequest courierRequest);

    CourierDto getCourierById(Long courierId);

    GetCouriersResponse getCouriers(Integer offset, Integer limit);

    void deleteCourierById(Long courierId);

    GetCourierMetaInfoResponse getCourierMetaInfo(Long courierId, String startDate, String endDate);
}
