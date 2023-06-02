package ru.burtsev.yandexlavka2023.couriers.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.burtsev.yandexlavka2023.couriers.entity.CourierType;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetCourierMetaInfoResponse {

    @JsonProperty("courier_id")
    private Long courierId;

    @JsonProperty("courier_type")
    private CourierType courierType;

    @JsonProperty("regions")
    private List<Integer> regions;

    @JsonProperty("working_hours")
    private List<String> workingHours;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("rating")
    private Long rating;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("earnings")
    private Integer earnings;
}
