package com.example.fileupload.exception;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.spring.web.advice.ProblemHandling;

import java.util.Optional;

public class ExceptionTranslator implements ProblemHandling{

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Problem> handleNotFoundException(BadRequestException e, NativeWebRequest request) {
        Problem problem = Problem
                .builder()
                .withStatus(e.getStatus())
                .withTitle(e.getUserMessage())
                .withDetail(Optional.ofNullable(e.getDevMessage()).orElse(e.getUserMessage()))
                .build();
        return create(e, problem, request);
    }


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Problem> handleNotFoundException(NotFoundException e, NativeWebRequest request) {
        Problem problem = Problem
                .builder()
                .withStatus(e.getStatus())
                .withTitle(e.getUserMessage())
                .withDetail(Optional.ofNullable(e.getDevMessage()).orElse(e.getUserMessage()))
                .build();
        return create(e, problem, request);
    }
}
