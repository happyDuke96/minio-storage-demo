package com.example.fileupload.config;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {

    private final ApplicationProperties properties;

    public MinioConfig(ApplicationProperties properties) {
        this.properties = properties;
    }

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(properties.getMinioStorage().getHost())
                .credentials(properties.getMinioStorage().getUsername(),
                             properties.getMinioStorage().getPassword())
                .build();
    }
}
