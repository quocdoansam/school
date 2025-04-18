package com.quocdoansam.school.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.quocdoansam.school.dto.request.StudentCreationRequest;
import com.quocdoansam.school.dto.request.StudentUpdateRequest;
import com.quocdoansam.school.dto.response.BaseResponse;
import com.quocdoansam.school.dto.response.StudentResponse;
import com.quocdoansam.school.service.StudentService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class StudentController {

        @Autowired
        StudentService studentService;

        @PostMapping("/admin/students")
        public ResponseEntity<BaseResponse<StudentResponse>> create(
                        @RequestBody @Valid StudentCreationRequest request) {
                StudentResponse studentResponse = studentService.create(request);
                return ResponseEntity.ok(
                                BaseResponse.<StudentResponse>builder()
                                                .success(true)
                                                .statusCode(HttpStatus.CREATED.value())
                                                .message("The student has been created.")
                                                .data(studentResponse)
                                                .build());
        }

        @GetMapping("/admin/students")
        public ResponseEntity<BaseResponse<List<StudentResponse>>> getAllStudent() {
                List<StudentResponse> students = studentService.getAllStudents();

                return ResponseEntity.ok(
                                BaseResponse.<List<StudentResponse>>builder()
                                                .success(true)
                                                .statusCode(HttpStatus.OK.value())
                                                .message("Get all students successfully.")
                                                .data(students)
                                                .build());
        }

        @GetMapping("/students/{id}")
        public ResponseEntity<BaseResponse<StudentResponse>> getStudentById(@PathVariable String id) {
                StudentResponse studentResponse = studentService.getStudentById(id);
                return ResponseEntity.ok(
                                BaseResponse.<StudentResponse>builder()
                                                .success(true)
                                                .statusCode(HttpStatus.OK.value())
                                                .message("Get student by ID successful.")
                                                .data(studentResponse)
                                                .build());
        }

        @PutMapping("students/{id}")
        public ResponseEntity<BaseResponse<StudentResponse>> update(@PathVariable String id,
                        @RequestBody @Valid StudentUpdateRequest request) {
                StudentResponse studentResponse = studentService.updateStudentById(id, request);
                return ResponseEntity.ok(
                                BaseResponse.<StudentResponse>builder()
                                                .success(true)
                                                .statusCode(HttpStatus.OK.value())
                                                .message("Student information has been updated.")
                                                .data(studentResponse)
                                                .build());
        }

        @DeleteMapping("/students/{id}")
        public ResponseEntity<BaseResponse<StudentResponse>> deleteById(@PathVariable String id) {
                studentService.deleteStudentById(id);
                return ResponseEntity.ok(
                                BaseResponse.<StudentResponse>builder()
                                                .success(true)
                                                .statusCode(HttpStatus.OK.value())
                                                .message("The student has been deleted.")
                                                .build());
        }
}
