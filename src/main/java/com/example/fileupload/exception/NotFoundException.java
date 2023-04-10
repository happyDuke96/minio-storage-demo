package com.example.fileupload.exception;

import lombok.Getter;
import lombok.Setter;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

@Getter
@Setter
public class NotFoundException extends AbstractThrowableProblem {

    String userMessage;
    String devMessage;

    public NotFoundException(String userMessage) {
        this.userMessage = userMessage;
    }

    public NotFoundException(String userMessage, String devMessage) {
        super(null, userMessage, Status.NOT_FOUND, devMessage);
        this.userMessage = userMessage;
        this.devMessage = devMessage;
    }
}
