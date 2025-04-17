package com.quocdoansam.school.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quocdoansam.school.dto.request.AuthenticationRequest;
import com.quocdoansam.school.dto.response.AuthenticationResponse;
import com.quocdoansam.school.dto.response.BaseResponse;
import com.quocdoansam.school.service.AuthenticationService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<AuthenticationResponse>> login(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(request);
        return ResponseEntity.ok(
                BaseResponse.<AuthenticationResponse>builder()
                        .success(true)
                        .statusCode(HttpStatus.OK.value())
                        .message("Authenticated.")
                        .data(authenticationResponse)
                        .build());
    }

}
