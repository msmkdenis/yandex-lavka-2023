package ru.burtsev.yandexlavka2023.orders.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderRequest {

    @NotEmpty
    @JsonProperty("orders")
    private List<CreateOrderDto> orders;
}
