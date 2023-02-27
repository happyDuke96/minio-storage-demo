package com.example.fileupload.domain;

import com.example.fileupload.dto.UploadFileResponse;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.compress.utils.FileNameUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class FileUpload {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "url") // url to minio storage
    private String url;

    @Column(name = "size")
    private Long size;


    public UploadFileResponse getDTO() {
        UploadFileResponse fileResponse = new UploadFileResponse();
        fileResponse.setId(getId());
        fileResponse.setFileName(getFileName());
        fileResponse.setName(FileNameUtils.getName(getFileName()));
        fileResponse.setContentType(getContentType());
        fileResponse.setFileDownloadUri(getUrl());
        fileResponse.setSize(getSize());
        fileResponse.setFileSize(FileUtils.getFileNameSizeAsString(getSize()));
        return fileResponse;
    }

}
