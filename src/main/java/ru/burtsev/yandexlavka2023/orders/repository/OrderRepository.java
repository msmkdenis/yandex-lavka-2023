package ru.burtsev.yandexlavka2023.orders.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.burtsev.yandexlavka2023.orders.entity.Order;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @EntityGraph(attributePaths = {"regions", "deliveryHours"})
    Optional<Order> findById(Long orderId);

    @EntityGraph(attributePaths = {"regions", "deliveryHours"})
    Page<Order> findAll(Pageable pageable);
}