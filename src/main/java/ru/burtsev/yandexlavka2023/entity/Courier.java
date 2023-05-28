package ru.burtsev.yandexlavka2023.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.burtsev.yandexlavka2023.dto.CourierType;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "couriers")
public class Courier {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "courier_type")
    private CourierType courierType;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE})
    @JoinTable(name = "courier_region",
            joinColumns = @JoinColumn(name = "courier_id"),
            inverseJoinColumns = @JoinColumn(name = "region_id"))
    private Set<Region> regions = new HashSet<>();

    public void addRegion(Region region) {
        this.regions.add(region);
        region.getCouriers().add(this);
    }

    public void removeRegion(long regionId) {
        Region region = this.regions
                .stream()
                .filter(t -> t.getId() == regionId)
                .findFirst().orElse(null);
        if (region != null) {
            this.regions.remove(region);
            region.getCouriers().remove(this);
        }
    }

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE})
    @JoinTable(name = "courier_working_hours",
            joinColumns = @JoinColumn(name = "courier_id"),
            inverseJoinColumns = @JoinColumn(name = "working_hours_id"))
    private Set<WorkingHour> workingHours = new HashSet<>();

    public void addWorkingHour(WorkingHour workingHour) {
        this.workingHours.add(workingHour);
        workingHour.getCouriers().add(this);
    }

    public void removeWorkingHour(long workingHourId) {
        WorkingHour workingHour = this.workingHours
                .stream()
                .filter(t -> t.getId() == workingHourId)
                .findFirst().orElse(null);
        if (workingHour != null) {
            this.workingHours.remove(workingHour);
            workingHour.getCouriers().remove(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Courier courier = (Courier) o;
        return Objects.equals(id, courier.id)
                && courierType == courier.courierType
                && Objects.equals(regions, courier.regions)
                && Objects.equals(workingHours, courier.workingHours);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, courierType, regions, workingHours);
    }
}
