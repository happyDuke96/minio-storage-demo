package com.example.fileupload.service;

import com.example.fileupload.domain.Employee;
import com.example.fileupload.domain.FileUpload;
import com.example.fileupload.dto.EmployeeDTO;
import com.example.fileupload.dto.UploadFileResponse;
import com.example.fileupload.exception.NotFoundException;
import com.example.fileupload.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class EmployeeService extends BaseService<Employee,Integer>{

    private final FileUploadService fileUploadService;
    private final EmployeeRepository repository;

    public EmployeeService(FileUploadService fileUploadService,
                           EmployeeRepository repository)
    {
        super(repository);
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

    public void update(Integer id,EmployeeDTO dto){
        Optional<Employee> optionalEmployee = repository.findById(id);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            if (dto.getFirstName() != null) {
                employee.setFirstName(dto.getFirstName());
            }
            if (dto.getLastName() != null) {
                employee.setLastName(dto.getLastName());
            }
            repository.save(employee);
        }
    }

    public EmployeeDTO get(Integer id){
        Employee employee = repository.findById(id).orElseThrow(() -> new NotFoundException("Employee not found"));
        return employee.toDto();
    }

    public List<EmployeeDTO> getList(){
        return repository.findAll().stream().map(Employee::toDto).collect(Collectors.toList());
    }

    public UploadFileResponse uploadImage(Integer id, MultipartFile file){
        Employee employee = repository.findById(id).orElseThrow(notFoundException(Employee.class.getSimpleName()));
        this.validateFile(file);

        FileUpload fileUpload = fileUploadService.fileUpload(file);
        employee.setLogoId(fileUpload.getId());
        employee.setLogo(fileUpload);
        repository.save(employee);
        return fileUpload.getDTO();
    }

}
