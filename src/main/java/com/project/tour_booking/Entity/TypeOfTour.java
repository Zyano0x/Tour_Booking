package com.project.tour_booking.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "types_of_tours")
@Setter
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
public class TypeOfTour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @NonNull
    @NotBlank(message = "Tên loại tour không được để trống")
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "Mô tả loại tour không được để trống")
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull(message = "Trạng thái không được để trống!")
    @Column(name = "status", nullable = false)
    private Boolean status;

    @JsonIgnore
    @OneToMany(mappedBy = "typeOfTour", cascade = CascadeType.ALL)
    private List<Tour> tours;
}
