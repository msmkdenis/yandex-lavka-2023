package ru.burtsev.yandexlavka2023.couriers.repository;

import ru.burtsev.yandexlavka2023.couriers.entity.CourierType;
import ru.burtsev.yandexlavka2023.couriers.entity.Region;
import ru.burtsev.yandexlavka2023.couriers.entity.WorkingHour;
import ru.burtsev.yandexlavka2023.orders.entity.Order;

import java.util.List;
import java.util.Set;

public interface CourierFullInfo {

    Long getId();

    CourierType getCourierType();

    Set<Region> getRegions();

    Set<WorkingHour> getWorkingHours();

    List<Order> getCompletedOrders();
}
