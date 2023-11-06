package com.booker.demo.repository;

import com.booker.demo.model.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BlockRepository extends JpaRepository<Block, Long> {
    @Query(value = "select count(b) > 0 from Block b where ((startDate BETWEEN :startDate AND :endDate) OR (endDate BETWEEN :startDate AND :endDate)) AND propertyId = :propertyId")
    boolean existsBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("propertyId") Long propertyId);
}
