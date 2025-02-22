package ait.cohort49.hostel_casa_flamingo.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Room number must not be empty or blank")
    @Pattern(regexp = "^\\S+$", message = "Room number must not contain spaces")
    @Size(min = 1, max = 10, message = "Room number must be between 1 and 10 characters")
    private String number;

    @NotBlank(message = "Room type must not be empty or blank")
    @Size(min = 1, max = 30, message = "Room type must be between 1 and 30 characters")
    private String type;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bed> beds = new ArrayList<>();

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Image> images;

    @Column(name = "description")
    private String description;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Bed> getBeds() {
        return beds;
    }

    public void setBeds(List<Bed> beds) {
        this.beds = beds;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
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
        if (!(o instanceof Room room)) return false;
        return Objects.equals(id, room.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", type='" + type + '\'' +
                ", beds=" + beds +
                ", images=" + images +
                ", description='" + description + '\'' +
                '}';
    }
}
