package com.quocdoansam.school.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.quocdoansam.school.dto.request.StudentCreationRequest;
import com.quocdoansam.school.dto.request.StudentUpdateRequest;
import com.quocdoansam.school.dto.response.StudentResponse;
import com.quocdoansam.school.entity.Student;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface StudentMapper {
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "gpa", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Student toStudentCreationRequest(StudentCreationRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Student toStudentUpdateRequest(StudentUpdateRequest request, @MappingTarget Student student);

    StudentResponse toStudentResponse(Student student);
}
