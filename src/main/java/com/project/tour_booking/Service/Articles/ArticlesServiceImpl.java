package com.project.tour_booking.Service.Articles;

import com.project.tour_booking.DTO.ArticlesDTO;
import com.project.tour_booking.Entity.Articles;
import com.project.tour_booking.Entity.Role;
import com.project.tour_booking.Entity.User;
import com.project.tour_booking.Exception.UserNotFoundException;
import com.project.tour_booking.Repository.ArticlesRepository;
import com.project.tour_booking.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticlesServiceImpl implements ArticlesService {
    private final ArticlesRepository articlesRepository;
    private final UserRepository userRepository;

    @Override
    public Articles saveArticles(ArticlesDTO articlesDTO) {
        Optional<User> user = userRepository.findById(articlesDTO.getUserId());

        if (user.isPresent()) {
            Articles post = new Articles();
            post.setTitle(articlesDTO.getTitle());
            post.setDescription(articlesDTO.getDescription());
            post.setThumbnail(articlesDTO.getThumbnail());
            post.setContent(articlesDTO.getContent());
            post.setDateOfPosting(LocalDate.now());
            post.setStatus(user.get().getRole() == Role.ADMIN);
            post.setUser(user.get());

            articlesRepository.save(post);
            return post;
        } else {
            throw new UserNotFoundException(articlesDTO.getUserId());
        }
    }

    @Override
    public Articles getArticles(Long articlesId) {
        return articlesRepository.findById(articlesId)
                .orElseThrow(() -> new EntityNotFoundException("Articles not found with id: " + articlesId));
    }

    @Override
    public List<Articles> getAllArticles() {
        return articlesRepository.findAll();
    }

    @Override
    public Articles updateArticles(Articles articles, Long articlesId) {
        Articles updateArticles = articlesRepository.findById(articlesId)
                .orElseThrow(() -> new EntityNotFoundException("Articles not found with id: " + articlesId));

        updateArticles.setTitle(articles.getTitle());
        updateArticles.setDescription(articles.getDescription());
        updateArticles.setThumbnail(articles.getThumbnail());
        updateArticles.setContent(articles.getContent());
        updateArticles.setStatus(articles.getStatus());
        updateArticles.setEditDate(LocalDate.now());

        return articlesRepository.save(updateArticles);
    }

    @Override
    public void updateArticlesStatus(Long articlesId) {
        Articles articles = articlesRepository.findById(articlesId)
                .orElseThrow(() -> new EntityNotFoundException("Articles not found with id: " + articlesId));

        articles.setStatus(!articles.getStatus());

        articlesRepository.save(articles);
    }
}
