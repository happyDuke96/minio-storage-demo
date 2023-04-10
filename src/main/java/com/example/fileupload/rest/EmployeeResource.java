package com.example.fileupload.rest;


import com.example.fileupload.domain.Employee;
import com.example.fileupload.domain.EmployeeDetail;
import com.example.fileupload.repository.EmployeeRepository;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PUBLIC;


@RestController
@RequestMapping("/api/v1/employees")
@Api("Employee API")
@Slf4j
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PUBLIC)
public class EmployeeResource {


    @Autowired
    private final EmployeeRepository repository;

    @GetMapping
    public ResponseEntity<Void> get(){
        Set<EmployeeDetail> details = new HashSet<>();
        Employee employee = new Employee();
        employee.setFirstName("one");
        EmployeeDetail detail = new EmployeeDetail();
        for (int i = 0; i < 2; i++) {
            detail.setBarcode("one");
            detail.setQty("10");
            details.add(detail);
        }
        employee.setDetails(details);
        repository.save(employee);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/get")
    public ResponseEntity<List<EmployeeDetail>> getList(){
        List<EmployeeDetail> details = new ArrayList<>();
        List<Employee> all = repository.findAll();
        for (Employee employee : all) {
            details.addAll(employee.getDetails());
        }
        return ResponseEntity.ok().body(details);
    }
}
