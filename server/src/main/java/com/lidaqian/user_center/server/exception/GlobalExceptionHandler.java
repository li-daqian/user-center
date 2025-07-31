package com.lidaqian.user_center.server.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Map<String, Object>> handleException(Throwable ex) {
        return handleError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(BindException ex) {
        return handleError(HttpStatus.BAD_REQUEST, getFirstErrorMsg(ex), ex);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequestException(BadRequestException ex) {
        return handleError(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
    }

    private String getFirstErrorMsg(BindException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldErrors().getFirst();
        return fieldError.getField() + " " + fieldError.getDefaultMessage();
    }

    private ResponseEntity<Map<String, Object>> handleError(HttpStatus status, String errorMsg, Throwable ex) {
        log.error(errorMsg, ex);

        Map<String, Object> errorAttributes = new LinkedHashMap<>() {{
            put("status", status.value());
            if (status == HttpStatus.BAD_REQUEST) {
                put("error", errorMsg);
            } else {
                put("error", status.getReasonPhrase());
            }
            put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        }};
        return new ResponseEntity<>(errorAttributes, status);
    }
}