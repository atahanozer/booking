package com.booker.demo.repository;

import com.booker.demo.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query(value = "select count(b) > 0 from Booking b where ((startDate BETWEEN :startDate AND :endDate) OR (endDate BETWEEN :startDate AND :endDate)) AND propertyId = :propertyId AND id <> :bookingId AND canceled = false")
    boolean existsBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("propertyId") Long propertyId, @Param("bookingId") Long bookingId);

    @Query(value = "select count(b) > 0 from Booking b where ((startDate BETWEEN :startDate AND :endDate) OR (endDate BETWEEN :startDate AND :endDate)) AND propertyId = :propertyId AND canceled = false")
    boolean existsBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("propertyId") Long propertyId);
}
