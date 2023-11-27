package com.project.tour_booking.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.tour_booking.DTO.ArticlesDTO;
import com.project.tour_booking.Entity.Articles;
import com.project.tour_booking.Service.Articles.ArticlesService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

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

  @GetMapping("/articles/{articlesId}")
  public ResponseEntity<Articles> getTour(@PathVariable Long articlesId) {
    return new ResponseEntity<>(articlesService.getArticles(articlesId), HttpStatus.OK);
  }

  @GetMapping("/articles/all")
  public ResponseEntity<List<Articles>> getAllArticles() {
    return new ResponseEntity<>(articlesService.getAllArticles(), HttpStatus.OK);
  }

  @PutMapping("/admin/update-articles/{articlesId}")
  public ResponseEntity<Articles> updateArticles(@Valid @RequestBody Articles articles,
      @PathVariable Long articlesId) {
    return new ResponseEntity<>(articlesService.updateArticles(articles, articlesId), HttpStatus.OK);
  }

  @PutMapping("/admin/update-articles-status/{articlesId}")
  public ResponseEntity<String> updateArticlesStatus(@PathVariable Long articlesId) {
    articlesService.updateArticlesStatus(articlesId);
    return new ResponseEntity<String>("Updated articles status", HttpStatus.OK);
  }
}
