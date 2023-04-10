package com.example.fileupload.domain;

import com.example.fileupload.dto.FileUploadDTO;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "logo_id",insertable = false,updatable = false)
    private FileUpload logo;

    @Column(name = "logo_id")
    private String logoId;

    @ElementCollection
    @CollectionTable(
            name = "employee_details",
            joinColumns = @JoinColumn(name = "employee_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    )
    @Column(name = "product_details")
    private Set<EmployeeDetail> details;

}
