package com.example.fileupload;

import com.example.fileupload.config.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.math.BigDecimal;
import java.math.RoundingMode;

@SpringBootApplication
@EnableConfigurationProperties({ApplicationProperties.class})
public class FileUploadApplication {

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
}
