package ru.burtsev.yandexlavka2023.orders.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderResponse {

    @JsonProperty("orders")
    private List<OrderDto> orders;
}
