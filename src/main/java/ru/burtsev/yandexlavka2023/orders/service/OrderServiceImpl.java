package ru.burtsev.yandexlavka2023.orders.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.burtsev.yandexlavka2023.couriers.entity.Region;
import ru.burtsev.yandexlavka2023.couriers.repository.RegionRepository;
import ru.burtsev.yandexlavka2023.exception.BadRequest;
import ru.burtsev.yandexlavka2023.orders.dto.CreateOrderDto;
import ru.burtsev.yandexlavka2023.orders.dto.CreateOrderRequest;
import ru.burtsev.yandexlavka2023.orders.dto.OrderDto;
import ru.burtsev.yandexlavka2023.orders.entity.DeliveryHour;
import ru.burtsev.yandexlavka2023.orders.entity.Order;
import ru.burtsev.yandexlavka2023.orders.mapper.OrderMapper;
import ru.burtsev.yandexlavka2023.orders.repository.DeliveryHourRepository;
import ru.burtsev.yandexlavka2023.orders.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final DeliveryHourRepository deliveryHourRepository;
    private final RegionRepository regionRepository;

    @Override
    @Transactional
    public List<OrderDto> saveOrders(CreateOrderRequest createOrderRequest) {

        List<CreateOrderDto> createOrderDtos = validateCreateOrderRequest(createOrderRequest);

        List<Order> savedOrder = new ArrayList<>(createOrderDtos.size());

        for (CreateOrderDto dto: createOrderDtos) {
            Set<DeliveryHour> deliveryHours = OrderMapper.toDeliveryHours(dto);

            for (DeliveryHour hour : deliveryHours) {
                Optional<DeliveryHour> deliveryHourEntity = deliveryHourRepository
                        .findWorkingHourByStartTimeAndEndTime(hour.getStartTime(), hour.getEndTime());
                if (deliveryHourEntity.isEmpty()) {
                    deliveryHourRepository.save(hour);
                }
            }

            Region region = Region.builder().regionId(dto.getRegions()).build();

            Optional<Region> regionEntity = regionRepository.findRegionByRegionId(region.getRegionId());
            if (regionEntity.isEmpty()) {
                regionRepository.save(region);
            }

            List<String> startTimeEndTime = deliveryHours.stream().map(DeliveryHour::getStartTimeEndTime).collect(Collectors.toList());

            Region regionEntityToSave = regionRepository.findRegionByRegionId(region.getRegionId()).get();

            Set<DeliveryHour> deliverHoursEntity = deliveryHourRepository.findAllByStartTimeEndTimeIn(startTimeEndTime);

            Order order = OrderMapper.toOrder(dto, regionEntityToSave, deliverHoursEntity);
            savedOrder.add(order);
            orderRepository.save(order);
        }

        return savedOrder
                .stream()
                .map(OrderMapper::toOrderDto)
                .collect(Collectors.toList());
    }

    private List<CreateOrderDto> validateCreateOrderRequest(CreateOrderRequest createOrderRequest) {
        List<CreateOrderDto> createOrderDtos = createOrderRequest.getOrders();

        for (CreateOrderDto dto : createOrderDtos) {
            if (dto.getRegions() == null) {
                throw new BadRequest("Регион доставки не может быть null");
            } else if (dto.getCost() == null) {
                throw new BadRequest("Стоимость доставки не может быть null");
            } else if (dto.getWeight() == null) {
                throw new BadRequest("Вес доставки не может быть null");
            } else if (dto.getDeliveryHours() == null) {
                throw new BadRequest("Время доставки не может быть null");
            }
        }
        return createOrderDtos;
    }
}
