package com.project.tour_booking.Controller;

import com.project.tour_booking.DTO.*;
import com.project.tour_booking.Entity.*;
import com.project.tour_booking.Service.ArticleReview.ArticleReviewService;
import com.project.tour_booking.Service.Articles.ArticlesService;
import com.project.tour_booking.Service.Booking.BookingService;
import com.project.tour_booking.Service.DepartureDay.DepartureDayService;
import com.project.tour_booking.Service.Destination.DestinationService;
import com.project.tour_booking.Service.Slide.SliderService;
import com.project.tour_booking.Service.Tour.TourService;
import com.project.tour_booking.Service.TourImage.TourImageService;
import com.project.tour_booking.Service.TourReview.TourReviewService;
import com.project.tour_booking.Service.TypeOfTour.TypeOfTourService;
import com.project.tour_booking.Service.User.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final TourService tourService;
    private final DestinationService destinationService;
    private final DepartureDayService departureDayService;
    private final TourImageService tourImageService;
    private final TourReviewService tourReviewService;
    private final TypeOfTourService typeOfTourService;
    private final SliderService sliderService;
    private final ArticlesService articlesService;
    private final ArticleReviewService articleReviewService;
    private final BookingService bookingService;

    // USER MANAGEMENT
    @GetMapping("/users-manage")
    public ResponseEntity<?> listUsers() {
        return ResponseEntity.ok(userService.listUsers());
    }

    @GetMapping("/users/{email}")
    public ResponseEntity<User> user(@PathVariable String email) {
        return new ResponseEntity<>(userService.user(email), HttpStatus.OK);
    }

    @PutMapping("/update-user-status/{id}")
    public ResponseEntity<?> updateUserStatus(@PathVariable Long id) {
        return ResponseEntity.ok(userService.updateUserStatus(id));
    }

    // TOUR MANAGEMENT
    @PostMapping("/tours")
    public ResponseEntity<Tour> saveTour(@Valid @RequestBody TourDTO tourDTO) {
        return new ResponseEntity<>(tourService.saveTour(tourDTO), HttpStatus.CREATED);
    }

    @PutMapping("/update-tours/{id}")
    public ResponseEntity<Tour> updateTour(@Valid @RequestBody TourDTO tourDTO, @PathVariable Long id) {
        return new ResponseEntity<>(tourService.updateTour(tourDTO, id), HttpStatus.OK);
    }

    @PutMapping("/update-tour-status/{id}")
    public ResponseEntity<Tour> updateTourStatus(@PathVariable Long id) {
        return new ResponseEntity<>(tourService.updateTourStatus(id), HttpStatus.OK);
    }

    // DESTINATIONS MANAGEMENT
    @PostMapping("/destinations")
    public ResponseEntity<Destination> saveDestination(@Valid @RequestBody DestinationDTO destinationDTO) {
        return new ResponseEntity<>(destinationService.saveDestination(destinationDTO), HttpStatus.CREATED);
    }

    @PutMapping("/update-destination-status/{id}")
    public ResponseEntity<Destination> updateDestinationStatus(@PathVariable Long id) {
        return new ResponseEntity<>(destinationService.updateDestinationStatus(id), HttpStatus.OK);
    }

    @PutMapping("/update-destinations/{id}")
    public ResponseEntity<Destination> updateDestination(@RequestBody Destination destination, @PathVariable Long id) {
        return new ResponseEntity<>(destinationService.updateDestination(destination, id), HttpStatus.OK);
    }

    // DEPARTURE DAY
    @PostMapping("/departure-days")
    public ResponseEntity<DepartureDay> saveDepartureDay(@Valid @RequestBody DepartureDayDTO departureDayDTO) {
        return new ResponseEntity<>(departureDayService.saveDepartureDay(departureDayDTO), HttpStatus.CREATED);
    }

    @PutMapping("/update-departure-days/{id}")
    public ResponseEntity<DepartureDay> updateDepartureDay(@Valid @RequestBody DepartureDayDTO departureDayDTO, @PathVariable Long id) {
        return new ResponseEntity<>(departureDayService.updateDepartureDay(departureDayDTO, id), HttpStatus.OK);
    }

    @PutMapping("/update-departure-day-status/{id}")
    public ResponseEntity<DepartureDay> updateDepartureDayStatus(@PathVariable Long id) {
        return new ResponseEntity<>(departureDayService.updateDepartureDayStatus(id), HttpStatus.OK);
    }

    // TOUR IMAGES
    @PostMapping("/tour-images")
    public ResponseEntity<TourImage> saveTourImage(@Valid @RequestBody TourImageDTO tourImageDTO) {
        return new ResponseEntity<>(tourImageService.saveTourImage(tourImageDTO), HttpStatus.CREATED);
    }

    @PutMapping("/update-tour-images/{id}")
    public ResponseEntity<TourImage> updateTourImage(@Valid @RequestBody TourImageDTO tourImageDTO, @PathVariable Long id) {
        return new ResponseEntity<>(tourImageService.updateTourImage(tourImageDTO, id), HttpStatus.OK);
    }

    @DeleteMapping("/delete-tour-images/{id}")
    public ResponseEntity<String> deleteTourImage(@PathVariable Long id) {
        tourImageService.deleteTourImage(id);
        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }

    // TOUR REVIEW
    @GetMapping("/tour-reviews/{tourReviewId}")
    public ResponseEntity<TourReview> getTourReview(@PathVariable Long tourReviewId) {
        return new ResponseEntity<>(tourReviewService.getTourReview(tourReviewId), HttpStatus.OK);
    }

    @GetMapping("/tour-reviews/tours/{tourId}/users/{userId}")
    public ResponseEntity<TourReview> getTourReviewByTourIdAndUserId(@PathVariable Long tourId, @PathVariable Long userId) {
        return new ResponseEntity<>(tourReviewService.getTourReviewByTourIdAndUserId(tourId, userId), HttpStatus.OK);
    }

    @GetMapping("/tour-reviews/users/{userId}")
    public ResponseEntity<List<TourReview>> getAllTourReviewByUserId(@PathVariable Long userId) {
        return new ResponseEntity<>(tourReviewService.getAllTourReviewByUserId(userId), HttpStatus.OK);
    }

    // TYPE OF TOUR
    @PostMapping("/type-of-tours")
    public ResponseEntity<TypeOfTour> saveTOT(@Valid @RequestBody TypeOfTourDTO typeOfTourDTO) {
        return new ResponseEntity<>(typeOfTourService.saveTOT(typeOfTourDTO), HttpStatus.CREATED);
    }

    @PutMapping("/update-type-of-tours-status/{id}")
    public ResponseEntity<TypeOfTour> updateTOTStatus(@PathVariable Long id) {
        return new ResponseEntity<>(typeOfTourService.updateTOTStatus(id), HttpStatus.OK);
    }

    @PutMapping("/update-type-of-tours/{id}")
    public ResponseEntity<TypeOfTour> updateTOT(@Valid @RequestBody TypeOfTour typeOfTour, @PathVariable Long id) {
        return new ResponseEntity<>(typeOfTourService.updateTOT(typeOfTour, id), HttpStatus.OK);
    }

    // SLIDER
    @PostMapping("/sliders")
    public ResponseEntity<Slider> saveSlider(@Valid @RequestBody SliderDTO sliderDTO) {
        return new ResponseEntity<>(sliderService.savSlider(sliderDTO), HttpStatus.CREATED);
    }

    @PutMapping("/update-sliders/{sliderId}")
    public ResponseEntity<Slider> updateSlider(@Valid @RequestBody Slider slider, @PathVariable Long sliderId) {
        return new ResponseEntity<>(sliderService.updateSlider(slider, sliderId), HttpStatus.OK);
    }

    @PutMapping("/update-slider-status/{sliderId}")
    public ResponseEntity<Slider> updateSlider(@PathVariable Long sliderId) {
        return new ResponseEntity<>(sliderService.updateSliderStatus(sliderId), HttpStatus.OK);
    }

    // ARTICLES
    @PutMapping("/update-articles/{id}")
    public ResponseEntity<Articles> updateArticles(@Valid @RequestBody Articles articles, @PathVariable Long id) {
        return new ResponseEntity<>(articlesService.updateArticles(articles, id), HttpStatus.OK);
    }

    @PutMapping("/update-articles-status/{id}")
    public ResponseEntity<Articles> updateArticlesStatus(@PathVariable Long id) {
        return new ResponseEntity<>(articlesService.updateArticlesStatus(id), HttpStatus.OK);
    }

    // ARTICLES REVIEW
    @GetMapping("/article-reviews/{articleReviewId}")
    public ResponseEntity<ArticleReview> getArticleReview(@PathVariable Long articleReviewId) {
        return new ResponseEntity<>(articleReviewService.getArticleReview(articleReviewId), HttpStatus.OK);
    }

    @GetMapping("/article-reviews/articles/{articleId}/users/{userId}")
    public ResponseEntity<ArticleReview> getArticleReviewByArticleIdAndUserId(@PathVariable Long userId, @PathVariable Long articleId) {
        return new ResponseEntity<>(articleReviewService.getArticleReviewByArticleIdAndUserId(articleId, userId),
                HttpStatus.OK);
    }

    @GetMapping("/article-reviews/users/{userId}")
    public ResponseEntity<List<ArticleReview>> getAllArticleReviewByUserId(@PathVariable Long userId) {
        return new ResponseEntity<>(articleReviewService.getAllArticleReviewByUserId(userId), HttpStatus.OK);
    }

    // BOOKING
    @GetMapping("/bookings/tours/{tourId}")
    public ResponseEntity<List<Booking>> getAllBookingByTourId(@PathVariable Long tourId) {
        return new ResponseEntity<>(bookingService.getAllBookingByTourId(tourId), HttpStatus.OK);
    }

    @GetMapping("/bookings/users/{userId}/tour/{tourId}")
    public ResponseEntity<List<Booking>> getBookingByUserIdAndTourId(@PathVariable Long userId, @PathVariable Long tourId) {
        return new ResponseEntity<>(bookingService.getBookingByUserIdAndTourId(userId, tourId), HttpStatus.OK);
    }
}
