package com.example.fileupload.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseFileUploadDTO {
    String id;

    String contentType;

    String fileName;

    String url;

    String path;
}
