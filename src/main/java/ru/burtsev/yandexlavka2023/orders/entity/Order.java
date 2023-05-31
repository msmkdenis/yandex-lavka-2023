package ru.burtsev.yandexlavka2023.orders.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.burtsev.yandexlavka2023.couriers.entity.Courier;
import ru.burtsev.yandexlavka2023.couriers.entity.Region;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "weight")
    private Float weight;

    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region regions;

    @ManyToOne
    @JoinColumn(name = "courier_complete_id")
    private Courier completedCouriers;

    @Column(name = "cost")
    private Integer cost;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE})
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "order_delivery_hours",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "delivery_hours_id"))
    @JsonIgnore
    private Set<DeliveryHour> deliveryHours = new HashSet<>();

    public void addDeliverHour(DeliveryHour deliveryHour) {
        this.deliveryHours.add(deliveryHour);
        deliveryHour.getOrders().add(this);
    }

    public void removeWorkingHour(long deliveryHourId) {
        DeliveryHour deliveryHour = this.deliveryHours
                .stream()
                .filter(t -> t.getId() == deliveryHourId)
                .findFirst().orElse(null);
        if (deliveryHour != null) {
            this.deliveryHours.remove(deliveryHour);
            deliveryHour.getOrders().remove(this);
        }
    }

    @Column(name = "completed_time")
    private OffsetDateTime completedTime;
}
