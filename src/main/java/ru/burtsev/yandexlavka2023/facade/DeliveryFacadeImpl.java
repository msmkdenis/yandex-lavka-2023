package ru.burtsev.yandexlavka2023.facade;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.burtsev.yandexlavka2023.util.Constants;
import ru.burtsev.yandexlavka2023.configuration.CourierCoefficient;
import ru.burtsev.yandexlavka2023.couriers.dto.*;
import ru.burtsev.yandexlavka2023.couriers.entity.Courier;
import ru.burtsev.yandexlavka2023.couriers.mapper.GetCourierMetaInfoResponseMapper;
import ru.burtsev.yandexlavka2023.couriers.repository.CourierFullInfo;
import ru.burtsev.yandexlavka2023.couriers.service.CourierService;
import ru.burtsev.yandexlavka2023.exception.BadRequest;
import ru.burtsev.yandexlavka2023.orders.dto.*;
import ru.burtsev.yandexlavka2023.orders.entity.Order;
import ru.burtsev.yandexlavka2023.orders.mapper.OrderMapper;
import ru.burtsev.yandexlavka2023.orders.service.OrderService;

import java.time.Duration;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeliveryFacadeImpl implements DeliveryFacade{

    private final CourierService courierService;
    private final OrderService orderService;
    private final CourierCoefficient courierCoefficient;

    @Override
    public CreateCouriersResponse saveCouriers(CreateCourierRequest courierRequest) {
        return courierService.saveCouriers(courierRequest);
    }

    @Override
    public CourierDto getCourierById(Long courierId) {
        return courierService.findCourierById(courierId);
    }

    @Override
    public GetCouriersResponse getCouriers(Integer offset, Integer limit) {
        return courierService.findCouriers(offset, limit);
    }

    @Override
    public void deleteCourierById(Long courierId) {
        courierService.deleteCourierById(courierId);
    }

    @Override
    @Transactional
    public GetCourierMetaInfoResponse getCourierMetaInfo(Long courierId, String startDate, String endDate) {
        OffsetDateTime startOffset = parseOffsetDateTime(startDate);
        OffsetDateTime endOffset = parseOffsetDateTime(endDate);

        CourierFullInfo courierFullInfo = courierService.findCourierShortInfo(courierId);
        Integer courierEarnings = null;
        Long courierRating = null;

        if (!courierFullInfo.getCompletedOrders().isEmpty()) {
            List<Order> completedOrders =
                    orderService.findCompletedOrdersByCourierBetweenDates(courierId, startOffset, endOffset);
            courierEarnings = calcCourierEarnings(completedOrders, courierFullInfo);
            courierRating = calcCourierRating(completedOrders.size(), courierFullInfo, startOffset, endOffset);
        }

        return GetCourierMetaInfoResponseMapper.toCourierMetaInfo(courierFullInfo, courierEarnings, courierRating);
    }

    @Override
    public CreateOrderResponse saveOrders(CreateOrderRequest createOrderRequest) {
        return orderService.saveOrders(createOrderRequest);
    }

    @Override
    public OrderDto getOrderById(Long orderId) {
        return orderService.findOrderById(orderId);
    }

    @Override
    public List<OrderDto> getOrders(Integer offset, Integer limit) {
        return orderService.findOrders(offset, limit);
    }

    @Override
    @Transactional
    public List<OrderDto> saveCompletedOrders(CompleteOrderRequestDto completeOrderRequestDto) {
        List<CompleteOrder> completeOrders = validateCompleteOrderRequestDto(completeOrderRequestDto);

        Set<Long> courierIds = completeOrders.stream().map(CompleteOrder::getCourierId).collect(Collectors.toSet());
        List<Long> oderIds = completeOrders.stream().map(CompleteOrder::getOrderId).collect(Collectors.toList());

        Map<Long, Courier> couriers = courierService.findCouriersOrThrow(courierIds)
                .stream().collect(Collectors.toMap(Courier::getId, item -> item));

        Map<Long, Order> orders = orderService.findOrdersOrThrow(oderIds)
                .stream().collect(Collectors.toMap(Order::getId, item -> item));

        for (CompleteOrder completeOrder : completeOrders) {
            Courier courier = couriers.get(completeOrder.getCourierId());
            Order order = orders.get(completeOrder.getOrderId());

            order.setCompletedTime(OffsetDateTime.parse(completeOrder.getCompleteTime(),
                    Constants.offsetFormatter.ISO_OFFSET_DATE_TIME));
            courier.getCompletedOrders().add(order);
            order.setCompletedCouriers(courier);
        }

        return orders.values().stream()
                .map(OrderMapper::toOrderDto)
                .collect(Collectors.toList());
    }

    private OffsetDateTime parseOffsetDateTime(String date){
        return OffsetDateTime.from(LocalDate.parse(date, Constants.localDateFormatter)
                .atStartOfDay(ZoneOffset.UTC)
                .toOffsetDateTime());
    }

    private Integer calcCourierEarnings(List<Order> completedOrders, CourierFullInfo courierFullInfo) {
        Map<String, Integer> paymentCoefficient = courierCoefficient.getPaymentCoefficient();

        return completedOrders
                .stream()
                .mapToInt(Order::getCost).sum() *
                paymentCoefficient.get(String.valueOf(courierFullInfo.getCourierType()));
    }

    private Long calcCourierRating(Integer completedOrders,
                                   CourierFullInfo courierFullInfo,
                                   OffsetDateTime startOffset,
                                   OffsetDateTime endOffset)
    {
        Duration duration = Duration.between(startOffset, endOffset);
        Map<String, Integer> ratingCoefficient = courierCoefficient.getRatingCoefficient();

        return (long) (((double) completedOrders / (double) duration.toHours())
                * (double) ratingCoefficient.get(String.valueOf(courierFullInfo.getCourierType())));
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
}
