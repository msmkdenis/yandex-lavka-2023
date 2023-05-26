package ru.burtsev.yandexlavka2023.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.burtsev.yandexlavka2023.entity.Courier;

@Repository
public interface CourierRepository extends JpaRepository<Courier, Long> {
}
