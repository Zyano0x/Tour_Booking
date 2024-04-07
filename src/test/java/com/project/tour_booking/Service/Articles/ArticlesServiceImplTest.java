package com.project.tour_booking.Service.Articles;

import com.project.tour_booking.DTO.ArticlesDTO;
import com.project.tour_booking.Entity.Articles;
import com.project.tour_booking.Entity.Role;
import com.project.tour_booking.Entity.User;
import com.project.tour_booking.Exception.UserNotFoundException;
import com.project.tour_booking.Repository.ArticlesRepository;
import com.project.tour_booking.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArticlesServiceImplTest {

    @Mock
    private ArticlesRepository articlesRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ArticlesServiceImpl articlesService;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1337L)
                .name("WorryZy")
                .username("WorryZy")
                .email("ghuy042@gmail.com")
                .password(new BCryptPasswordEncoder().encode("0..982883636"))
                .birthday(LocalDate.of(2002, 05, 30))
                .gender("Male")
                .address("Viet Nam")
                .cid("1337")
                .phone("0865689470")
                .role(Role.ADMIN)
                .enabled(true)
                .locked(false)
                .build();

        userRepository.save(user);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void saveArticles_Success() {
        Articles articles = new Articles();

        ArticlesDTO articlesDTO = ArticlesDTO.builder()
                .title("Title Test")
                .description("Description Test")
                .thumbnail("Thumbnail Test")
                .content("Content Test")
                .userId(user.getId())
                .build();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(articlesRepository.save(Mockito.any(Articles.class))).thenReturn(articles);

        Articles ret = articlesService.saveArticles(articlesDTO);

        assertNotNull(ret);
        assertEquals("Title Test", ret.getTitle());
        assertEquals("Description Test", ret.getDescription());
        assertEquals("Thumbnail Test", ret.getThumbnail());
        assertEquals("Content Test", ret.getContent());
        assertEquals(LocalDate.now(), ret.getDateOfPosting());
        assertEquals(user, ret.getUser());
    }

    @Test
    void saveArticles_InvalidUserId() {
        ArticlesDTO articlesDTO = ArticlesDTO.builder()
                .title("Title Test")
                .description("Description Test")
                .thumbnail("Thumbnail Test")
                .content("Content Test")
                .userId(user.getId())
                .build();

        when(userRepository.findById(1337L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> articlesService.saveArticles(articlesDTO));
    }

    @Test
    void getArticles_Existing() {
        Articles articles = Articles.builder()
                .title("Title Test")
                .description("Description Test")
                .thumbnail("Thumbnail Test")
                .content("Content Test")
                .dateOfPosting(LocalDate.now())
                .editDate(LocalDate.now())
                .status(true)
                .user(user)
                .build();

        when(articlesRepository.findById(1L)).thenReturn(Optional.of(articles));

        Articles ret = articlesService.getArticles(1L);

        assertNotNull(ret);
        assertEquals("Title Test", ret.getTitle());
        assertEquals("Description Test", ret.getDescription());
        assertEquals("Thumbnail Test", ret.getThumbnail());
        assertEquals("Content Test", ret.getContent());
        assertEquals(LocalDate.now(), ret.getDateOfPosting());
        assertEquals(LocalDate.now(), ret.getEditDate());
        assertTrue(ret.getStatus());
        assertEquals(user, ret.getUser());
    }

    @Test
    void getArticles_NonExisting() {
        when(articlesRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> articlesService.getArticles(1L));
    }

    @Test
    void updateArticles_Success() {
        Articles articles = Articles.builder()
                .title("Title Test")
                .description("Description Test")
                .thumbnail("Thumbnail Test")
                .content("Content Test")
                .dateOfPosting(LocalDate.now())
                .editDate(LocalDate.now())
                .status(true)
                .user(user)
                .build();

        Articles update = Articles.builder()
                .title("Title Test Update")
                .description("Description Test Update")
                .thumbnail("Thumbnail Test Update")
                .content("Content Test Update")
                .dateOfPosting(LocalDate.now())
                .editDate(LocalDate.now())
                .status(false)
                .user(user)
                .build();

        when(articlesRepository.findById(1L)).thenReturn(Optional.of(articles));
        when(articlesRepository.save(Mockito.any(Articles.class))).thenReturn(articles);

        Articles ret = articlesService.updateArticles(update, 1L);

        assertNotNull(ret);
        assertEquals("Title Test Update", ret.getTitle());
        assertEquals("Description Test Update", ret.getDescription());
        assertEquals("Thumbnail Test Update", ret.getThumbnail());
        assertEquals("Content Test Update", ret.getContent());
        assertFalse(ret.getStatus());
        assertEquals(user, ret.getUser());
    }

    @Test
    void updateArticles_NotFound() {
        Articles update = Articles.builder()
                .title("Title Test Update")
                .description("Description Test Update")
                .thumbnail("Thumbnail Test Update")
                .content("Content Test Update")
                .dateOfPosting(LocalDate.now())
                .editDate(LocalDate.now())
                .status(false)
                .user(user)
                .build();

        when(articlesRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> articlesService.updateArticles(update, 1L));
    }
}