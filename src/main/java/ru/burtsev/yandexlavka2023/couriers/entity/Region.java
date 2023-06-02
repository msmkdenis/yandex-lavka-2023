package ru.burtsev.yandexlavka2023.couriers.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import ru.burtsev.yandexlavka2023.orders.entity.Order;

import java.util.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "region")
public class Region {

    public Region(Integer regionId) {
        this.regionId = regionId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "region_id")
    private Integer regionId;

    @OneToMany(mappedBy = "regions")
    @Builder.Default
    private List<Order> orders = new ArrayList<>();

    public void addOrder(Order order) {
        this.orders.add(order);
        order.setRegions(this);
    }

    @ManyToMany(mappedBy = "regions",
            cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE})
    @JsonIgnore
    @Builder.Default
    private Set<Courier> couriers = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Region region = (Region) o;
        return Objects.equals(id, region.id) && Objects.equals(regionId, region.regionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, regionId);
    }
}
