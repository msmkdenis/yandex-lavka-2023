package ru.burtsev.yandexlavka2023.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.burtsev.yandexlavka2023.dto.CourierDto;
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

    @GetMapping("/{courierId}")
    CourierDto getCourierById(@PathVariable Long courierId){
        return courierService.getCourierById(courierId);
    }
}
