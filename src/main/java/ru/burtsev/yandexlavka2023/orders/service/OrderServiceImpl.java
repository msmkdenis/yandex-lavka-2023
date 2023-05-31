package ru.burtsev.yandexlavka2023.orders.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.burtsev.yandexlavka2023.Constants;
import ru.burtsev.yandexlavka2023.couriers.entity.Courier;
import ru.burtsev.yandexlavka2023.couriers.entity.Region;
import ru.burtsev.yandexlavka2023.couriers.repository.CourierRepository;
import ru.burtsev.yandexlavka2023.couriers.repository.RegionRepository;
import ru.burtsev.yandexlavka2023.couriers.service.CouriersRequest;
import ru.burtsev.yandexlavka2023.exception.BadRequest;
import ru.burtsev.yandexlavka2023.exception.NotFound;
import ru.burtsev.yandexlavka2023.orders.dto.*;
import ru.burtsev.yandexlavka2023.orders.entity.DeliveryHour;
import ru.burtsev.yandexlavka2023.orders.entity.Order;
import ru.burtsev.yandexlavka2023.orders.mapper.OrderMapper;
import ru.burtsev.yandexlavka2023.orders.repository.DeliveryHourRepository;
import ru.burtsev.yandexlavka2023.orders.repository.OrderRepository;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final DeliveryHourRepository deliveryHourRepository;
    private final RegionRepository regionRepository;
    private final CourierRepository courierRepository;

    @Override
    @Transactional
    public List<OrderDto> saveOrders(CreateOrderRequest createOrderRequest) {

        List<CreateOrderDto> createOrderDtos = validateCreateOrderRequest(createOrderRequest);

        List<Order> savedOrder = new ArrayList<>(createOrderDtos.size());

        for (CreateOrderDto dto : createOrderDtos) {
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

    @Override
    public OrderDto getOrderById(Long orderId) {
        Order order = findOrderOrThrow(orderId);

        return OrderMapper.toOrderDto(order);
    }

    @Override
    public List<OrderDto> getOrders(Integer offset, Integer limit) {
        if (offset == limit) {
            throw new BadRequest(String.format("Параметры offset=%d и limit=%d не должны быть равны", offset, limit));
        }

        List<Order> orders = orderRepository.findAll(new CouriersRequest(offset, limit, Sort.unsorted()))
                .stream().collect(Collectors.toList());

        return orders.stream()
                .map(OrderMapper::toOrderDto)
                .collect(Collectors.toList());
    }

    private Order findOrderOrThrow(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NotFound(String.format("Order с id=%d не найден!", id)));
    }

    @Override
    @Transactional
    public List<OrderDto> saveCompletedOrders(CompleteOrderRequestDto completeOrderRequestDto) {
        List<CompleteOrder> completeOrders = validateCompleteOrderRequestDto(completeOrderRequestDto);

        List<Long> courierIds = completeOrders.stream().map(CompleteOrder::getCourierId).collect(Collectors.toList());
        List<Long> oderIds = completeOrders.stream().map(CompleteOrder::getOrderId).collect(Collectors.toList());

        Map<Long, Courier> couriers = getCouriersOrThrow(courierIds);
        Map<Long, Order> orders = getOrdersOrThrow(oderIds);

        for (CompleteOrder completeOrder : completeOrders) {
            Courier courier = couriers.get(completeOrder.getCourierId());
            Order order = orders.get(completeOrder.getOrderId());

            order.setCompletedTime(OffsetDateTime.parse(completeOrder.getCompleteTime(),
                    Constants.inputFormatter.ISO_OFFSET_DATE_TIME));
            courier.getCompletedOrders().add(order);
            order.setCompletedCouriers(courier);
        }

        return orders.values().stream()
                .map(OrderMapper::toOrderDto)
                .collect(Collectors.toList());
    }

    private Map<Long, Courier> getCouriersOrThrow(List<Long> courierIds) {
        List<Courier> couriers = courierRepository.findAllById(courierIds);
        if (couriers.size() != courierIds.size()) {
            throw new BadRequest("Отсутствует courier в базе данных");
        } else {
            return couriers.stream()
                    .collect(Collectors.toMap(Courier::getId, item -> item));
        }
    }

    private Map<Long, Order> getOrdersOrThrow(List<Long> orderIds) {
        List<Order> orders = orderRepository.findAllById(orderIds);
        if (orders.size() != orderIds.size()) {
            throw new BadRequest("Отсутствует order в базе данных");
        } else {
            return orders.stream()
                    .collect(Collectors.toMap(Order::getId, item -> item));
        }
    }

    private List<CompleteOrder> validateCompleteOrderRequestDto(CompleteOrderRequestDto completeOrderRequestDto) {
        List<CompleteOrder> completeOrders = completeOrderRequestDto.getCompleteInfo();
        for (CompleteOrder dto : completeOrders) {
            if (dto.getCourierId() == null) {
                throw new BadRequest("Id курьера не может быть null");
            } else if (dto.getOrderId() == null) {
                throw new BadRequest("Id региона не может быть null");
            } else if (dto.getCompleteTime() == null) {
                throw new BadRequest("Complete_time не может быть null");
            }
        }
        return completeOrders;
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
