package com.bird_forum.config;

import io.micrometer.common.util.StringUtils;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@Data
public class MinioConfig {
    @Value("${bird-forum.minio.endpoint}")
    private String endpoint;
    @Value("${bird-forum.minio.username}")
    private String username;
    @Value("${bird-forum.minio.password}")
    private String password;
    @Value("${bird-forum.minio.bucket-name}")
    private String bucketName;

    @Getter
    public static MinioClient minioClient;
    @Getter
    public static MinioConfig minioConfig;

    @Bean
    public MinioClient initMinioClient() {
        try {
            if (StringUtils.isEmpty(endpoint)) {
                throw new RuntimeException("MinIO endpoint cannot be empty");
            }
            if (!endpoint.startsWith("http://")) {
                endpoint = "http://" + endpoint;
            }

            // 初始化 MinIO 客户端
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(endpoint)
                    .credentials(username, password)
                    .build();
            // 判断 MinIO 存储桶是否存在
            boolean isExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());

            if (!isExists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                log.info("MinIO bucket {} create success!", bucketName);
            } else {
                log.warn("MinIO bucket {} already exists.", bucketName);
            }

            MinioConfig.minioClient = minioClient;
            MinioConfig.minioConfig = this;

            return minioClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
