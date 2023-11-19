package com.project.tour_booking.Service.Post;

import java.util.List;

import com.project.tour_booking.DTO.ArticlesDTO;
import com.project.tour_booking.Entity.Articles;

public interface ArticlesService {
  void saveArticles(ArticlesDTO articlesDTO);

  Articles getArticles(Long articlesId);

  List<Articles> getAllArticles();

  Articles updateArticles(ArticlesDTO articlesDTO, Long articlesId);

  void updateArticlesStatus(Long articlesId);
}
