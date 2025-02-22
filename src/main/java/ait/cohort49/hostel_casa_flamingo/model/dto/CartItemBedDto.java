package ait.cohort49.hostel_casa_flamingo.model.dto;

import java.time.LocalDate;
import java.util.Objects;


public class CartItemBedDto {

    private Long id;
    private LocalDate entryDate;
    private LocalDate departureDate;
    private BedDto bed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public BedDto getBed() {
        return bed;
    }

    public void setBed(BedDto bed) {
        this.bed = bed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItemBedDto that = (CartItemBedDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return String.format("CartItemBed: id - %d, entryDate - %s, departureDate - %s, bed - %s",
                id, entryDate, departureDate, bed);
    }
}
