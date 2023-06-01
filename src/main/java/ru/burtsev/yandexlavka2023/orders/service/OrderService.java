package ru.burtsev.yandexlavka2023.orders.service;

import ru.burtsev.yandexlavka2023.orders.dto.CompleteOrderRequestDto;
import ru.burtsev.yandexlavka2023.orders.dto.CreateOrderRequest;
import ru.burtsev.yandexlavka2023.orders.dto.CreateOrderResponse;
import ru.burtsev.yandexlavka2023.orders.dto.OrderDto;

import java.util.List;

public interface OrderService {

    CreateOrderResponse saveOrders(CreateOrderRequest createOrderRequest);

    OrderDto getOrderById(Long orderId);

    List<OrderDto> getOrders(Integer offset, Integer limit);

    List<OrderDto> saveCompletedOrders(CompleteOrderRequestDto completeOrderRequestDto);
}
