package com.quocdoansam.school.dto.request;

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
public class SubjectCreationRequest {
    @Size(min = 2, max = 255, message = "INVALID_SUBJECT_NAME")
    String name;
    @Size(min = 2, max = 3000, message = "INVALID_SUBJECT_DESCRIPTION")
    String description;
    String creditHour;
}
