package com.example.fileupload.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UploadFileResponse {

    String id;

    String name;

    String fileName;

    String fileDownloadUri;

    String contentType;

    Long size;

    String fileSize;

    String path;
}
