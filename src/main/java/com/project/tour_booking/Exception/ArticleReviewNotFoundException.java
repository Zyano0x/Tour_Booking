package com.project.tour_booking.Exception;

public class ArticleReviewNotFoundException extends RuntimeException {
    public ArticleReviewNotFoundException(Long id) {
        super("Đánh giá bài viết có mã đánh giá '" + id + "' không tồn tại!");
    }

    public ArticleReviewNotFoundException(Long articleId, Long userId) {
        super("Đánh giá của người dùng có mã '" + userId + "' cho bài viết có mã  '" + articleId + "' không tồn tại!");
    }
}