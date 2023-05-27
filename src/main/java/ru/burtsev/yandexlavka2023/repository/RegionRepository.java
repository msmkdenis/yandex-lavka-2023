package ru.burtsev.yandexlavka2023.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.burtsev.yandexlavka2023.entity.Region;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {

    Optional<Region> findRegionByRegionId(Integer regionId);

    Set<Region> findAllByRegionIdIsIn(List<Integer> regionsId);
}
