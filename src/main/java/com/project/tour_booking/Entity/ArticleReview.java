package com.project.tour_booking.Entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "article_review", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "article_id", "user_id" })
})
@Setter
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
public class ArticleReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NonNull
    @NotBlank(message = "Nội dung đánh giá không thể để trống!")
    @Column(name = "content", columnDefinition = "LONGTEXT")
    private String content;

    @NonNull
    @NotNull(message = "Vote không được để trống!")
    @Column(name = "vote")
    private Integer vote;

    @Column(name = "date_of_posting")
    private LocalDate dateOfPosting;

    @Column(name = "edit_date")
    private LocalDate editDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "article_id", referencedColumnName = "id")
    private Articles article;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
