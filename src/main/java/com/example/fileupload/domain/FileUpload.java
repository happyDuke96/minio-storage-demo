package com.example.fileupload.domain;

import com.example.fileupload.dto.FileDTO;
import com.example.fileupload.dto.UploadFileResponse;
import com.example.fileupload.utils.FileUtils;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
public class FileUpload implements Serializable {

    private static final long serialVersionUID = 1L;

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

    @Column(name = "file_id")
    private String fileId;

    @Column(name = "path")
    private String path;


    public UploadFileResponse getDTO() {
        UploadFileResponse fileResponse = new UploadFileResponse();
        fileResponse.setId(getId());
        fileResponse.setFileName(getFileName());
        fileResponse.setName(FilenameUtils.getName(getFileName()));
        fileResponse.setContentType(getContentType());
        fileResponse.setFileDownloadUri(getUrl());
        fileResponse.setSize(getSize());
        fileResponse.setFileSize(FileUtils.getFileNameSizeAsString(getSize()));
        return fileResponse;
    }
    public FileDTO getFileDTO() {
        return new FileDTO(getId(), getUrl());
    }

}
