package com.project.tour_booking.Service.Articles;

import com.project.tour_booking.DTO.ArticlesDTO;
import com.project.tour_booking.Entity.Articles;
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
    public void saveArticles(ArticlesDTO articlesDTO) {
        Articles post = new Articles();
        post.setContent(articlesDTO.getContent());
        post.setTitle(articlesDTO.getTitle());
        post.setDateOfPosting(LocalDate.now());
        post.setStatus(true);
        Optional<User> userOptional = userRepository.findById(articlesDTO.getUserId());
        if (userOptional.isPresent())
            post.setUser(userOptional.get());
        else
            throw new UserNotFoundException(articlesDTO.getUserId());
        articlesRepository.save(post);
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
    public Articles updateArticles(ArticlesDTO articlesDTO, Long articlesId) {
        Articles articles = articlesRepository.findById(articlesId)
                .orElseThrow(() -> new EntityNotFoundException("Articles not found with id: " + articlesId));

        articles.setTitle(articlesDTO.getTitle());
        articles.setContent(articlesDTO.getContent());

        return articlesRepository.save(articles);
    }

    @Override
    public void updateArticlesStatus(Long articlesId) {
        Articles articles = articlesRepository.findById(articlesId)
                .orElseThrow(() -> new EntityNotFoundException("Articles not found with id: " + articlesId));

        articles.setStatus(!articles.getStatus());

        articlesRepository.save(articles);
    }
}
