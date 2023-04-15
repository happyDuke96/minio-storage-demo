package com.example.fileupload.rest;


import com.example.fileupload.dto.EmployeeDTO;
import com.example.fileupload.dto.UploadFileResponse;
import com.example.fileupload.service.EmployeeService;
import com.example.fileupload.service.FileUploadService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PUBLIC;


@RestController
@RequestMapping("/api/v1/employees")
@Api("Employee API")
@Slf4j
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PUBLIC)
public class EmployeeResource {

    private final EmployeeService employeeService;
    private final FileUploadService fileUploadService;

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> get(@PathVariable Integer id){
        return ResponseEntity.ok().body(employeeService.get(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> update(@PathVariable Integer id,@RequestBody EmployeeDTO dto){
        employeeService.update(id,dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<EmployeeDTO>> getList(){
        return ResponseEntity.ok().body(employeeService.getList());
    }

    @PostMapping("/create")
    public ResponseEntity<Void> create(EmployeeDTO dto){
        employeeService.create(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/upload")
    public ResponseEntity<UploadFileResponse> uploadImage(@PathVariable Integer id,@RequestPart("file") MultipartFile file){
        log.debug("REST request to upload Employee image: {}", id);
        return ResponseEntity.ok().body(employeeService.uploadImage(id, file));
    }
}
