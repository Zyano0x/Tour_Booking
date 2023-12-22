package com.project.tour_booking.Exception;

public class ArticleNotFoundException extends RuntimeException {
    public ArticleNotFoundException(Long id) {
        super("Bài viết có mã '" + id + "' không tồn tại!");
    }
}
