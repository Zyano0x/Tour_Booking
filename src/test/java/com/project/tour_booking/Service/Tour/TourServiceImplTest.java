package com.project.tour_booking.Service.Tour;

import com.project.tour_booking.DTO.TourDTO;
import com.project.tour_booking.Entity.Destination;
import com.project.tour_booking.Entity.Tour;
import com.project.tour_booking.Entity.TourImage;
import com.project.tour_booking.Entity.TypeOfTour;
import com.project.tour_booking.Exception.TourNotFoundException;
import com.project.tour_booking.Exception.TourSaveException;
import com.project.tour_booking.Repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TourServiceImplTest {

    @Mock
    private TourRepository tourRepository;

    @Mock
    private TypeOfTourRepository typeOfTourRepository;

    @Mock
    private TourImageRepository tourImageRepository;

    @Mock
    private DepartureDayRepository departureDayRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private DestinationRepository destinationRepository;

    @InjectMocks
    private TourServiceImpl tourService;

    private TypeOfTour typeOfTour;

    private Destination destination;

    @BeforeEach
    void setUp() {
        typeOfTour = TypeOfTour.builder()
                .id(1L)
                .name("TOT Test")
                .description("TOT Desc Test")
                .status(true)
                .build();

        destination = Destination.builder()
                .id(1L)
                .name("Destination Test")
                .thumbnail("Thumbnail Test")
                .isHot(true)
                .status(true)
                .build();

        typeOfTourRepository.save(typeOfTour);
        destinationRepository.save(destination);
    }

    @AfterEach
    void tearDown() {
        typeOfTourRepository.deleteAll();
        destinationRepository.deleteAll();
    }

    @Test
    void saveTour_Success() {
        Tour tour = new Tour();
        TourImage tourImage = new TourImage();

        TourDTO tourDTO = TourDTO.builder()
                .name("Tour Test")
                .thumbnail("Thumbnail Test")
                .description("Tour Desc Test")
                .service("Tour Service Test")
                .time("Tour Time Test")
                .schedule("Tour Schedule Test")
                .priceForAdult(BigDecimal.valueOf(100.00))
                .priceForChildren(BigDecimal.valueOf(50.00))
                .departurePoint("Departure Point Test")
                .status(true)
                .isHot(true)
                .images(Arrays.asList("Image 1", "Image 2"))
                .totId(typeOfTour.getId())
                .destinationId(destination.getId())
                .build();

        when(typeOfTourRepository.findById(typeOfTour.getId())).thenReturn(Optional.of(typeOfTour));
        when(destinationRepository.findById(destination.getId())).thenReturn(Optional.of(destination));
        when(tourRepository.save(Mockito.any(Tour.class))).thenReturn(tour);
        when(tourImageRepository.save(Mockito.any(TourImage.class))).thenReturn(tourImage);

        Tour ret = tourService.saveTour(tourDTO);

        assertNotNull(ret);
        assertEquals(tourDTO.getName(), ret.getName());
        assertEquals(tourDTO.getThumbnail(), ret.getThumbnail());
        assertEquals(tourDTO.getDescription(), ret.getDescription());
        assertEquals(tourDTO.getService(), ret.getService());
        assertEquals(tourDTO.getTime(), ret.getTime());
        assertEquals(tourDTO.getSchedule(), ret.getSchedule());
        assertEquals(tourDTO.getPriceForAdult(), ret.getPriceForAdult());
        assertEquals(tourDTO.getPriceForChildren(), ret.getPriceForChildren());
        assertEquals(tourDTO.getDeparturePoint(), ret.getDeparturePoint());
        assertEquals(tourDTO.getStatus(), ret.getStatus());
        assertEquals(tourDTO.getIsHot(), ret.getIsHot());
        assertEquals(typeOfTour, ret.getTypeOfTour());
        assertEquals(destination, ret.getDestination());
    }

    @Test
    void saveTour_TypeOfTourNotFound() {
        Tour tour = new Tour();
        TourImage tourImage = new TourImage();

        TourDTO tourDTO = TourDTO.builder()
                .name("Tour Test")
                .thumbnail("Thumbnail Test")
                .description("Tour Desc Test")
                .service("Tour Service Test")
                .time("Tour Time Test")
                .schedule("Tour Schedule Test")
                .priceForAdult(BigDecimal.valueOf(100.00))
                .priceForChildren(BigDecimal.valueOf(50.00))
                .departurePoint("Departure Point Test")
                .status(true)
                .isHot(true)
                .images(Arrays.asList("Image 1", "Image 2"))
                .totId(typeOfTour.getId())
                .destinationId(destination.getId())
                .build();

        when(typeOfTourRepository.findById(typeOfTour.getId())).thenReturn(Optional.empty());

        assertThrows(TourSaveException.class, () -> tourService.saveTour(tourDTO));
    }

    @Test
    void saveTour_DestinationNotFound() {
        Tour tour = new Tour();
        TourImage tourImage = new TourImage();

        TourDTO tourDTO = TourDTO.builder()
                .name("Tour Test")
                .thumbnail("Thumbnail Test")
                .description("Tour Desc Test")
                .service("Tour Service Test")
                .time("Tour Time Test")
                .schedule("Tour Schedule Test")
                .priceForAdult(BigDecimal.valueOf(100.00))
                .priceForChildren(BigDecimal.valueOf(50.00))
                .departurePoint("Departure Point Test")
                .status(true)
                .isHot(true)
                .images(Arrays.asList("Image 1", "Image 2"))
                .totId(typeOfTour.getId())
                .destinationId(destination.getId())
                .build();

        when(typeOfTourRepository.findById(typeOfTour.getId())).thenReturn(Optional.of(typeOfTour));
        when(destinationRepository.findById(destination.getId())).thenReturn(Optional.empty());

        assertThrows(TourSaveException.class, () -> tourService.saveTour(tourDTO));
    }

    @Test
    void getExistingTour() {
        Tour tour = Tour.builder()
                .name("Tour Test")
                .thumbnail("Thumbnail Test")
                .description("Tour Desc Test")
                .service("Tour Service Test")
                .time("Tour Time Test")
                .schedule("Tour Schedule Test")
                .priceForAdult(BigDecimal.valueOf(100.00))
                .priceForChildren(BigDecimal.valueOf(50.00))
                .departurePoint("Departure Point Test")
                .dateOfPosting(LocalDate.now()) // Example date
                .status(true)
                .isHot(true)
                .typeOfTour(typeOfTour)
                .destination(destination)
                .build();

        when(tourRepository.findById(1L)).thenReturn(Optional.of(tour));

        Tour ret = tourService.getTour(1L);

        Assertions.assertNotNull(ret);
        assertEquals("Tour Test", ret.getName());
        assertEquals("Thumbnail Test", ret.getThumbnail());
        assertEquals("Tour Desc Test", ret.getDescription());
        assertEquals("Tour Time Test", ret.getTime());
        assertTrue(ret.getIsHot());
        assertTrue(ret.getStatus());
    }

    @Test
    void getNonExistingTour() {
        when(tourRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TourNotFoundException.class, () -> tourService.getTour(1L));
    }
}