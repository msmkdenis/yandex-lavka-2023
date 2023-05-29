package ru.burtsev.yandexlavka2023.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.burtsev.yandexlavka2023.entity.Courier;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourierRepository extends JpaRepository<Courier, Long> {


    @EntityGraph(attributePaths = {"regions", "workingHours"})
    Page<Courier> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"regions", "workingHours"})
    Optional<Courier> findFullEntityById(Long courierId);
}
