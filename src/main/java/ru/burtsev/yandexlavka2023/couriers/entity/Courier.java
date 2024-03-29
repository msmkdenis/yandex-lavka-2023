package ru.burtsev.yandexlavka2023.couriers.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.burtsev.yandexlavka2023.orders.entity.Order;

import java.util.*;

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

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE})
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "courier_region",
            joinColumns = @JoinColumn(name = "courier_id"),
            inverseJoinColumns = @JoinColumn(name = "region_id"))
    @JsonIgnore
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

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE})
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "courier_working_hours",
            joinColumns = @JoinColumn(name = "courier_id"),
            inverseJoinColumns = @JoinColumn(name = "working_hours_id"))
    @JsonIgnore
    @Builder.Default
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

    @OneToMany(mappedBy = "completedCouriers")
    @JsonIgnore
    @Builder.Default
    private List<Order> completedOrders = new ArrayList<>();

    public void addCompletedOrder(Order order) {
        this.completedOrders.add(order);
        order.setCompletedCouriers(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Courier courier = (Courier) o;
        return Objects.equals(id, courier.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
