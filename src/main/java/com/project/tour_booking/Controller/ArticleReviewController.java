package com.project.tour_booking.Controller;

import com.project.tour_booking.DTO.ArticleReviewDTO;
import com.project.tour_booking.Entity.ArticleReview;
import com.project.tour_booking.Service.ArticleReview.ArticleReviewService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ArticleReviewController {
    private final ArticleReviewService articleReviewService;

    @PostMapping("/article-reviews")
    public ResponseEntity<String> saveArticleReview(@Valid @RequestBody ArticleReviewDTO articleReviewDTO) {
        articleReviewService.saveArticleReview(articleReviewDTO);
        return new ResponseEntity<>("THÊM BÌNH LUẬN THÀNH CÔNG!", HttpStatus.CREATED);
    }

    @GetMapping("/article-reviews/articles/{articleId}")
    public ResponseEntity<List<ArticleReview>> getAllArticleReviewByArticleId(@PathVariable Long articleId) {
        return new ResponseEntity<>(articleReviewService.getAllArticleReviewByArticleId(articleId), HttpStatus.OK);
    }

    @PutMapping("/update-article-reviews/{articleReviewId}")
    public ResponseEntity<ArticleReview> updateArticleReview(@Valid @RequestBody ArticleReviewDTO articleReviewDTO,
            @PathVariable Long articleReviewId) {
        return new ResponseEntity<>(articleReviewService.updateArticleReview(articleReviewDTO, articleReviewId),
                HttpStatus.OK);
    }

    @DeleteMapping("/delete-article-reviews/{articleReviewId}")
    public ResponseEntity<String> deleteArticleReview(@PathVariable Long articleReviewId) {
        articleReviewService.deleteArticleReview(articleReviewId);
        return new ResponseEntity<>(" ", HttpStatus.NO_CONTENT);
    }
}
