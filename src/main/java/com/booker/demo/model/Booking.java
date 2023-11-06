package com.booker.demo.model;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "startDate")
    @NotNull
    private LocalDate startDate;
    @Column(name = "endDate")
    @NotNull
    private LocalDate endDate;
    @Column(name = "userId")
    @NotNull
    private Long userId;
    @Column(name = "propertyId")
    @NotNull
    private Long propertyId;
    @Column(name = "canceled")
    private boolean canceled;

    public Booking(LocalDate startDate, LocalDate endDate, Long userId, Long propertyId, boolean canceled) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.userId = userId;
        this.propertyId = propertyId;
        this.canceled = canceled;
    }

    public Booking() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }
    public boolean getCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return Objects.equals(getId(), booking.getId()) && Objects.equals(getStartDate(), booking.getStartDate()) && Objects.equals(getEndDate(), booking.getEndDate()) && Objects.equals(getUserId(), booking.getUserId()) && Objects.equals(getPropertyId(), booking.getPropertyId()) && Objects.equals(getCanceled(), booking.getCanceled());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getStartDate(), getEndDate(), getUserId(), getPropertyId(), getCanceled());
    }
}
