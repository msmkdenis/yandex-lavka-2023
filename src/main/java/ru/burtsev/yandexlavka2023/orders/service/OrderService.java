package ru.burtsev.yandexlavka2023.orders.service;

import ru.burtsev.yandexlavka2023.orders.dto.CreateOrderRequest;
import ru.burtsev.yandexlavka2023.orders.dto.CreateOrderResponse;
import ru.burtsev.yandexlavka2023.orders.dto.OrderDto;
import ru.burtsev.yandexlavka2023.orders.entity.Order;

import java.time.OffsetDateTime;
import java.util.List;

public interface OrderService {

    CreateOrderResponse saveOrders(CreateOrderRequest createOrderRequest);

    OrderDto findOrderById(Long orderId);

    List<OrderDto> findOrders(Integer offset, Integer limit);

    List<Order> findOrdersOrThrow(List<Long> orderIds);

    List<Order> findCompletedOrdersByCourierBetweenDates(Long courierId, OffsetDateTime startDate, OffsetDateTime endDate);
}
