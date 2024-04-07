package com.example.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.util.UUID;

@Component
@Slf4j
public class SpacesUploader {

    @Value("${spaces.name}")
    private String spaceName;
    private S3Client s3Client;

    @Autowired
    public SpacesUploader(S3Client s3Client) {
        this.s3Client = s3Client;
    }


    private void uploadFile(String spaceName, String objectKey, MultipartFile file) {
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(spaceName)
                    .key(objectKey)
                    .acl(ObjectCannedACL.PUBLIC_READ)
                    .contentType(file.getContentType())
                    .build();
            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        } catch (S3Exception | IOException e) {
            log.error("Error uploading file to Spaces", e);
        }
    }

    public String uploadAndReturnUrl(MultipartFile file) {
        String fileExtension = getFileExtension(file.getOriginalFilename());
        String folderId = generateRandomObjectKey();
        String originalObjectKey = folderId + "." + fileExtension;


        uploadFile(spaceName, originalObjectKey, file);

        return "https://" + spaceName + "/" + originalObjectKey;
    }

    private String generateRandomObjectKey() {
        return UUID.randomUUID().toString();
    }

    private String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        return (dotIndex == -1) ? "" : filename.substring(dotIndex + 1);
    }
}
