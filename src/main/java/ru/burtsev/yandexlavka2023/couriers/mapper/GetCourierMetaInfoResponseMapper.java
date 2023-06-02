package ru.burtsev.yandexlavka2023.couriers.mapper;

import lombok.experimental.UtilityClass;
import ru.burtsev.yandexlavka2023.couriers.dto.GetCourierMetaInfoResponse;
import ru.burtsev.yandexlavka2023.couriers.entity.Region;
import ru.burtsev.yandexlavka2023.couriers.entity.WorkingHour;
import ru.burtsev.yandexlavka2023.couriers.repository.CourierFullInfo;

import java.util.stream.Collectors;

@UtilityClass
public class GetCourierMetaInfoResponseMapper {
    public static GetCourierMetaInfoResponse toCourierMetaInfo(CourierFullInfo courierFullInfo,
                                                               Integer courierEarnings,
                                                               Long courierRating)
    {
        return GetCourierMetaInfoResponse.builder()
                .courierId(courierFullInfo.getId())
                .courierType(courierFullInfo.getCourierType())
                .regions(courierFullInfo.getRegions()
                        .stream()
                        .map(Region::getRegionId)
                        .collect(Collectors.toList()))
                .workingHours(courierFullInfo.getWorkingHours()
                        .stream()
                        .map(WorkingHour::getStartTimeEndTime)
                        .collect(Collectors.toList()))
                .earnings(courierEarnings)
                .rating(courierRating)
                .build();
    }
}
