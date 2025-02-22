package ait.cohort49.hostel_casa_flamingo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

import java.net.URI;

@Configuration
public class S3Config {

    @Value("${s3.endpoint}")
    private String s3Endpoint;

    @Value("${s3.region}")
    private String s3Region;

    @Value("${s3.accessKey}")
    private String accessKey;

    @Value("${s3.secretKey}")
    private String secretKey;

    /**
     * Создание синхронного клиента S3 для работы с S3Presigner
     */
    @Bean
    @Primary
    public S3Client s3Client() {
        return S3Client.builder()

                .endpointOverride(URI.create(s3Endpoint))
                .region(Region.of(s3Region))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
                .serviceConfiguration(S3Configuration.builder().pathStyleAccessEnabled(true).build())
                .build();
    }
}
