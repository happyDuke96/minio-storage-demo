package com.example.fileupload.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Getter
@Setter
@Embeddable
public class EmployeeDetail {
    private String barcode;
    private String qty;
}
