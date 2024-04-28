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
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ArticlesController {
    private final ArticlesService articlesService;

    @PostMapping("/articles")
    public ResponseEntity<Articles> saveArticles(@Valid @RequestBody ArticlesDTO articlesDTO) {
        return new ResponseEntity<>(articlesService.saveArticles(articlesDTO), HttpStatus.CREATED);
    }

    @GetMapping("/articles/{id}")
    public ResponseEntity<Articles> getTour(@PathVariable Long id) {
        return new ResponseEntity<>(articlesService.getArticles(id), HttpStatus.OK);
    }

    @GetMapping("/articles")
    public ResponseEntity<List<Articles>> getAllArticles() {
        return new ResponseEntity<>(articlesService.getAllArticles(), HttpStatus.OK);
    }
}
