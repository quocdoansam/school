package com.quocdoansam.school.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.quocdoansam.school.dto.request.StudentCreationRequest;
import com.quocdoansam.school.dto.request.StudentUpdateRequest;
import com.quocdoansam.school.dto.response.StudentResponse;
import com.quocdoansam.school.entity.Student;
import com.quocdoansam.school.enums.ErrorMessage;
import com.quocdoansam.school.enums.Role;
import com.quocdoansam.school.exception.BaseException;
import com.quocdoansam.school.mapper.StudentMapper;
import com.quocdoansam.school.repository.StudentRepository;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    PasswordEncoder passwordEncoder;

    public StudentResponse create(StudentCreationRequest request) {
        Student student = studentMapper.toStudentCreationRequest(request);

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.STUDENT.name());
        student.setRoles(roles);

        student.setPassword(passwordEncoder.encode(request.getPassword()));

        return studentMapper.toStudentResponse(studentRepository.save(student));
    }

    public List<StudentResponse> getAllStudents() {
        return studentRepository.findAll().stream().map(studentMapper::toStudentResponse).toList();
    }

    public StudentResponse getStudentById(String id) {
        return studentMapper
                .toStudentResponse(studentRepository
                        .findById(id)
                        .orElseThrow(() -> new BaseException(ErrorMessage.STUDENT_NOT_FOUND)));
    }

    public StudentResponse updateStudentById(String id, StudentUpdateRequest request) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new BaseException(ErrorMessage.STUDENT_NOT_FOUND));

        if (request.getEmail() != null && !request.getEmail().equals(student.getEmail())) {
            if (studentRepository.existsByEmail(request.getEmail())) {
                throw new BaseException(ErrorMessage.EMAIL_EXISTED);
            }
        }

        student = studentMapper.toStudentUpdateRequest(request, student);

        return studentMapper.toStudentResponse(studentRepository.save(student));
    }

    public void deleteStudentById(String id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
        } else {
            throw new BaseException(ErrorMessage.STUDENT_NOT_FOUND);
        }
    }
}
