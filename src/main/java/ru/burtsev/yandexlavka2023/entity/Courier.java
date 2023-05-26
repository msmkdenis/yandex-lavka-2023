package ru.burtsev.yandexlavka2023.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.burtsev.yandexlavka2023.dto.CourierType;

import java.util.Objects;
import java.util.Set;

import static jakarta.persistence.CascadeType.ALL;

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

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "courier_region",
            joinColumns = @JoinColumn(name = "courier_id"),
            inverseJoinColumns = @JoinColumn(name = "region_id"))
    private Set<Region> regions;

    @ManyToMany(cascade = ALL)
    @JoinTable(name = "courier_working_hours",
            joinColumns = @JoinColumn(name = "courier_id"),
            inverseJoinColumns = @JoinColumn(name = "working_hours_id"))
    private Set<WorkingHours> workingHours;

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
