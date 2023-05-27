package ru.burtsev.yandexlavka2023.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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

    @ManyToMany(mappedBy = "regions",
            cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE})
    @JsonIgnore
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
