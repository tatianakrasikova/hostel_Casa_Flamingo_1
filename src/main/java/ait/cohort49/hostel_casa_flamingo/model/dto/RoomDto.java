package ait.cohort49.hostel_casa_flamingo.model.dto;


import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Schema(description = "Class that describes Room")
public class RoomDto {

    private Long id;
    private String number;
    private String type;
    private BigDecimal price;
    private List<BedDto> beds = new ArrayList<>();
    private List<String> imageUrls;
    public String description;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<BedDto> getBeds() {
        return beds;
    }

    public void setBeds(List<BedDto> beds) {
        this.beds = beds;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomDto roomDto = (RoomDto) o;
        return Objects.equals(id, roomDto.id) && Objects.equals(number, roomDto.number) && Objects.equals(type, roomDto.type) && Objects.equals(price, roomDto.price) && Objects.equals(beds, roomDto.beds) && Objects.equals(imageUrls, roomDto.imageUrls) && Objects.equals(description, roomDto.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, type, price, beds, imageUrls, description);
    }
}
