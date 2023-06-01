package ru.burtsev.yandexlavka2023.couriers.repository;

import ru.burtsev.yandexlavka2023.couriers.entity.CourierType;
import ru.burtsev.yandexlavka2023.couriers.entity.Region;
import ru.burtsev.yandexlavka2023.couriers.entity.WorkingHour;

import java.util.Set;

public interface CourierMetaInfo {

    Long getId();

    CourierType getCourierType();

    Set<Region> getRegions();

    Set<WorkingHour> getWorkingHours();
}
