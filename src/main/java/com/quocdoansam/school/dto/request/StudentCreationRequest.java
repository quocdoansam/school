package com.quocdoansam.school.dto.request;

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
public class StudentCreationRequest {
    @Size(min = 2, max = 50, message = "INVALID_FULL_NAME")
    String fullName;
    @Size(min = 6, max = 50, message = "INVALID_PASSWORD")
    String password;
    Set<String> roles;
}
