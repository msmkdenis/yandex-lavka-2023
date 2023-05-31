package ru.burtsev.yandexlavka2023.orders.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.burtsev.yandexlavka2023.orders.dto.CompleteOrderRequestDto;
import ru.burtsev.yandexlavka2023.orders.dto.CreateOrderRequest;
import ru.burtsev.yandexlavka2023.orders.dto.OrderDto;
import ru.burtsev.yandexlavka2023.orders.service.OrderService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
@Validated
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    List<OrderDto> postOrders(@RequestBody @Valid CreateOrderRequest createOrderRequest) {
        return orderService.saveOrders(createOrderRequest);
    }

    @GetMapping("/{orderId}")
    public OrderDto getOrderById(@PathVariable Long orderId){
        return orderService.getOrderById(orderId);
    }

    @GetMapping
    public List<OrderDto> getOrders(
            @PositiveOrZero @RequestParam(defaultValue = "0") int offset,
            @Positive @RequestParam(defaultValue = "1") int limit
    ) {
        return orderService.getOrders(offset, limit);
    }

    @PostMapping("/complete")
    public List<OrderDto> saveCompleteOrders(@RequestBody @Valid CompleteOrderRequestDto completeOrderRequestDto) {
        return orderService.saveCompletedOrders(completeOrderRequestDto);
    }
}
