package com.project.tour_booking.Service.ArticleReview;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.tour_booking.DTO.ArticleReviewDTO;
import com.project.tour_booking.Entity.Articles;
import com.project.tour_booking.Entity.ArticleReview;
import com.project.tour_booking.Entity.User;
import com.project.tour_booking.Exception.ArticleNotFoundException;
import com.project.tour_booking.Exception.ArticleReviewNotFoundException;
import com.project.tour_booking.Exception.UserNotFoundException;
import com.project.tour_booking.Repository.ArticlesRepository;
import com.project.tour_booking.Repository.ArticleReviewRepository;
import com.project.tour_booking.Repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ArticleReviewServiceImpl implements ArticleReviewService {
  private ArticleReviewRepository articleReviewRepository;
  private ArticlesRepository articlesRepository;
  private UserRepository userRepository;

  @Override
  public void saveArticleReview(ArticleReviewDTO articleReviewDTO) {
    ArticleReview articleReview = new ArticleReview();
    articleReview.setContent(articleReviewDTO.getContent());
    articleReview.setVote(articleReviewDTO.getVote());

    Optional<User> userOptional = userRepository.findById(articleReviewDTO.getUserId());
    if (userOptional.isPresent())
      articleReview.setUser(userOptional.get());
    else
      throw new UserNotFoundException(articleReviewDTO.getUserId());

    Optional<Articles> articleOptional = articlesRepository.findById(articleReviewDTO.getArticleId());
    if (articleOptional.isPresent())
      articleReview.setArticle(articleOptional.get());
    else
      throw new ArticleNotFoundException(articleReviewDTO.getArticleId());

    articleReview.setDateOfPosting(LocalDate.now());
    articleReviewRepository.save(articleReview);
  }

  @Override
  public ArticleReview getArticleReview(Long articleReviewId) {
    Optional<ArticleReview> articleReviewOptional = articleReviewRepository.findById(articleReviewId);
    if (articleReviewOptional.isPresent())
      return articleReviewOptional.get();
    else
      throw new ArticleReviewNotFoundException(articleReviewId);
  }

  @Override
  public ArticleReview getArticleReviewByArticleIdAndUserId(Long articleId, Long userId) {
    Optional<ArticleReview> articleReviewOptional = articleReviewRepository.findByArticleIdAndUserId(articleId, userId);
    if (articleReviewOptional.isPresent())
      return articleReviewOptional.get();
    else
      throw new ArticleReviewNotFoundException(articleId, userId);
  }

  @Override
  public List<ArticleReview> getAllArticleReviewByArticleId(Long articleId) {
    Optional<Articles> articleOptional = articlesRepository.findById(articleId);
    if (articleOptional.isPresent())
      return articleReviewRepository.findAllByArticleId(articleId);
    else
      throw new ArticleNotFoundException(articleId);
  }

  @Override
  public List<ArticleReview> getAllArticleReviewByUserId(Long userId) {
    Optional<User> userOptional = userRepository.findById(userId);
    if (userOptional.isPresent())
      return articleReviewRepository.findAllByUserId(userId);
    else
      throw new UserNotFoundException(userId);
  }

  @Override
  public ArticleReview updateArticleReview(ArticleReviewDTO articleReviewDTO, Long articleReviewId) {
    Optional<ArticleReview> articleReviewOptional = articleReviewRepository.findById(articleReviewId);
    if (articleReviewOptional.isPresent()) {
      ArticleReview updateArticleReview = articleReviewOptional.get();
      updateArticleReview.setContent(articleReviewDTO.getContent());
      updateArticleReview.setVote(articleReviewDTO.getVote());
      updateArticleReview.setEditDate(LocalDate.now());
      return articleReviewRepository.save(updateArticleReview);
    } else
      throw new ArticleReviewNotFoundException(articleReviewId);
  }

  @Override
  public void deleteArticleReview(Long articleReviewId) {
    Optional<ArticleReview> articleReviewOptional = articleReviewRepository.findById(articleReviewId);
    if (articleReviewOptional.isPresent())
      articleReviewRepository.deleteById(articleReviewId);
    else
      throw new ArticleReviewNotFoundException(articleReviewId);
  }
}
