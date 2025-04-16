package com.quocdoansam.school.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.quocdoansam.school.dto.request.SubjectCreationRequest;
import com.quocdoansam.school.dto.request.SubjectUpdateRequest;
import com.quocdoansam.school.dto.response.SubjectResponse;
import com.quocdoansam.school.entity.Subject;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SubjectMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Subject toCreationSubjectRequest(SubjectCreationRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Subject toUpdateSubjectRequest(SubjectUpdateRequest request, @MappingTarget Subject subject);

    SubjectResponse toSubjectResponse(Subject subject);
}
