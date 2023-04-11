package com.example.fileupload;

import com.example.fileupload.config.ApplicationProperties;
import com.example.fileupload.domain.Employee;
import com.example.fileupload.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableConfigurationProperties({ApplicationProperties.class})
public class FileUploadApplication implements CommandLineRunner {

    private final EmployeeRepository repository;

    public FileUploadApplication(EmployeeRepository repository) {
        this.repository = repository;
    }

    public static void main(String[] args) {
        SpringApplication.run(FileUploadApplication.class, args);
    }

    public static Long toCents(BigDecimal amount) {
        amount = amount == null ? BigDecimal.ZERO : amount;
        return amount.multiply(BigDecimal.valueOf(100)).longValue();
    }


    public static BigDecimal fromCents(Long amount) {
        if (amount == null) {
            return BigDecimal.ZERO;
        } else {
            return (BigDecimal.valueOf(amount / 100.00)).setScale(2, RoundingMode.HALF_UP);
        }
    }

    public static int checkBalance(Long amount, Long balance) {
        if (amount.compareTo(balance) > 0) {
            return 0; //throw new BadRequestException("Not enough balance: " + balance);
        }
        return 1;
    }

    @Override
    public void run(String... args) throws Exception {
//        List<Employee> resultList = new ArrayList<>();
//        Employee employee1 = Employee.builder()
//                .firstName("name1")
//                .lastName("last1")
//                .build();
//        resultList.add(employee1);
//
//        Employee employee2 = Employee.builder()
//                .firstName("name2")
//                .lastName("last2")
//                .build();
//        resultList.add(employee2);
//
//        Employee employee3 = Employee.builder()
//                .firstName("name3")
//                .lastName("last3")
//                .build();
//        resultList.add(employee3);
//
//
//        Employee employee4 = Employee.builder()
//                .firstName("name3")
//                .lastName("last3")
//                .build();
//        resultList.add(employee4);
//
//        repository.saveAll(resultList);
    }
}
