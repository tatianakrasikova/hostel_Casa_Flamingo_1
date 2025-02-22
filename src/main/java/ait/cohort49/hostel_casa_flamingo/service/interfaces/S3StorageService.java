package ait.cohort49.hostel_casa_flamingo.service.interfaces;

import ait.cohort49.hostel_casa_flamingo.model.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface S3StorageService {

    void uploadFile(String s3Path, MultipartFile file);

    String getImageUrl(String s3Path);

    List<String> getImageUrl(List<Image> bedImages);

    void deleteFile(String s3Path);
}
