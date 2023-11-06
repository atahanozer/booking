package com.booker.demo.service;

import com.booker.demo.exception.BookingOverlappingException;
import com.booker.demo.model.Booking;
import com.booker.demo.repository.BlockRepository;
import com.booker.demo.repository.BookingRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookingServiceTest {

    @Autowired
    private BookingService bookingService;

    @MockBean
    private BookingRepository bookingRepository;

    @MockBean
    private BlockRepository blockRepository;

    @Test
    public void isBookingOverlapping_NoOverlap() {
        Booking newBooking = new Booking();

        Mockito.when(bookingRepository.existsBetweenDates(newBooking.getStartDate(), newBooking.getEndDate(), newBooking.getPropertyId())).thenReturn(false);

        boolean isOverlapping = bookingService.isBookingOverlapping(newBooking);

        assertFalse(isOverlapping);
    }

    @Test
    public void isBookingOverlapping_Overlap() {
        // Create a new booking and define existing bookings that overlap
        Booking newBooking = new Booking(/* Set booking details */);
        // List<Booking> existingBookings = Arrays.asList(/* Existing bookings that overlap */);

        // Mockito.when(bookingRepository.findAll()).thenReturn(existingBookings);
        Mockito.when(bookingRepository.existsBetweenDates(newBooking.getStartDate(), newBooking.getEndDate(), newBooking.getPropertyId(), newBooking.getId())).thenReturn(true);


        boolean isOverlapping = bookingService.isBookingOverlapping(newBooking);

        assertTrue(isOverlapping);
    }

    @Test
    public void createBooking_NoOverlap() {
        Booking newBooking = new Booking(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 5), 1L, 1L, false);

        Mockito.when(bookingRepository.existsBetweenDates(newBooking.getStartDate(), newBooking.getEndDate(), newBooking.getPropertyId())).thenReturn(false);
        Mockito.when(bookingRepository.save(newBooking)).thenReturn(newBooking);

        Booking booking = bookingService.createBooking(newBooking);

        assertEquals(newBooking, booking);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createBooking_Overlap() {
        Booking newBooking = new Booking(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 5), 1L, 1L, false);

        Mockito.when(bookingRepository.existsBetweenDates(newBooking.getStartDate(), newBooking.getEndDate(), newBooking.getPropertyId(), newBooking.getId())).thenReturn(true);

        thrown.expect(BookingOverlappingException.class);
        thrown.expectMessage("Booking overlaps with an existing booking or block.");
        bookingService.createBooking(newBooking);

    }

}
