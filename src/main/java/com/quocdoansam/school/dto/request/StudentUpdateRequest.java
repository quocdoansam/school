package com.quocdoansam.school.dto.request;

import java.time.LocalDate;
import java.util.Set;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentUpdateRequest {
    @Size(min = 6, max = 50, message = "INVALID_PASSWORD")
    String password;
    @Size(min = 2, max = 50, message = "INVALID_FULL_NAME")
    String fullName;
    LocalDate dob;
    String gender;
    String hometown;
    String address;
    String email;
    Set<String> roles;
}
