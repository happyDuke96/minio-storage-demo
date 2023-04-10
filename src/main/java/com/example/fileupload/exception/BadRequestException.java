package com.example.fileupload.exception;

import lombok.Getter;
import lombok.Setter;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.sql.Statement;

@Getter
@Setter
public class BadRequestException extends AbstractThrowableProblem {

    String userMessage;
    String devMessage;


    public BadRequestException(String userMessage) {
        super(null,userMessage, Status.NOT_FOUND);
        this.userMessage = userMessage;
    }

    public BadRequestException(String userMessage, String devMessage) {
        super(null,userMessage,Status.NOT_FOUND,devMessage);
        this.userMessage = userMessage;
        this.devMessage = devMessage;
    }
}
