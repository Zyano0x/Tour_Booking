package com.project.tour_booking.Service.ArticleReview;

import java.util.List;

import com.project.tour_booking.DTO.ArticleReviewDTO;
import com.project.tour_booking.Entity.ArticleReview;

public interface ArticleReviewService {
  void saveArticleReview(ArticleReviewDTO articleReviewDTO);

  ArticleReview getArticleReview(Long articleReviewId);

  ArticleReview getArticleReviewByArticleIdAndUserId(Long articleId, Long userId);

  List<ArticleReview> getAllArticleReviewByArticleId(Long articleId);

  // String getRatingByArticleId(Long articleId);

  List<ArticleReview> getAllArticleReviewByUserId(Long userId);

  ArticleReview updateArticleReview(ArticleReviewDTO articleReviewDTO, Long articleReviewId);

  void deleteArticleReview(Long articleReviewId);
}
