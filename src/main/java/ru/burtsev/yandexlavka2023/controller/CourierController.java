package ru.burtsev.yandexlavka2023.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.burtsev.yandexlavka2023.dto.CourierDto;
import ru.burtsev.yandexlavka2023.dto.CreateCourierRequest;
import ru.burtsev.yandexlavka2023.dto.CreateCouriersResponse;
import ru.burtsev.yandexlavka2023.dto.GetCouriersResponse;
import ru.burtsev.yandexlavka2023.service.CourierService;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/couriers")
public class CourierController {

    private final CourierService courierService;

    @PostMapping
    public CreateCouriersResponse saveCouriers(@RequestBody @Valid CreateCourierRequest courierRequest){
        return courierService.saveCouriers(courierRequest);
    }

    @GetMapping("/{courierId}")
    public CourierDto getCourierById(@PathVariable Long courierId){
        return courierService.getCourierById(courierId);
    }

    @GetMapping
    public GetCouriersResponse getCouriers(
            @PositiveOrZero @RequestParam(defaultValue = "0") int offset,
            @Positive @RequestParam(defaultValue = "1") int limit
    ) {
        return courierService.getCouriers(offset, limit);
    }

    @DeleteMapping("/{courierId}")
    public void deleteCourierById(@PathVariable Long courierId){
        courierService.deleteCourierById(courierId);
    }
}
