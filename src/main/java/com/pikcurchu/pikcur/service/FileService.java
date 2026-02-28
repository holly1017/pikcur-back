package com.pikcurchu.pikcur.service;

import com.pikcurchu.pikcur.common.ResponseCode;
import com.pikcurchu.pikcur.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.cloudfront.domain}")
    private String cdnDomain;

    public String goodsUploadFile(MultipartFile file) {
        return uploadToS3(file, "goods");
    }

    public String questionUploadFile(MultipartFile file) {
        return uploadToS3(file, "question");
    }

    public String answerUploadFile(MultipartFile file) {
        return uploadToS3(file, "answer");
    }

    public String profileUploadFile(MultipartFile file) {
        return uploadToS3(file, "profile");
    }

    private String uploadToS3(MultipartFile file, String dir) {
        if (file.isEmpty())
            return null;

        String key = dir + "/" + UUID.randomUUID() + "_" + file.getOriginalFilename();

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .contentType(file.getContentType())
                .build();

        try {
            s3Client.putObject(
                    request,
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        } catch (Exception e) {
            throw new BusinessException(ResponseCode.INTERNAL_SERVER_ERROR);
        }

        return cdnDomain + "/" + key;
    }
}