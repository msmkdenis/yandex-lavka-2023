package ru.burtsev.yandexlavka2023.couriers.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.burtsev.yandexlavka2023.couriers.dto.CourierDto;
import ru.burtsev.yandexlavka2023.couriers.dto.CreateCourierRequest;
import ru.burtsev.yandexlavka2023.couriers.dto.CreateCouriersResponse;
import ru.burtsev.yandexlavka2023.couriers.dto.GetCouriersResponse;
import ru.burtsev.yandexlavka2023.couriers.entity.Courier;
import ru.burtsev.yandexlavka2023.couriers.repository.CourierRepository;
import ru.burtsev.yandexlavka2023.couriers.service.CourierService;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/couriers")
public class CourierController {

    private final CourierService courierService;
    private final CourierRepository courierRepository;

    @PostMapping
    public CreateCouriersResponse saveCouriers(@RequestBody @Valid CreateCourierRequest courierRequest){
        return courierService.saveCouriers(courierRequest);
    }

    @GetMapping("/{courierId}")
    public CourierDto getCourierById(@PathVariable Long courierId){
        return courierService.getCourierById(courierId);
    }

    @GetMapping("/id/{courierId}")
    public Courier getCourierId(@PathVariable Long courierId){
        return courierRepository.findById(courierId).get();
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
