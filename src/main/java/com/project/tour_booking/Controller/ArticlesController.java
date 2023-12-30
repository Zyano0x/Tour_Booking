package com.project.tour_booking.Controller;

import com.project.tour_booking.DTO.ArticlesDTO;
import com.project.tour_booking.Entity.Articles;
import com.project.tour_booking.Service.Articles.ArticlesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ArticlesController {

    private final ArticlesService articlesService;

    @PostMapping("/post-articles")
    public ResponseEntity<String> saveArticles(@Valid @RequestBody ArticlesDTO articlesDTO) {
        articlesService.saveArticles(articlesDTO);
        return new ResponseEntity<>("ĐĂNG BÀI THÀNH CÔNG!", HttpStatus.CREATED);
    }

    @GetMapping("/articles")
    public ResponseEntity<Articles> getTour(@RequestParam Long id) {
        return new ResponseEntity<>(articlesService.getArticles(id), HttpStatus.OK);
    }

    @GetMapping("/articles/all")
    public ResponseEntity<List<Articles>> getAllArticles() {
        return new ResponseEntity<>(articlesService.getAllArticles(), HttpStatus.OK);
    }

    @PutMapping("/admin/update-articles")
    public ResponseEntity<Articles> updateArticles(@Valid @RequestBody Articles articles, @RequestParam Long id) {
        return new ResponseEntity<>(articlesService.updateArticles(articles, id), HttpStatus.OK);
    }

    @PutMapping("/admin/update-articles-status")
    public ResponseEntity<String> updateArticlesStatus(@RequestParam Long id) {
        articlesService.updateArticlesStatus(id);
        return new ResponseEntity<String>("Updated articles status", HttpStatus.OK);
    }
}
