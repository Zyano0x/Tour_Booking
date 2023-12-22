package com.project.tour_booking.Controller;

import com.project.tour_booking.DTO.ArticleReviewDTO;
import com.project.tour_booking.Entity.ArticleReview;
import com.project.tour_booking.Service.ArticleReview.ArticleReviewService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ArticleReviewController {
    private ArticleReviewService articleReviewService;

    @PostMapping("/article-review")
    public ResponseEntity<String> saveArticleReview(@Valid @RequestBody ArticleReviewDTO articleReviewDTO) {
        articleReviewService.saveArticleReview(articleReviewDTO);
        return new ResponseEntity<>("THÊM BÌNH LUẬN THÀNH CÔNG!", HttpStatus.CREATED);
    }

    @GetMapping("/admin/article-review/{articleReviewId}")
    public ResponseEntity<ArticleReview> getArticleReview(@PathVariable Long articleReviewId) {
        return new ResponseEntity<>(articleReviewService.getArticleReview(articleReviewId), HttpStatus.OK);
    }

    @GetMapping("/admin/user/{userId}/article/{articleId}/article-review")
    public ResponseEntity<ArticleReview> getArticleReviewByArticleIdAndUserId(@PathVariable Long userId,
            @PathVariable Long articleId) {
        return new ResponseEntity<>(articleReviewService.getArticleReviewByArticleIdAndUserId(articleId, userId),
                HttpStatus.OK);
    }

    @GetMapping("/admin/user/{userId}/article-reviews")
    public ResponseEntity<List<ArticleReview>> getAllArticleReviewByUserId(@PathVariable Long userId) {
        return new ResponseEntity<>(articleReviewService.getAllArticleReviewByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/article/{articleId}/article-reviews")
    public ResponseEntity<List<ArticleReview>> getAllArticleReviewByArticleId(@PathVariable Long articleId) {
        return new ResponseEntity<>(articleReviewService.getAllArticleReviewByArticleId(articleId), HttpStatus.OK);
    }

    @PutMapping("/update-article-review/{articleReviewId}")
    public ResponseEntity<ArticleReview> updateArticleReview(@Valid @RequestBody ArticleReviewDTO articleReviewDTO,
            @PathVariable Long articleReviewId) {
        return new ResponseEntity<>(articleReviewService.updateArticleReview(articleReviewDTO, articleReviewId),
                HttpStatus.OK);
    }

    @DeleteMapping("/delete-article-review/{articleReviewId}")
    public ResponseEntity<String> deleteArticleReview(@PathVariable Long articleReviewId) {
        articleReviewService.deleteArticleReview(articleReviewId);
        return new ResponseEntity<>("XÓA BÌNH LUẬN THÀNH CÔNG", HttpStatus.OK);
    }
}
