package ait.cohort49.hostel_casa_flamingo.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Objects;

@Schema(description = "DTO for specifying the entry and departure dates for a cart item")
public class CartDatesDto {

    @FutureOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Entry date", type = "string", format = "date", example = "2025-02-10")
    private LocalDate entryDate;

    @FutureOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Departure date", type = "string", format = "date", example = "2025-02-15")
    private LocalDate departureDate;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartDatesDto that = (CartDatesDto) o;
        return Objects.equals(entryDate, that.entryDate) && Objects.equals(departureDate, that.departureDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entryDate, departureDate);
    }

    @Override
    public String toString() {
        return "CartDatesDto{" +
                "entryDate=" + entryDate +
                ", departureDate=" + departureDate +
                '}';
    }
}
