package ait.cohort49.hostel_casa_flamingo.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class AvailableBedDto {
    private Long id;
    private String number;
    private String type;
    private BigDecimal price;
    private Long roomId;
    private List<String> imageUrls;
    public String description;
    public boolean isAvailable;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("isAvailable")
    public boolean isAvailable() {
        return isAvailable;
    }

    @JsonProperty("isAvailable")
    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AvailableBedDto that = (AvailableBedDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return String.format("AvailableBedDto: id - %d, number - %s, type - %s, price - %s, roomId - %d, imageUrls - %s, description - %s, isAvailable - %s",
                id, number, type, price, roomId, imageUrls, description, isAvailable);
    }
}
