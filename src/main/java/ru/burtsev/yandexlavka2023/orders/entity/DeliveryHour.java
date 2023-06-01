package ru.burtsev.yandexlavka2023.orders.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "delivery_hours")
public class DeliveryHour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "start_time")
    @DateTimeFormat
    private LocalTime startTime;

    @Column(name = "end_time")
    @DateTimeFormat
    private LocalTime endTime;

    @ManyToMany(
            mappedBy = "deliveryHours",
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE})
    @JsonIgnore
    private Set<Order> orders = new HashSet<>();

    @JsonIgnore
    @Column(name = "start_time_end_time")
    private String startTimeEndTime;

    @Override
    public String toString() {
        return startTime + "-" + endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeliveryHour that = (DeliveryHour) o;
        return Objects.equals(id, that.id) && Objects.equals(startTime, that.startTime) && Objects.equals(endTime, that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startTime, endTime);
    }
}
