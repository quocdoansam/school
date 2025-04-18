package com.quocdoansam.school.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quocdoansam.school.dto.request.AuthenticationRequest;
import com.quocdoansam.school.dto.response.AuthenticationResponse;
import com.quocdoansam.school.dto.response.BaseResponse;
import com.quocdoansam.school.service.AuthenticationService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<AuthenticationResponse>> login(@RequestBody AuthenticationRequest request,
            HttpServletResponse response) {
        var result = authenticationService.authenticate(request);
        Cookie cookie = new Cookie("access_token", result.getToken());
        cookie.setHttpOnly(true); // Important: Not allowed JavaScript to read
        cookie.setSecure(true); // Only send cookie through HTTPS (Enable while production)
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 30); // 30 days
        cookie.setAttribute("SameSite", "None"); // Allowed website send cookie cross-origin
        response.addCookie(cookie);

        response.addCookie(cookie);

        return ResponseEntity.ok(
                BaseResponse.<AuthenticationResponse>builder()
                        .success(true)
                        .statusCode(HttpStatus.OK.value())
                        .message("Authenticated.")
                        .data(result)
                        .build());
    }

}
