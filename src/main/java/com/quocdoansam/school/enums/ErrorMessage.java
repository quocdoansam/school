package com.quocdoansam.school.enums;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorMessage {
    INVALID_FULL_NAME(HttpStatus.BAD_REQUEST, "The full name must be at least 2 up to 50 characters."),
    STUDENT_NOT_FOUND(HttpStatus.NOT_FOUND, "The student not found."),
    EMAIL_EXISTED(HttpStatus.BAD_REQUEST, "The email has existed."),

    SUBJECT_NOT_FOUND(HttpStatus.NOT_FOUND, "The subject not found."),
    SUBJECT_NAME_EXISTED(HttpStatus.BAD_REQUEST, "The subject name has been existed."),

    TEACHER_NOT_FOUND(HttpStatus.NOT_FOUND, "The teacher not found."),
    WRONG_CREDENTIALS(HttpStatus.UNAUTHORIZED, "Wrong username or password. Try again."),
    ;

    HttpStatus status;
    String message;
}
