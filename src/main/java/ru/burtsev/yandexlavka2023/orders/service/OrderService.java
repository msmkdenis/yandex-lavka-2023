package ru.burtsev.yandexlavka2023.orders.service;

import ru.burtsev.yandexlavka2023.orders.dto.CreateOrderRequest;
import ru.burtsev.yandexlavka2023.orders.dto.OrderDto;

import java.util.List;

public interface OrderService {

    List<OrderDto> saveOrders(CreateOrderRequest createOrderRequest);
}
