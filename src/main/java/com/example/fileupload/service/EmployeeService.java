package com.example.fileupload.service;

import com.example.fileupload.domain.Employee;
import com.example.fileupload.dto.EmployeeDTO;
import com.example.fileupload.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class EmployeeService {

    private final FileUploadService fileUploadService;
    private final EmployeeRepository repository;

    public EmployeeService(FileUploadService fileUploadService,
                           EmployeeRepository repository)
    {
        this.fileUploadService = fileUploadService;
        this.repository = repository;
    }

    public void create(EmployeeDTO dto){
        Employee employee = Employee
                .builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .logoId(dto.getLogoId())
                .build();
        repository.save(employee);
    }

    public void update(EmployeeDTO dto){
        Employee employee = new Employee();
        if (dto.getFirstName() != null){
            employee.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null){
            employee.setLastName(dto.getLastName());
        }
        repository.save(employee);
    }
}
