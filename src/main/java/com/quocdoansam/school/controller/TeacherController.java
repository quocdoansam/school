package com.quocdoansam.school.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quocdoansam.school.dto.request.TeacherCreationRequest;
import com.quocdoansam.school.dto.request.TeacherUpdateRequest;
import com.quocdoansam.school.dto.response.BaseResponse;
import com.quocdoansam.school.dto.response.TeacherResponse;
import com.quocdoansam.school.service.TeacherService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    TeacherService teacherService;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<TeacherResponse>> create(@RequestBody @Valid TeacherCreationRequest request) {
        TeacherResponse teacherResponse = teacherService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                BaseResponse.<TeacherResponse>builder()
                        .success(true)
                        .statusCode(HttpStatus.CREATED.value())
                        .message("The teacher has been created.")
                        .data(teacherResponse)
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<TeacherResponse>> getById(@PathVariable String id) {
        TeacherResponse teacherResponse = teacherService.getById(id);
        return ResponseEntity.ok(
                BaseResponse.<TeacherResponse>builder()
                        .success(true)
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Get teacher successful.")
                        .data(teacherResponse)
                        .build());
    }

    @GetMapping("/all")
    public ResponseEntity<BaseResponse<List<TeacherResponse>>> getAllTeachers() {
        List<TeacherResponse> teacherResponses = teacherService.getAllTeachers();
        return ResponseEntity.ok(
                BaseResponse.<List<TeacherResponse>>builder()
                        .success(true)
                        .statusCode(HttpStatus.OK.value())
                        .message("Get all teachers successful.")
                        .data(teacherResponses)
                        .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<TeacherResponse>> update(@PathVariable String id,
            @RequestBody TeacherUpdateRequest request) {
        TeacherResponse teacherResponse = teacherService.updateById(id, request);
        return ResponseEntity.ok(
                BaseResponse.<TeacherResponse>builder()
                        .success(true)
                        .statusCode(HttpStatus.OK.value())
                        .message("The teacher has been updated.")
                        .data(teacherResponse)
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<?>> deleteById(@PathVariable String id) {
        teacherService.deleteTeacherById(id);
        return ResponseEntity.ok(
                BaseResponse.builder()
                        .success(true)
                        .statusCode(HttpStatus.OK.value())
                        .message("The teacher has been deleted.")
                        .build());
    }

    @DeleteMapping("/all")
    public ResponseEntity<BaseResponse<?>> deleteAllTeachers() {
        teacherService.deleteAllTeacher();
        return ResponseEntity.ok(
                BaseResponse.builder()
                        .success(true)
                        .statusCode(HttpStatus.OK.value())
                        .message("All teacher has been deleted.")
                        .build());
    }
}
