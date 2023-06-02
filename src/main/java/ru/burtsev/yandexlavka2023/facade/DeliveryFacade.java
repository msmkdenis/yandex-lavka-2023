package ru.burtsev.yandexlavka2023.facade;

import ru.burtsev.yandexlavka2023.couriers.dto.*;
import ru.burtsev.yandexlavka2023.orders.dto.CompleteOrderRequestDto;
import ru.burtsev.yandexlavka2023.orders.dto.CreateOrderRequest;
import ru.burtsev.yandexlavka2023.orders.dto.CreateOrderResponse;
import ru.burtsev.yandexlavka2023.orders.dto.OrderDto;

import java.util.List;

public interface DeliveryFacade {

    CreateCouriersResponse saveCouriers(CreateCourierRequest courierRequest);

    CourierDto getCourierById(Long courierId);

    GetCouriersResponse getCouriers(Integer offset, Integer limit);

    void deleteCourierById(Long courierId);

    GetCourierMetaInfoResponse getCourierMetaInfo(Long courierId, String startDate, String endDate);

    CreateOrderResponse saveOrders(CreateOrderRequest createOrderRequest);

    OrderDto getOrderById(Long orderId);

    List<OrderDto> getOrders(Integer offset, Integer limit);

    List<OrderDto> saveCompletedOrders(CompleteOrderRequestDto completeOrderRequestDto);
}
