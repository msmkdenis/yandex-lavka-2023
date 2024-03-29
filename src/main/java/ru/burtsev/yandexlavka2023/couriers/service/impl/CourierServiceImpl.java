package ru.burtsev.yandexlavka2023.couriers.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.burtsev.yandexlavka2023.couriers.dto.*;
import ru.burtsev.yandexlavka2023.couriers.entity.Courier;
import ru.burtsev.yandexlavka2023.couriers.entity.Region;
import ru.burtsev.yandexlavka2023.couriers.entity.WorkingHour;
import ru.burtsev.yandexlavka2023.couriers.mapper.CourierMapper;
import ru.burtsev.yandexlavka2023.couriers.repository.CourierFullInfo;
import ru.burtsev.yandexlavka2023.couriers.repository.CourierRepository;
import ru.burtsev.yandexlavka2023.couriers.repository.RegionRepository;
import ru.burtsev.yandexlavka2023.couriers.repository.WorkingHourRepository;
import ru.burtsev.yandexlavka2023.couriers.service.CourierService;
import ru.burtsev.yandexlavka2023.couriers.service.CouriersRequest;
import ru.burtsev.yandexlavka2023.exception.BadRequest;
import ru.burtsev.yandexlavka2023.exception.NotFound;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourierServiceImpl implements CourierService {

    private final CourierRepository courierRepository;
    private final WorkingHourRepository workingHourRepository;
    private final RegionRepository regionRepository;

    @Override
    @Transactional
    public CreateCouriersResponse saveCouriers(CreateCourierRequest courierRequest) {

        List<CreateCourierDto> createCourierDtos = validateCreateCourierRequest(courierRequest);

        List<Courier> savedCouriers = new ArrayList<>();

        for (CreateCourierDto dto : createCourierDtos) {

            Set<WorkingHour> workingHours = CourierMapper.toWorkingHours(dto);
            Set<Region> regions = CourierMapper.toRegions(dto);

            for (WorkingHour hour : workingHours) {
                Optional<WorkingHour> workingHourEntity = workingHourRepository
                        .findWorkingHourByStartTimeAndEndTime(hour.getStartTime(), hour.getEndTime());
                if (workingHourEntity.isEmpty()) {
                    workingHourRepository.save(hour);
                }
            }

            for (Region region : regions) {
                Optional<Region> regionEntity = regionRepository.findRegionByRegionId(region.getRegionId());
                if (regionEntity.isEmpty()) {
                    regionRepository.save(region);
                }
            }

            List<Integer> regionIds = regions
                    .stream()
                    .map(Region::getRegionId)
                    .collect(Collectors.toList());

            List<String> startTimeEndTime = workingHours
                    .stream()
                    .map(WorkingHour::getStartTimeEndTime)
                    .collect(Collectors.toList());

            Set<Region> regionsEntity = regionRepository.findAllByRegionIdIsIn(regionIds);
            Set<WorkingHour> workingHoursEntity = workingHourRepository.findAllByStartTimeEndTimeIn(startTimeEndTime);

            Courier courier = CourierMapper.toCourier(dto, regionsEntity, workingHoursEntity);
            savedCouriers.add(courier);
            courierRepository.save(courier);
        }

        List<CourierDto> courierDtos = savedCouriers
                .stream()
                .map(CourierMapper::toCourierDto)
                .collect(Collectors.toList());

        return new CreateCouriersResponse(courierDtos);
    }

    @Override
    public CourierDto findCourierById(Long courierId) {
        Courier courier = findCourierOtThrow(courierId);

        return CourierMapper.toCourierDto(courier);
    }

    @Override
    public GetCouriersResponse findCouriers(Integer offset, Integer limit) {
        if (offset == limit) {
            throw new BadRequest(String.format("Параметры offset=%d и limit=%d не должны быть равны", offset, limit));
        }

        List<Courier> couriers = courierRepository.findAll(new CouriersRequest(offset, limit, Sort.unsorted()))
                .stream().collect(Collectors.toList());

        return GetCouriersResponse.builder()
                .couriers(couriers
                        .stream()
                        .map(CourierMapper::toCourierDto)
                        .collect(Collectors.toList()))
                .offset(offset)
                .limit(limit)
                .build();
    }

    @Override
    public void deleteCourierById(Long courierId) {
        courierRepository.deleteById(courierId);
    }

    @Override
    public List<Courier> findCouriersOrThrow(Set<Long> courierIds) {
        List<Courier> couriers = courierRepository.findAllById(courierIds);
        if (couriers.size() != courierIds.size()) {
            throw new BadRequest("Отсутствует courier в базе данных");
        } else {
            return couriers;
        }
    }

    @Override
    public CourierFullInfo findCourierShortInfo(Long courierId) {
        return courierRepository.findCourierShortInfo(courierId)
                .orElseThrow(() -> new NotFound(String.format("Courier с id=%d не найден!", courierId)));
    }

    private List<CreateCourierDto> validateCreateCourierRequest(CreateCourierRequest courierRequest) {
        List<CreateCourierDto> createCourierDtos = courierRequest.getCouriers();

        for (CreateCourierDto dto : createCourierDtos) {
            if (dto.getCourierType() == null) {
                throw new BadRequest("Тип курьера не может быть null");
            } else if (dto.getRegions() == null) {
                throw new BadRequest("Регионы работы курьера не могут быть null");
            } else if (dto.getWorkingHours() == null) {
                throw new BadRequest("Часы работы курьера не могут быть null");
            }
        }
        return createCourierDtos;
    }

    private Courier findCourierOtThrow(Long id) {
        return courierRepository.findById(id)
                .orElseThrow(() -> new NotFound(String.format("Courier с id=%d не найден!", id)));
    }
}
