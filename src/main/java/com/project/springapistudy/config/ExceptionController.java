package com.project.springapistudy.config;

import com.project.springapistudy.common.DBException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@Slf4j
@RestControllerAdvice
public class ExceptionController {
    private final MessageSource messageSource;

    public ExceptionController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(DBException.DataNotFound.class)
    public ResponseEntity<String> handleDataNotFound(DBException.DataNotFound e){
        log.error("{} : {}", this.getClass().getSimpleName(),e.getMessage());
        return new ResponseEntity<>(messageSource.getMessage("read.fail", null, Locale.getDefault()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationError(MethodArgumentNotValidException e){
        log.error("{} : {}", this.getClass().getSimpleName(),e.getMessage());
        return new ResponseEntity<>(e.getBindingResult().getAllErrors().get(0).getDefaultMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolation(DataIntegrityViolationException e){
        log.error("{} : {}", e.getClass().getSimpleName(), e.getMessage());
        return new ResponseEntity<>(messageSource.getMessage("save.fail", null, Locale.getDefault()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleJSOnParseError(HttpMessageNotReadableException e){
        log.error("{} : {}", e.getClass().getSimpleName(), e.getMessage());
        return new ResponseEntity<>(messageSource.getMessage("request.error", null, Locale.getDefault()), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e){
        log.error("{} : {}", e.getClass().getSimpleName(), e.getMessage());
        return new ResponseEntity<>(messageSource.getMessage("internal.error", null, Locale.getDefault()), HttpStatus.EXPECTATION_FAILED);
    }
}
