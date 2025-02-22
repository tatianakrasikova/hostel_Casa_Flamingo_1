package ait.cohort49.hostel_casa_flamingo.model.dto;

import java.util.Objects;

public class ImageInfoDto {

    private Long imageId;
    private String imageUrl;

    public ImageInfoDto(Long imageId, String imageUrl) {
        this.imageId = imageId;
        this.imageUrl = imageUrl;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageInfoDto that = (ImageInfoDto) o;
        return Objects.equals(imageId, that.imageId) && Objects.equals(imageUrl, that.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageId, imageUrl);
    }

    @Override
    public String toString() {
        return "ImageInfoDto{" +
                "imageId=" + imageId +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
