package com.example.fileupload.service;

import com.example.fileupload.config.ApplicationProperties;
import com.example.fileupload.domain.FileUpload;
import com.example.fileupload.dto.BaseFileUploadDTO;
import com.example.fileupload.exception.BadRequestException;
import com.example.fileupload.repository.EmployeeRepository;
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
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class FileUploadService extends BaseService{

    private final MinioClient minioClient;
    private final FileUploadRepository repository;
    private final EmployeeRepository employeeRepository;
    private ApplicationProperties properties;


    public FileUploadService(MinioClient minioClient,
                             FileUploadRepository repository,
                             EmployeeRepository employeeRepository, ApplicationProperties properties) {
        super(repository);
        this.minioClient = minioClient;
        this.repository = repository;
        this.employeeRepository = employeeRepository;
        this.properties = properties;
    }


    public FileUpload fileUpload(MultipartFile file) {
        this.validateFile(file);
        BaseFileUploadDTO dto;
        try {
            dto = uploadToStorageServer(file.getBytes(), file.getOriginalFilename(), file.getContentType());
        } catch (IOException e) {
            log.error("An unaccepted error has occurred while uploading file: ", e);
            throw new BadRequestException("An unaccepted error has occurred while uploading file");
        }
        FileUpload fileUpload = new FileUpload();
        fileUpload.setFileName(dto.getFileName());
        fileUpload.setContentType(file.getContentType());
        fileUpload.setSize(file.getSize());
        fileUpload.setUrl(dto.getUrl());
        fileUpload = repository.save(fileUpload);
        return fileUpload;
    }


    public BaseFileUploadDTO uploadToStorageServer(byte[] file, String fileName, String contentType) {
        UUID uuid = UUID.randomUUID();
        String filename = encodeFileName(fileName);
        String objectName = getObjectName(fileName, uuid);

        ByteArrayInputStream stream = new ByteArrayInputStream(file);

        log.info("fileName: {} -------------------------------------", filename);
        log.info("objectName: {} -------------------------------------", objectName);
        log.info("contentType: {} -------------------------------------", contentType);
        log.info("objectSize: {} -------------------------------------", file.length);

        BaseFileUploadDTO uploadDTO = null;
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
            uploadDTO = new BaseFileUploadDTO();
            uploadDTO.setId(uuid.toString());
            uploadDTO.setFileName(filename);
            uploadDTO.setPath(String.format("%s/%s",properties.getMinioStorage().getApplication(),objectName));
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




    private String encodeFileName(String originalFilename) {
        String filename = null;
        try {
            filename = URLEncoder.encode(originalFilename, "UTF-8");
            filename = URLDecoder.decode(filename, "UTF-8");
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return filename;
    }

    private String getObjectName(String fileName, UUID uuid) {
        String correctedName = encodeFileName(fileName);
        String filename = FileNameUtils.getBaseName(correctedName);
        String extension = FileNameUtils.getExtension(filename);
        return filename.concat("-").concat(uuid.toString()).concat(!StringUtils.hasLength(extension) ? "" : "." + extension);
    }

    private void uploadWIthPutObject(PutObjectArgs objectArgs) {

        try {
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(objectArgs.bucket()).build())) {
                throw new BadRequestException("Bucket not exist");
            }
            Optional.ofNullable(this.minioClient.putObject(objectArgs)).map(ObjectWriteResponse::etag);
        } catch (Exception e) {
            log.error("Error upload file: {}", e.getMessage());
            throw new BadRequestException("Error upload file");
        }
    }
}
