package com.project.tour_booking.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Data
@Entity
@Builder
@Table(name = "articles")
@AllArgsConstructor
@NoArgsConstructor
public class Articles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @NonNull
    @NotBlank(message = "Tiêu đề không được để trống!")
    @Column(name = "title")
    private String title;

    @NonNull
    @NotBlank(message = "Mô tả không được để trống!")
    @Column(name = "description", columnDefinition = "LONGTEXT")
    private String description;

    @NonNull
    @NotBlank(message = "Thumbnail không được để trống!")
    @Column(name = "thumbnail")
    private String thumbnail;

    @NonNull
    @NotBlank(message = "Nội dung không được để trống!")
    @Column(name = "content", columnDefinition = "LONGTEXT")
    private String content;

    @NonNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_of_posting")
    private LocalDate dateOfPosting;

    @Column(name = "edit_date")
    private LocalDate editDate;

    @NonNull
    @NotNull(message = "Trạng thái không được để trống")
    @Column(name = "status")
    private Boolean status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
