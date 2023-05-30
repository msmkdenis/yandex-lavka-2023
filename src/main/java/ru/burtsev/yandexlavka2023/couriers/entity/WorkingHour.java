package ru.burtsev.yandexlavka2023.couriers.entity;

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
@Table(name = "working_hours")
public class WorkingHour {

    public WorkingHour(LocalTime startTime, LocalTime endTime, String startTimeEndTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.startTimeEndTime = startTimeEndTime;
    }

    @Override
    public String toString() {
        return startTime + "-" + endTime;
    }

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
            mappedBy = "workingHours",
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE})
    @JsonIgnore
    private Set<Courier> couriers = new HashSet<>();

    @JsonIgnore
    @Column(name = "start_time_end_time")
    private String startTimeEndTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkingHour that = (WorkingHour) o;
        return Objects.equals(id, that.id) && Objects.equals(startTime, that.startTime) && Objects.equals(endTime, that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startTime, endTime);
    }
}
