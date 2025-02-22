package ait.cohort49.hostel_casa_flamingo.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "bed", uniqueConstraints = @UniqueConstraint(columnNames = {"room_id", "bed_number"}))
public class Bed {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bed_number")
    @NotBlank(message = "Bed number must not be empty or blank")
    @Pattern(regexp = "^\\S+$", message = "Bed number must not contain spaces")
    @Size(min = 1, max = 10, message = "Bed number must be between 1 and 10 characters")
    private String number;

    @Column(name = "bed_type")
    @NotBlank(message = "Bed type must not be empty or blank")
    @Size(min = 1, max = 30, message = "Bed type must be between 1 and 30 characters")
    private String type;

    @Column(name = "bed_price", nullable = false)
    @PositiveOrZero(message = "Price must be non-negative")
    @Digits(integer = 10, fraction = 2, message = "Price must be a valid number with at most one digit after the decimal")
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @OneToMany(mappedBy = "bed", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Image> images;

    @Column(name = "description")
    private String description;

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bed bed = (Bed) o;
        return Objects.equals(id, bed.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return String.format("Bed: id - %d, number - %s, type - %s, price - %s, description - %s, images - %s",
                id, number, type, price, description, images);
    }
}
