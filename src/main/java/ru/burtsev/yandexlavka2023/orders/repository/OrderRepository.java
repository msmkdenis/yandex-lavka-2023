package ru.burtsev.yandexlavka2023.orders.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.burtsev.yandexlavka2023.orders.entity.Order;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @EntityGraph(attributePaths = {"regions", "deliveryHours"})
    Optional<Order> findById(Long orderId);

    @EntityGraph(attributePaths = {"regions", "deliveryHours"})
    Page<Order> findAll(Pageable pageable);

    List<Order> findAllById(Iterable<Long> irderIds);

    @EntityGraph(attributePaths = {"regions", "deliveryHours"})
    @Query("select o from Order o where o.completedCouriers.id =?1 and (o.completedTime >= ?2 and o.completedTime < ?3)")
    List<Order> findCompletedOrderByCourierBetweenDates(Long courierId, OffsetDateTime startDate, OffsetDateTime endDate);
}
