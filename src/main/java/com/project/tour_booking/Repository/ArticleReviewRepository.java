package com.project.tour_booking.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.tour_booking.Entity.ArticleReview;

public interface ArticleReviewRepository extends JpaRepository<ArticleReview, Long> {

    Optional<ArticleReview> findByArticleIdAndUserId(Long articleId, Long userid);

    List<ArticleReview> findAllByArticleId(Long articleId);

    List<ArticleReview> findAllByUserId(Long userId);
}