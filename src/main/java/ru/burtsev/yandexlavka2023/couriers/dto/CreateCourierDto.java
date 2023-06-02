package ru.burtsev.yandexlavka2023.couriers.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.burtsev.yandexlavka2023.couriers.entity.CourierType;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCourierDto {

    @JsonProperty("courier_type")
    private CourierType courierType;

    @JsonProperty("regions")
    private Set<Integer> regions;

    @JsonProperty("working_hours")
    private Set<String> workingHours;
}
