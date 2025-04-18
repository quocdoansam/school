package com.quocdoansam.school.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.quocdoansam.school.dto.request.TeacherCreationRequest;
import com.quocdoansam.school.dto.request.TeacherUpdateRequest;
import com.quocdoansam.school.dto.response.TeacherResponse;
import com.quocdoansam.school.entity.Teacher;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TeacherMapper {
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    public Teacher toTeacherCreationRequest(TeacherCreationRequest request);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    public Teacher toTeacherUpdateRequest(TeacherUpdateRequest request, @MappingTarget Teacher teacher);

    public TeacherResponse toTeacherResponse(Teacher teacher);
}