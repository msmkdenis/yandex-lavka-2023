package ru.burtsev.yandexlavka2023.orders.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.burtsev.yandexlavka2023.facade.DeliveryFacade;
import ru.burtsev.yandexlavka2023.orders.dto.CompleteOrderRequestDto;
import ru.burtsev.yandexlavka2023.orders.dto.CreateOrderRequest;
import ru.burtsev.yandexlavka2023.orders.dto.CreateOrderResponse;
import ru.burtsev.yandexlavka2023.orders.dto.OrderDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
@Validated
public class OrderController {

    private final DeliveryFacade deliveryFacade;

    @PostMapping
    CreateOrderResponse postOrders(@RequestBody @Valid CreateOrderRequest createOrderRequest) {
        return deliveryFacade.saveOrders(createOrderRequest);
    }

    @GetMapping("/{orderId}")
    public OrderDto getOrderById(@PathVariable Long orderId){
        return deliveryFacade.getOrderById(orderId);
    }

    @GetMapping
    public List<OrderDto> getOrders(
            @PositiveOrZero @RequestParam(defaultValue = "0") int offset,
            @Positive @RequestParam(defaultValue = "1") int limit
    ) {
        return deliveryFacade.getOrders(offset, limit);
    }

    @PostMapping("/complete")
    public List<OrderDto> saveCompleteOrders(@RequestBody @Valid CompleteOrderRequestDto completeOrderRequestDto) {
        return deliveryFacade.saveCompletedOrders(completeOrderRequestDto);
    }
}
