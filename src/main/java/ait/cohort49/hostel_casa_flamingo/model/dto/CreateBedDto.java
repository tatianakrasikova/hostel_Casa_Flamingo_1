package ait.cohort49.hostel_casa_flamingo.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;


@Schema(description = "Class that describes Bed")
public class CreateBedDto {

    @NotBlank(message = "Bed number must not be empty or blank")
    @Pattern(regexp = "^\\S+$", message = "Bed number must not contain spaces")
    @Size(min = 1, max = 10, message = "Bed number must be between 1 and 10 characters")
    private String number;

    @NotBlank(message = "Bed type must not be empty or blank")
    @Size(min = 1, max = 30, message = "Bed type must be between 1 and 30 characters")
    private String type;

    @PositiveOrZero(message = "Price must be non-negative")
    @Digits(integer = 10, fraction = 2, message = "Price must be a valid number with at most one digit after the decimal")
    private BigDecimal price;

    private Long roomId;

    private List<String> imageUrls;

    private String description;

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
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
    public String toString() {
        return String.format("Bed: number - %s, type - %s, price - %s, roomId - %s, description - %s, imageUrls - %s ",
                number, type, price, roomId, imageUrls, description);
    }
}
