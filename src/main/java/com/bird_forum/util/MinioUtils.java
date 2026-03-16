package com.bird_forum.util;

import com.bird_forum.config.MinioConfig;
import io.minio.*;
import io.minio.http.Method;

import javax.swing.*;
import java.io.InputStream;

public class MinioUtils {

    /**
     * 上传文件
     * @param stream 文件流
     * @param objectName 文件名 /xxx/xxx.jpg
     * @return 文件路径
     */
    public static String uploadFile(InputStream stream, String objectName) {
        try {
            MinioClient minioClient = MinioConfig.getMinioClient();

            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(MinioConfig.minioConfig.getBucketName())
                    .object(objectName)
                    .stream(stream, -1, 1024*1024*10)
                    .build());

            return objectName;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除文件
     * @param objectName 文件名 /xxx/xxx.jpg
     */
    public static void deleteFile(String objectName) {
        try {
            MinioClient minioClient = MinioConfig.getMinioClient();

            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(MinioConfig.minioConfig.getBucketName())
                            .object(objectName)
                            .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取 Minio 外链
     *
     * @param objectName 文件名 /xxx/xxx.jpg
     * @return 文件url
     */
    public static String getFileUrl(String objectName) {
        try {
            MinioClient minioClient = MinioConfig.getMinioClient();

            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .bucket(MinioConfig.minioConfig.getBucketName())
                            .object(objectName)
                            .method(Method.GET)
                            .expiry(60 * 60 * 24 * 7)
                            .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取 Minio 文件
     *
     * @param objectName 文件名 /xxx/xxx.jpg
     * @return 文件流
     */
    public static InputStream getFile(String objectName) {
        try {
            MinioClient minioClient = MinioConfig.getMinioClient();

            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(MinioConfig.minioConfig.getBucketName())
                            .object(objectName)
                            .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
