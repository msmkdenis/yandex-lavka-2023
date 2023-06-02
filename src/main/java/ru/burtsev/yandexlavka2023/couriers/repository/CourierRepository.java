package ru.burtsev.yandexlavka2023.couriers.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.burtsev.yandexlavka2023.couriers.entity.Courier;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourierRepository extends JpaRepository<Courier, Long> {


    @EntityGraph(attributePaths = {"regions", "workingHours"})
    Page<Courier> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"regions", "workingHours"})
    Optional<Courier> findById(Long courierId);

    //@EntityGraph(attributePaths = {"regions", "workingHours"})
    List<Courier> findAllById(Iterable<Long> courierIds);

    @Query("select c from Courier c where c.id=?1")
    @EntityGraph(attributePaths = {"regions", "workingHours"})
    Optional<CourierFullInfo> findCourierShortInfo(Long courierId);
}
