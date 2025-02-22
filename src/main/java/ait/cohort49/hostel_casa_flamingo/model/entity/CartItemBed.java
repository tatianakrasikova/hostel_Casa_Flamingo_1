package ait.cohort49.hostel_casa_flamingo.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import java.time.LocalDate;
import java.util.Objects;


@Entity
@Table(name = "cart_item_bed")
public class CartItemBed {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "entry_date", nullable = false)
    @FutureOrPresent
    private LocalDate entryDate;

    @Column(name = "departure_date", nullable = false)
    @FutureOrPresent
    private LocalDate departureDate;

    @ManyToOne
    @JoinColumn(name = "bed_id")
    private Bed bed;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

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

    public Bed getBed() {
        return bed;
    }

    public void setBed(Bed bed) {
        this.bed = bed;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItemBed that = (CartItemBed) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return String.format("CartItemBed: id - %d, entryDate - %s, departureDate - %s, bed - %s, cart - %s",
                id, entryDate, departureDate, bed, cart);
    }
}
