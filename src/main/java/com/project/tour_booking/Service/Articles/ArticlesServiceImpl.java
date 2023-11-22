package com.project.tour_booking.Service.Articles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.tour_booking.DTO.ArticlesDTO;
import com.project.tour_booking.Entity.Articles;
import com.project.tour_booking.Entity.User;
import com.project.tour_booking.Exception.UserNotFoundException;
import com.project.tour_booking.Repository.ArticlesRepository;
import com.project.tour_booking.Repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ArticlesServiceImpl implements ArticlesService {
  private ArticlesRepository articlesRepository;
  private UserRepository userRepository;

  @Override
  public void saveArticles(ArticlesDTO articlesDTO) {
    Articles post = new Articles();
    post.setContent(articlesDTO.getContent());
    post.setTitle(articlesDTO.getTitle());
    post.setDateOfPosting(LocalDate.now());
    if (userRepository.findById(articlesDTO.getUserId()).get().getRole().getName() == "ADMIN") {
      post.setStatus(true);
    } else {
      post.setStatus(false);
    }
    Optional<User> userOptional = userRepository.findById(articlesDTO.getUserId());
    if (userOptional.isPresent())
      post.setUser(userOptional.get());
    else
      throw new UserNotFoundException(articlesDTO.getUserId());
    articlesRepository.save(post);
  }

  @Override
  public Articles getArticles(Long articlesId) {
    Articles articles = articlesRepository.findById(articlesId)
        .orElseThrow(() -> new EntityNotFoundException("Articles not found with id: " + articlesId));

    return articles;
  }

  @Override
  public List<Articles> getAllArticles() {
    return (List<Articles>) articlesRepository.findAll();
  }

  @Override
  public Articles updateArticles(Articles articles, Long articlesId) {
    Articles updateArticles = articlesRepository.findById(articlesId)
        .orElseThrow(() -> new EntityNotFoundException("Articles not found with id: " + articlesId));

    updateArticles.setTitle(articles.getTitle());
    updateArticles.setContent(articles.getContent());
    updateArticles.setStatus(articles.getStatus());
    updateArticles.setEditDate(LocalDate.now());

    return articlesRepository.save(updateArticles);
  }

  @Override
  public void updateArticlesStatus(Long articlesId) {
    Articles articles = articlesRepository.findById(articlesId)
        .orElseThrow(() -> new EntityNotFoundException("Articles not found with id: " + articlesId));

    if (articles.getStatus())
      articles.setStatus(false);
    else
      articles.setStatus(true);

    articlesRepository.save(articles);
  }
}
