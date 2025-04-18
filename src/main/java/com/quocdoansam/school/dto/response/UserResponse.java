package com.quocdoansam.school.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

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
public class UserResponse {
    String id;
    String fullName;
    LocalDate dob;
    String hometown;
    String address;
    String phoneNumber;
    String email;
    Set<String> roles;
    LocalDateTime updatedAt;
    LocalDateTime createdAt;
}
