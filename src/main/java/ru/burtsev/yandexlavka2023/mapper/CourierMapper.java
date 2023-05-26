package ru.burtsev.yandexlavka2023.mapper;

import lombok.experimental.UtilityClass;
import ru.burtsev.yandexlavka2023.dto.CourierDto;
import ru.burtsev.yandexlavka2023.dto.CreateCourierDto;
import ru.burtsev.yandexlavka2023.entity.Courier;
import ru.burtsev.yandexlavka2023.entity.Region;
import ru.burtsev.yandexlavka2023.entity.WorkingHours;

import java.time.LocalTime;
import java.util.stream.Collectors;

@UtilityClass
public class CourierMapper {
    public static Courier toCourier(CreateCourierDto courierDto) {
        return Courier.builder()
                .courierType(courierDto.getCourierType())
                .workingHours(courierDto.getWorkingHours().stream()
                        .map(workingHour -> {
                            String[] split = workingHour.split("-");
                            return new WorkingHours(LocalTime.parse(split[0]), LocalTime.parse(split[1]));
                        })
                        .collect(Collectors.toSet()))
                .regions(courierDto.getRegions().stream()
                        .map(Region::new)
                        .collect(Collectors.toSet()))
                .build();
    }

    public static CourierDto toCourierDto(Courier courier) {
        return CourierDto.builder()
                .id(courier.getId())
                .courierType(courier.getCourierType())
                .regions(courier.getRegions().stream().map(Region::getRegionId).collect(Collectors.toSet()))
                .workingHours(courier.getWorkingHours().stream().map(WorkingHours::toString).collect(Collectors.toSet()))
                .build();
    }

}
