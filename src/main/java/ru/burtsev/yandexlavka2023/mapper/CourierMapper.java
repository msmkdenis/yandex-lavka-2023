package ru.burtsev.yandexlavka2023.mapper;

import lombok.experimental.UtilityClass;
import ru.burtsev.yandexlavka2023.dto.CourierDto;
import ru.burtsev.yandexlavka2023.dto.CreateCourierDto;
import ru.burtsev.yandexlavka2023.entity.Courier;
import ru.burtsev.yandexlavka2023.entity.Region;
import ru.burtsev.yandexlavka2023.entity.WorkingHour;

import java.time.LocalTime;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class CourierMapper {

    public static Courier toCourier(CreateCourierDto courierDto,
                                    Set<Region> regions,
                                    Set<WorkingHour> workingHours) {
        return Courier.builder()
                .courierType(courierDto.getCourierType())
                .workingHours(workingHours)
                .regions(regions)
                .build();
    }

    public static Set<WorkingHour> toWorkingHours(CreateCourierDto createCourierDto) {
        Set<String> workingHoursString = createCourierDto.getWorkingHours();
        return workingHoursString.stream()
                .map(workingHour -> {
                    String[] split = workingHour.split("-");
                    return WorkingHour.builder()
                            .startTime(LocalTime.parse(split[0]))
                            .endTime(LocalTime.parse(split[1]))
                            .startTimeEndTime(workingHour)
                            .build();
                })
                .collect(Collectors.toSet());
    }

    public static Set<Region> toRegions(CreateCourierDto createCourierDto) {
        Set<Integer> regionInteger = createCourierDto.getRegions();
        return regionInteger.stream()
                .map(regionId -> Region.builder()
                        .regionId(regionId)
                        .build())
                .collect(Collectors.toSet());
    }

/*    public static Courier toCourier(CreateCourierDto courierDto) {
        return Courier.builder()
                .courierType(courierDto.getCourierType())
                .workingHours(courierDto.getWorkingHours().stream()
                        .map(workingHour -> {
                            String[] split = workingHour.split("-");
                            return new WorkingHour(LocalTime.parse(split[0]), LocalTime.parse(split[1]));
                        })
                        .collect(Collectors.toSet()))
                .regions(courierDto.getRegions().stream()
                        .map(Region::new)
                        .collect(Collectors.toSet()))
                .build();
    }*/

    public static CourierDto toCourierDto(Courier courier) {
        return CourierDto.builder()
                .id(courier.getId())
                .courierType(courier.getCourierType())
                .regions(courier.getRegions().stream().map(Region::getRegionId).collect(Collectors.toSet()))
                .workingHours(courier.getWorkingHours().stream().map(WorkingHour::toString).collect(Collectors.toSet()))
                .build();
    }

}
