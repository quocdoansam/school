package com.quocdoansam.school.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quocdoansam.school.dto.request.TeacherCreationRequest;
import com.quocdoansam.school.dto.request.TeacherUpdateRequest;
import com.quocdoansam.school.dto.response.TeacherResponse;
import com.quocdoansam.school.entity.Teacher;
import com.quocdoansam.school.enums.ErrorMessage;
import com.quocdoansam.school.enums.Role;
import com.quocdoansam.school.exception.BaseException;
import com.quocdoansam.school.mapper.TeacherMapper;
import com.quocdoansam.school.repository.TeacherRepository;

@Service
public class TeacherService {
    @Autowired
    TeacherMapper teacherMapper;
    @Autowired
    TeacherRepository teacherRepository;

    public TeacherResponse create(TeacherCreationRequest request) {
        Teacher teacher = teacherMapper.toTeacherCreationRequest(request);

        Set<String> roles = new HashSet<>();
        roles.add(Role.TEACHER.name());
        teacher.setRoles(roles);

        return teacherMapper.toTeacherResponse(teacherRepository.save(teacher));
    }

    public TeacherResponse getById(String id) {
        return teacherMapper
                .toTeacherResponse(teacherRepository
                        .findById(id)
                        .orElseThrow(() -> new BaseException(ErrorMessage.TEACHER_NOT_FOUND)));
    }

    public List<TeacherResponse> getAllTeachers() {
        return teacherRepository.findAll()
                .stream()
                .map(teacherMapper::toTeacherResponse)
                .toList();
    }

    public TeacherResponse updateById(String id, TeacherUpdateRequest request) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new BaseException(ErrorMessage.TEACHER_NOT_FOUND));

        if (request.getEmail() != null) {
            String newEmail = request.getEmail();
            if (!newEmail.equals(teacher.getEmail())) {
                if (teacherRepository.existsByEmail(newEmail)) {
                    throw new BaseException(ErrorMessage.EMAIL_EXISTED);
                }
            }
        }

        teacher = teacherMapper.toTeacherUpdateRequest(request, teacher);

        return teacherMapper.toTeacherResponse(teacherRepository.save(teacher));
    }

    public void deleteTeacherById(String id) {
        if (!teacherRepository.existsById(id)) {
            throw new BaseException(ErrorMessage.TEACHER_NOT_FOUND);
        }
        teacherRepository.deleteById(id);
    }

    public void deleteAllTeacher() {
        teacherRepository.deleteAll();
    }
}
