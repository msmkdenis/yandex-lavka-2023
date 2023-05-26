package ru.burtsev.yandexlavka2023.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.burtsev.yandexlavka2023.dto.CreateCourierRequest;
import ru.burtsev.yandexlavka2023.dto.CreateCouriersResponse;
import ru.burtsev.yandexlavka2023.service.CourierService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/couriers")
public class CourierController {

    private final CourierService courierService;

    @PostMapping
    CreateCouriersResponse saveCouriers(@RequestBody CreateCourierRequest courierRequest){
        return courierService.saveCouriers(courierRequest);
    }
}
