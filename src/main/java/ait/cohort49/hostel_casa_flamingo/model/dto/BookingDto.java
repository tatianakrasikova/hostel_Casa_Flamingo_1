package ait.cohort49.hostel_casa_flamingo.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;


public class BookingDto {

    private Long id;
    private LocalDate entryDate;
    private LocalDate departureDate;
    private BigDecimal totalPrice;
    private String bookingNumber;
    private UserDto user;
    private BedDto bed;

    /*
     * временное поле, необходимо для email
     */
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

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

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getBookingNumber() {
        return bookingNumber;
    }

    public void setBookingNumber(String bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
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
        BookingDto that = (BookingDto) o;
        return Objects.equals(id, that.id) && Objects.equals(entryDate, that.entryDate) && Objects.equals(departureDate, that.departureDate) && Objects.equals(totalPrice, that.totalPrice) && Objects.equals(bookingNumber, that.bookingNumber) && Objects.equals(user, that.user) && Objects.equals(bed, that.bed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, entryDate, departureDate, totalPrice, bookingNumber, user, bed);
    }

    @Override
    public String toString() {
        return "BookingDto{" +
                "id=" + id +
                ", entryDate=" + entryDate +
                ", departureDate=" + departureDate +
                ", totalPrice=" + totalPrice +
                ", bookingNumber='" + bookingNumber + '\'' +
                ", user=" + user +
                ", bed=" + bed +
                '}';
    }
}
