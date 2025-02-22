package ait.cohort49.hostel_casa_flamingo.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

import java.util.Objects;

public class CreateOrUpdateRoomDto {

    @NotBlank(message = "Room number must not be empty or blank")
    @Pattern(regexp = "^\\S+$", message = "Room number must not contain spaces")
    @Size(min = 1, max = 10, message = "Room number must be between 1 and 10 characters")
    private final String number;

    @NotBlank(message = "Room type must not be empty or blank")
    @Size(min = 1, max = 30, message = "Room type must be between 1 and 30 characters")
    private final String type;

    private String description;

    private List<String> imageUrls;

    public CreateOrUpdateRoomDto(String number, String type, String description) {
        this.number = number;
        this.type = type;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public String getNumber() {
        return number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateOrUpdateRoomDto that = (CreateOrUpdateRoomDto) o;
        return Objects.equals(number, that.number) && Objects.equals(type, that.type) && Objects.equals(description, that.description) && Objects.equals(imageUrls, that.imageUrls);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, type, description, imageUrls);
    }

    @Override
    public String toString() {
        return "CreateOrUpdateRoomDto{" +
                "number='" + number + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", imageUrls=" + imageUrls +
                '}';
    }
}
