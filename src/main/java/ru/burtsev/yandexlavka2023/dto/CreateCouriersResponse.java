package ru.burtsev.yandexlavka2023.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCouriersResponse {

    @JsonProperty("couriers")
    private List<CourierDto> couriers;

}