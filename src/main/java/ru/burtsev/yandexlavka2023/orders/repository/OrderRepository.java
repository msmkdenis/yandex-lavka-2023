package ru.burtsev.yandexlavka2023.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.burtsev.yandexlavka2023.orders.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
