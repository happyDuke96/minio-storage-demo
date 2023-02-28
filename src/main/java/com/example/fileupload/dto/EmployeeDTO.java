package com.example.fileupload.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeDTO {

    Integer id;

    String firstName;

    String lastName;

    String logoId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    FileUploadDTO fileDTO;
}
