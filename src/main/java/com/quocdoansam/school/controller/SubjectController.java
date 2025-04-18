package com.quocdoansam.school.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.quocdoansam.school.dto.request.SubjectCreationRequest;
import com.quocdoansam.school.dto.request.SubjectUpdateRequest;
import com.quocdoansam.school.dto.response.BaseResponse;
import com.quocdoansam.school.dto.response.SubjectResponse;
import com.quocdoansam.school.service.SubjectService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class SubjectController {
        @Autowired
        SubjectService subjectService;

        @GetMapping("/subjects/{id}")
        public ResponseEntity<BaseResponse<SubjectResponse>> getSubjectById(@PathVariable String id) {
                SubjectResponse subjectResponse = subjectService.getSubjectById(id);
                return ResponseEntity.ok(
                                BaseResponse.<SubjectResponse>builder()
                                                .success(true)
                                                .statusCode(HttpStatus.OK.value())
                                                .message("Get subject by ID successful.")
                                                .data(subjectResponse)
                                                .build());
        }

        @GetMapping("/admin/subjects")
        public ResponseEntity<BaseResponse<List<SubjectResponse>>> getAllSubjects() {
                List<SubjectResponse> subjectResponses = subjectService.getAllSubjects();

                return ResponseEntity.ok(
                                BaseResponse.<List<SubjectResponse>>builder()
                                                .success(true)
                                                .statusCode(HttpStatus.OK.value())
                                                .message("Get all subjects successful.")
                                                .data(subjectResponses)
                                                .build());
        }

        @PostMapping("/admin/subjects")
        public ResponseEntity<BaseResponse<SubjectResponse>> create(@RequestBody SubjectCreationRequest request) {

                SubjectResponse subjectResponse = subjectService.create(request);

                return ResponseEntity.status(HttpStatus.CREATED).body(
                                BaseResponse.<SubjectResponse>builder()
                                                .success(true)
                                                .statusCode(HttpStatus.CREATED.value())
                                                .message("The subject has been created.")
                                                .data(subjectResponse)
                                                .build());
        }

        @PutMapping("/admin/subjects/{id}")
        public ResponseEntity<BaseResponse<SubjectResponse>> update(@PathVariable String id,
                        @RequestBody SubjectUpdateRequest request) {
                SubjectResponse subjectResponse = subjectService.updateSubjectById(id, request);
                return ResponseEntity.ok(
                                BaseResponse.<SubjectResponse>builder()
                                                .success(true)
                                                .statusCode(HttpStatus.OK.value())
                                                .message("The subject has been updated.")
                                                .data(subjectResponse)
                                                .build());
        }

        @DeleteMapping("/admin/subjects/{id}")
        public ResponseEntity<BaseResponse<?>> deleteSubjectById(@PathVariable String id) {
                subjectService.deleteSubjectById(id);
                return ResponseEntity.ok(
                                BaseResponse.builder()
                                                .success(true)
                                                .statusCode(HttpStatus.OK.value())
                                                .message("The subject has been deleted.")
                                                .build());
        }

        @DeleteMapping("/admin/subjects")
        public ResponseEntity<BaseResponse<?>> deleteAllSubjects() {
                subjectService.deleteAllSubject();
                return ResponseEntity.ok(
                                BaseResponse.builder()
                                                .success(true)
                                                .statusCode(HttpStatus.OK.value())
                                                .message("All subjects has been deleted.")
                                                .build());
        }
}
