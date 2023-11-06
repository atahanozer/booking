package com.booker.demo.model;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "blocks")
public class Block {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "startDate")
    @NotNull
    private LocalDate startDate;
    @Column(name = "endDate")
    @NotNull
    private LocalDate endDate;
    @Column(name = "propertyId")
    @NotNull
    private Long propertyId;

    public Block(LocalDate startDate, LocalDate endDate, Long propertyId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.propertyId = propertyId;
    }

    public Block() {
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

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Block block = (Block) o;
        return Objects.equals(getId(), block.getId()) && Objects.equals(getStartDate(), block.getStartDate()) && Objects.equals(getEndDate(), block.getEndDate()) && Objects.equals(getPropertyId(), block.getPropertyId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getStartDate(), getEndDate(), getPropertyId());
    }
}