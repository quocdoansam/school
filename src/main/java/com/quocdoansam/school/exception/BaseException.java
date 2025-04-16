package com.quocdoansam.school.exception;

import org.springframework.http.HttpStatus;

import com.quocdoansam.school.enums.ErrorMessage;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseException extends RuntimeException {
    HttpStatus status;

    public BaseException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.status = errorMessage.getStatus();
    }
}
