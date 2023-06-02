package ru.burtsev.yandexlavka2023.orders.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {

    @JsonProperty("order_id")
    private Long id;

    @JsonProperty("weight")
    private Float weight;

    @JsonProperty("regions")
    private Integer regions;

    @JsonProperty("delivery_hours")
    private Set<String> deliveryHours;

    @JsonProperty("cost")
    private Integer cost;

    @JsonProperty("completed_time")
    private String completedTime;
}
