package ait.cohort49.hostel_casa_flamingo.service;

import ait.cohort49.hostel_casa_flamingo.model.entity.Image;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.S3StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class S3StorageServiceImpl implements S3StorageService {

    private final S3Client s3Client;

    @Value("${s3.bucketName}")
    private String bucketName;

    @Value("${s3.endpoint}")
    private String endpoint;

    private static final Map<String, String> IMAGE_CACHE = Collections.synchronizedMap(
            new LinkedHashMap<>(16, 0.75f, true) {
                @Override
                protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
                    // Размер кеша ограничен 100 элементами
                    return size() > 100;
                }
            }
    );


    public S3StorageServiceImpl(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    /**
     * Загрузка файла в S3
     */
    @Override
    public void uploadFile(String s3Path, MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(s3Path)
                    .acl("public-read")
                    .build();
            RequestBody requestBody = RequestBody.fromInputStream(inputStream, file.getSize());

            s3Client.putObject(putObjectRequest, requestBody);

        } catch (IOException e) {
            System.err.println("Error uploading file to S3: " + e.getMessage());
        }
    }


    /**
     * Retrieves the URL of an image stored in the S3 bucket corresponding to the provided key.
     * If the URL is already cached, it returns the cached value; otherwise, it computes
     * the URL, stores it in the cache, and returns it.
     *
     * @param key the key associated with the image in the S3 bucket; must not be null or empty
     * @return the URL of the image if the key is valid, or null if the key is null or empty
     */
    @Override
    public String getImageUrl(String key) {
        if (key == null || key.trim().isEmpty()) {
            return null;
        }
        String imageUrl = String.format("%s/%s/%s", endpoint, bucketName, key);
        IMAGE_CACHE.computeIfAbsent(key, k -> imageUrl);
        return IMAGE_CACHE.get(key);
    }

    @Override
    public List<String> getImageUrl(List<Image> bedImages) {
        if (bedImages == null || bedImages.isEmpty()) {
            return new ArrayList<>();
        }
        return bedImages.stream()
                .map(image -> getImageUrl(image.getS3Path()))
                .toList();
    }

    /**
     * Удаление файла из S3
     */
    @Override
    public void deleteFile(String s3Path) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(s3Path)
                .build();

        s3Client.deleteObject(deleteObjectRequest);
    }
}
