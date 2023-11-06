package com.booker.demo.service;

import com.booker.demo.exception.BookingOverlappingException;
import com.booker.demo.exception.InvalidDateRangeException;
import com.booker.demo.exception.NotFoundException;
import com.booker.demo.model.Booking;
import com.booker.demo.repository.BlockRepository;
import com.booker.demo.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private BlockRepository blockRepository;

    public Booking createBooking(Booking booking) {
        validate(booking);
        return bookingRepository.save(booking);
    }

    private void validate(Booking booking) throws InvalidDateRangeException {
        if (booking.getStartDate().isAfter(booking.getEndDate())) {
            throw new InvalidDateRangeException("Start date cannot be after end date");
        }

        if (isBookingOverlapping(booking)) {
            throw new BookingOverlappingException("Booking overlaps with an existing booking or block.");
        }
    }

    public Booking updateBooking(Long id, Booking updatedBooking) {
        Booking existingBooking = bookingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Booking not found"));

        if (isBookingOverlapping(updatedBooking)) {
            throw new BookingOverlappingException("Booking overlaps with an existing booking or block.");
        }

        // Update the existing booking with new data
        existingBooking.setStartDate(updatedBooking.getStartDate());
        existingBooking.setEndDate(updatedBooking.getEndDate());
        existingBooking.setUserId(updatedBooking.getUserId());
        existingBooking.setCanceled(updatedBooking.getCanceled());

        return bookingRepository.save(existingBooking);
    }

    public void deleteBooking(Long id) {
        Booking existingBooking = bookingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Booking not found"));

        bookingRepository.delete(existingBooking);
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Booking not found"));
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    protected boolean isBookingOverlapping(Booking newBooking) {
        boolean bookingOverlapExists = bookingRepository.existsBetweenDates(newBooking.getStartDate(), newBooking.getEndDate(), newBooking.getPropertyId(), newBooking.getId());
        if (bookingOverlapExists) {
            return true;
        }

        boolean blockOverlapExists = blockRepository.existsBetweenDates(newBooking.getStartDate(), newBooking.getEndDate(), newBooking.getPropertyId());
        if (blockOverlapExists) {
            return true;
        }

        return false; // No overlaps found
    }
}