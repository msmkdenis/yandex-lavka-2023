package ru.burtsev.yandexlavka2023.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.burtsev.yandexlavka2023.dto.CourierDto;
import ru.burtsev.yandexlavka2023.dto.CreateCourierDto;
import ru.burtsev.yandexlavka2023.dto.CreateCourierRequest;
import ru.burtsev.yandexlavka2023.dto.CreateCouriersResponse;
import ru.burtsev.yandexlavka2023.entity.Courier;
import ru.burtsev.yandexlavka2023.entity.Region;
import ru.burtsev.yandexlavka2023.entity.WorkingHour;
import ru.burtsev.yandexlavka2023.mapper.CourierMapper;
import ru.burtsev.yandexlavka2023.repository.CourierRepository;
import ru.burtsev.yandexlavka2023.repository.RegionRepository;
import ru.burtsev.yandexlavka2023.repository.WorkingHourRepository;

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

        List<CreateCourierDto> createCourierDtos = courierRequest.getCouriers();
        List<Courier> savedCouriers = new ArrayList<>();

        for (CreateCourierDto dto: createCourierDtos) {

            Set<WorkingHour> workingHours = CourierMapper.toWorkingHours(dto);

            for (WorkingHour hour: workingHours) {
                Optional<WorkingHour> workingHourEntity = workingHourRepository
                        .findWorkingHourByStartTimeAndEndTime(hour.getStartTime(), hour.getEndTime());
                if (workingHourEntity.isEmpty()) {
                    workingHourRepository.save(hour);
                }
            }

            Set<Region> regions = CourierMapper.toRegions(dto);
            for (Region region: regions) {
                Optional<Region> regionEntity = regionRepository.findRegionByRegionId(region.getRegionId());
                if (regionEntity.isEmpty()) {
                    regionRepository.save(region);
                }
            }

            List<Integer> regionIds = regions.stream().map(Region::getRegionId).collect(Collectors.toList());
            List<String> startTimeEndTime = workingHours.stream().map(WorkingHour::getStartTimeEndTime).collect(Collectors.toList());

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
    public CourierDto getCourierById(Long courierId) {
        Optional<Courier> courier = courierRepository.findById(courierId);
        if (courier.isPresent()) {
            return CourierMapper.toCourierDto(courier.get());
        }
        return null;
    }
}