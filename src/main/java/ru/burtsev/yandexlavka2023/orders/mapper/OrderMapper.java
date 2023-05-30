package ru.burtsev.yandexlavka2023.orders.mapper;

import lombok.experimental.UtilityClass;
import ru.burtsev.yandexlavka2023.couriers.entity.Region;
import ru.burtsev.yandexlavka2023.orders.dto.CreateOrderDto;
import ru.burtsev.yandexlavka2023.orders.dto.OrderDto;
import ru.burtsev.yandexlavka2023.orders.entity.DeliveryHour;
import ru.burtsev.yandexlavka2023.orders.entity.Order;

import java.time.LocalTime;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class OrderMapper {


    public static Order toOrder(CreateOrderDto orderDto,
                                Region region,
                                Set<DeliveryHour> deliveryHours) {
        return Order.builder()
                .weight(orderDto.getWeight())
                .regions(region)
                .cost(orderDto.getCost())
                .deliveryHours(deliveryHours)
                .build();
    }

    public static Set<DeliveryHour> toDeliveryHours(CreateOrderDto createOrderDto) {

        Set<String> deliveryHoursString = createOrderDto.getDeliveryHours();

        return deliveryHoursString.stream()
                .map(workingHour -> {
                    String[] split = workingHour.split("-");
                    return DeliveryHour.builder()
                            .startTime(LocalTime.parse(split[0]))
                            .endTime(LocalTime.parse(split[1]))
                            .startTimeEndTime(workingHour)
                            .build();
                })
                .collect(Collectors.toSet());
    }

    public static OrderDto toOrderDto(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .weight(order.getWeight())
                .regions(order.getRegions().getRegionId())
                .deliveryHours(order.getDeliveryHours().stream().map(DeliveryHour::toString).collect(Collectors.toSet()))
                .cost(order.getCost())
                .completedTime(order.getCompletedTime() == null ? null : order.getCompletedTime().toString())
                .build();
    }
}
