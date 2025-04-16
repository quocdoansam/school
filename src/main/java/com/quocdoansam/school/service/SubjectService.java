package com.quocdoansam.school.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quocdoansam.school.dto.request.SubjectCreationRequest;
import com.quocdoansam.school.dto.request.SubjectUpdateRequest;
import com.quocdoansam.school.dto.response.SubjectResponse;
import com.quocdoansam.school.entity.Subject;
import com.quocdoansam.school.enums.ErrorMessage;
import com.quocdoansam.school.exception.BaseException;
import com.quocdoansam.school.mapper.SubjectMapper;
import com.quocdoansam.school.repository.SubjectRepository;

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
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubjectService {
    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    SubjectMapper subjectMapper;

    public SubjectResponse create(SubjectCreationRequest request) {
        if (subjectRepository.existsByName(request.getName())) {
            throw new BaseException(ErrorMessage.SUBJECT_NAME_EXISTED);
        }

        Subject subject = subjectMapper.toCreationSubjectRequest(request);
        return subjectMapper.toSubjectResponse(subjectRepository.save(subject));
    }

    public SubjectResponse getSubjectById(String id) {
        return subjectMapper.toSubjectResponse(
                subjectRepository.findById(id).orElseThrow(() -> new BaseException(ErrorMessage.SUBJECT_NOT_FOUND)));
    }

    public List<SubjectResponse> getAllSubjects() {
        return subjectRepository.findAll().stream().map(subjectMapper::toSubjectResponse).toList();
    }

    public SubjectResponse updateSubjectById(String id, SubjectUpdateRequest request) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new BaseException(ErrorMessage.SUBJECT_NOT_FOUND));

        if (request.getName() != null && !request.getName().equals(subject.getName())) {
            if (subjectRepository.existsByName(request.getName())) {
                throw new BaseException(ErrorMessage.SUBJECT_NAME_EXISTED);
            }
        }

        subject = subjectMapper.toUpdateSubjectRequest(request, subject);

        return subjectMapper.toSubjectResponse(subjectRepository.save(subject));
    }

    public void deleteSubjectById(String id) {
        if (!subjectRepository.existsById(id)) {
            throw new BaseException(ErrorMessage.SUBJECT_NOT_FOUND);
        }
        subjectRepository.deleteById(id);
    }

    public void deleteAllSubject() {
        subjectRepository.deleteAll();
    }
}
