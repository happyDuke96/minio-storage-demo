package com.example.fileupload.service;

import com.example.fileupload.config.ApplicationProperties;
import com.example.fileupload.domain.FileUpload;
import com.example.fileupload.dto.FileUploadDTO;
import com.example.fileupload.repository.FileUploadRepository;
import io.minio.BucketExistsArgs;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.FileNameUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class FileUploadService {

    private final MinioClient minioClient;
    private final FileUploadRepository repository;
    private ApplicationProperties properties;


    public FileUploadService(MinioClient minioClient,
                             FileUploadRepository repository,
                             ApplicationProperties properties) {
        this.minioClient = minioClient;
        this.repository = repository;
        this.properties = properties;
    }


    public FileUpload fileUpload(MultipartFile file) {
        this.validateFile(file);
        FileUploadDTO dto;
        try {
            dto = uploadToStorageServer(file.getBytes(), file.getOriginalFilename(), file.getContentType());
        } catch (IOException e) {
            log.error("An unaccepted error has occurred while uploading file: ", e);
            throw new RuntimeException("An unaccepted error has occurred while uploading file");
        }
        FileUpload fileUpload = new FileUpload();
        fileUpload.setFileName(dto.getFileName());
        fileUpload.setContentType(file.getContentType());
        fileUpload.setSize(file.getSize());
        fileUpload.setUrl(dto.getUrl());
        fileUpload = repository.save(fileUpload);
        return fileUpload;
    }


    public FileUploadDTO uploadToStorageServer(byte[] file, String fileName, String contentType) {
        UUID uuid = UUID.randomUUID();
        String filename = encodeFileName(fileName);
        String objectName = getObjectName(fileName, uuid);

        ByteArrayInputStream stream = new ByteArrayInputStream(file);

        log.info("fileName: {} -------------------------------------", filename);
        log.info("objectName: {} -------------------------------------", objectName);
        log.info("contentType: {} -------------------------------------", contentType);
        log.info("objectSize: {} -------------------------------------", file.length);

        FileUploadDTO uploadDTO = null;
        try {

            this.uploadWIthPutObject(
                    PutObjectArgs
                            .builder()
                            .bucket(properties.getMinioStorage().getApplication())
                            .object(objectName)
                            .stream(stream, file.length, -1)
                            .contentType(contentType)
                            .build()
            );
            uploadDTO = new FileUploadDTO();
            uploadDTO.setId(uuid.toString());
            uploadDTO.setFileName(filename);
            uploadDTO.setUrl(properties.getMinioStorage().getHost());
        } catch (Exception e) {
            log.error("Close uploaded file error: {}", e.getMessage());
        } finally {
            try {
                stream.close();
            } catch (Exception e) {
                log.error("Close uploaded file error: {}", e.getMessage());
            }
        }
        return uploadDTO;
    }


    private void validateFile(MultipartFile file) {
        if (file.isEmpty() || file == null) {
            throw new RuntimeException("");
        }
        if (!StringUtils.hasLength(file.getOriginalFilename())) {
            throw new RuntimeException("");
        }
    }

    private String encodeFileName(String originalFilename) {
        String filename = null;
        try {
            URLEncoder.encode(originalFilename, "UTF-8");
            URLDecoder.decode(filename, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.info(e.getMessage());
        }
        return filename;
    }

    private String getObjectName(String fileName, UUID uuid) {
        String correctedName = encodeFileName(fileName);
        String filename = FileNameUtils.getBaseName(fileName);
        String extension = FileNameUtils.getExtension(filename);
        return filename.concat("-").concat(uuid.toString()).concat(!StringUtils.hasLength(extension) ? "" : "." + extension);
    }

    private void uploadWIthPutObject(PutObjectArgs objectArgs) {

        try {
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(objectArgs.bucket()).build())) {
                throw new RuntimeException("Bucket not exist");
            }
            Optional.ofNullable(this.minioClient.putObject(objectArgs)).map(ObjectWriteResponse::etag);
        } catch (Exception e) {
            log.error("Error upload file: {}", e.getMessage());
            throw new RuntimeException("Error upload file");
        }
    }

}
