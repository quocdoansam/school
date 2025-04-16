package com.quocdoansam.school.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.quocdoansam.school.dto.response.BaseResponse;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<BaseResponse<Object>> handleGenericException(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseResponse.builder()
                .success(false)
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("An unexpected error occurred.")
                .build());
    }

    @ExceptionHandler(value = BaseException.class)
    ResponseEntity<BaseResponse<Object>> handleBaseException(BaseException exception) {
        return ResponseEntity.status(exception.getStatus()).body(
                BaseResponse.builder()
                        .success(false)
                        .statusCode(exception.getStatus().value())
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<BaseResponse<Object>> handleValidation(MethodArgumentNotValidException exception) {
        return ResponseEntity.status(exception.getStatusCode()).body(
                BaseResponse.builder()
                        .success(false)
                        .statusCode(exception.getStatusCode().value())
                        .message(exception.getMessage())
                        .build());
    }
}
