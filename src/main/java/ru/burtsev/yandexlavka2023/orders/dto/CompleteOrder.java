package ru.burtsev.yandexlavka2023.orders.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.burtsev.yandexlavka2023.Constants;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompleteOrder {

    @JsonProperty("courier_id")
    private Long courierId;

    @JsonProperty("order_id")
    private Long orderId;

    @JsonProperty("complete_time")
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.YYYY_MM_DD_T_HH_MM_SSS_Z)
    private String completeTime;
}
