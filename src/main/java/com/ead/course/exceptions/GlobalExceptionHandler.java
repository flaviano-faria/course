package com.ead.course.exceptions;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorRecordResponse> handleNotFoundException(NotFoundException e) {
        ErrorRecordResponse errorResponse = new ErrorRecordResponse(HttpStatus.NOT_FOUND.value(), e.getMessage(), null);
        logger.error("handleNotFoundException message: {}", e.getMessage());
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorRecordResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {

        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
           String fieldName  = ((FieldError) error).getField();
           String errorMessage = error.getDefaultMessage();
           errors.put(fieldName, errorMessage);
        });

        var  errorResponse = new ErrorRecordResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Error: Validation failed", errors);
        logger.error("handleMethodArgumentNotValidException message: {}", e.getMessage());
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorRecordResponse> handleInvalidFormatException(HttpMessageNotReadableException ex){
        Map<String, String> errors = new HashMap<>();
        if(ex.getCause() instanceof InvalidFormatException){
            InvalidFormatException invalidFormatException = (InvalidFormatException) ex.getCause();
            if(invalidFormatException.getTargetType()!=null && invalidFormatException.getTargetType().isEnum()){
                String fieldName = invalidFormatException.getPath().get(invalidFormatException.getPath().size()-1).getFieldName();
                String errorMessage = invalidFormatException.getMessage();
                errors.put(fieldName, errorMessage);
            }
        }
        var  errorResponse = new ErrorRecordResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Error: Invalid enum value", errors);
        logger.error("handleInvalidFormatException message: {}", ex.getMessage());
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

}
