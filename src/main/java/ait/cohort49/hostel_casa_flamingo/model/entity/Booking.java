package ait.cohort49.hostel_casa_flamingo.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "entry_date", nullable = false)
    private LocalDate entryDate;

    @Column(name = "departure_date", nullable = false)
    private LocalDate departureDate;


    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "booking_number", nullable = false, unique = true)
    private String bookingNumber;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "bed_id", nullable = false)
    private Bed bed;

    public Booking(LocalDate entryDate, LocalDate departureDate, BigDecimal totalPrice, User user, Bed bed) {
        this.entryDate = entryDate;
        this.departureDate = departureDate;
        this.totalPrice = totalPrice;
        this.bookingNumber = UUID.randomUUID().toString();
        this.user = user;
        this.bed = bed;
    }

    public Booking() {
    }

    public Long getId() {
        return id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Bed getBed() {
        return bed;
    }

    public void setBed(Bed bed) {
        this.bed = bed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return Objects.equals(id, booking.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Booking{" +
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
