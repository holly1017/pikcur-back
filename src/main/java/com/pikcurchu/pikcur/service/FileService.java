package com.pikcurchu.pikcur.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.cloudfront.domain}")
    private String cdnDomain;

//    private final String goodsUploadPath = System.getProperty("user.dir") + "/uploads/images/goods/"; // 실제 경로 지정
//    private final String questionUploadPath = System.getProperty("user.dir") + "/uploads/images/question/"; // 실제 경로 지정
//    private final String answerUploadPath = System.getProperty("user.dir") + "/uploads/images/answer/"; // 실제 경로 지정
//    private final String profileUploadPath = System.getProperty("user.dir") + "/uploads/images/profile/"; // 실제 경로 지정

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
        if (file.isEmpty()) return null;

        String key = dir + "/" + UUID.randomUUID() + "_" + file.getOriginalFilename();

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .contentType(file.getContentType())
                .build();

        try {
            s3Client.putObject(
                    request,
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize())
            );
        } catch (Exception e) {
            throw new RuntimeException("S3 upload failed", e);
        }

        return cdnDomain + "/" + key;
    }


//    public String goodsUploadFile(MultipartFile file) {
//        if (file.isEmpty()) return null;
//
//        // 폴더가 없으면 생성
//        File folder = new File(goodsUploadPath);
//        if (!folder.exists()) folder.mkdirs();
//
//        // 파일명 생성
//        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
//        File dest = new File(goodsUploadPath + fileName);
//
//        try {
//            file.transferTo(dest);
//        } catch (Exception e) {
//            throw new RuntimeException("File upload failed", e);
//        }
//
//        // 브라우저 접근 URL 반환
//        return "/images/goods/" + fileName;
//    }
//    public String questionUploadFile(MultipartFile file) {
//        if (file.isEmpty()) return null;
//
//        // 폴더가 없으면 생성
//        File folder = new File(questionUploadPath);
//        if (!folder.exists()) folder.mkdirs();
//
//        // 파일명 생성
//        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
//        File dest = new File(questionUploadPath + fileName);
//
//        try {
//            file.transferTo(dest);
//        } catch (Exception e) {
//            throw new RuntimeException("File upload failed", e);
//        }
//
//        // 브라우저 접근 URL 반환
//        return "/images/question/" + fileName;
//    }
//    public String answerUploadFile(MultipartFile file) {
//        if (file.isEmpty()) return null;
//
//        // 폴더가 없으면 생성
//        File folder = new File(answerUploadPath);
//        if (!folder.exists()) folder.mkdirs();
//
//        // 파일명 생성
//        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
//        File dest = new File(answerUploadPath + fileName);
//
//        try {
//            file.transferTo(dest);
//        } catch (Exception e) {
//            throw new RuntimeException("File upload failed", e);
//        }
//
//        // 브라우저 접근 URL 반환
//        return "/images/answer/" + fileName;
//    }
//    public String profileUploadFile(MultipartFile file) {
//        if (file.isEmpty()) return null;
//
//        // 폴더가 없으면 생성
//        File folder = new File(profileUploadPath);
//        if (!folder.exists()) folder.mkdirs();
//
//        // 파일명 생성
//        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
//        File dest = new File(profileUploadPath + fileName);
//
//        try {
//            file.transferTo(dest);
//        } catch (Exception e) {
//            throw new RuntimeException("File upload failed", e);
//        }
//
//        // 브라우저 접근 URL 반환
//        return "/images/profile/" + fileName;
//    }
}

