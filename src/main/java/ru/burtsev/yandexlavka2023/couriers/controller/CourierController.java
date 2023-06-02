package ru.burtsev.yandexlavka2023.couriers.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.burtsev.yandexlavka2023.couriers.dto.*;
import ru.burtsev.yandexlavka2023.couriers.repository.CourierRepository;
import ru.burtsev.yandexlavka2023.couriers.service.CourierService;
import ru.burtsev.yandexlavka2023.facade.DeliveryFacade;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/couriers")
public class CourierController {

    private final CourierService courierService;
    private final CourierRepository courierRepository;
    private final DeliveryFacade deliveryFacade;

    @PostMapping
    public CreateCouriersResponse saveCouriers(@RequestBody @Valid CreateCourierRequest courierRequest){
        return deliveryFacade.saveCouriers(courierRequest);
    }

    @GetMapping("/{courierId}")
    public CourierDto getCourierById(@PathVariable Long courierId){
        return deliveryFacade.getCourierById(courierId);
    }

    @GetMapping("/meta-info/{courierId}")
    public GetCourierMetaInfoResponse getCourierMetaInfo(
            @PathVariable Long courierId,
            @RequestParam String startDate,
            @RequestParam String endDate
    ) {
        return deliveryFacade.getCourierMetaInfo(courierId, startDate, endDate);
    }

    @GetMapping
    public GetCouriersResponse getCouriers(
            @PositiveOrZero @RequestParam(defaultValue = "0") int offset,
            @Positive @RequestParam(defaultValue = "1") int limit
    ) {
        return deliveryFacade.getCouriers(offset, limit);
    }

    @DeleteMapping("/{courierId}")
    public void deleteCourierById(@PathVariable Long courierId){
        deliveryFacade.deleteCourierById(courierId);
    }
}
