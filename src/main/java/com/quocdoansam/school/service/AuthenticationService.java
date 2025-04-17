package com.quocdoansam.school.service;

import java.util.Date;
import java.util.Set;
import java.util.StringJoiner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.quocdoansam.school.dto.request.AuthenticationRequest;
import com.quocdoansam.school.dto.response.AuthenticationResponse;
import com.quocdoansam.school.enums.ErrorMessage;
import com.quocdoansam.school.enums.Role;
import com.quocdoansam.school.exception.BaseException;
import com.quocdoansam.school.repository.StudentRepository;
import com.quocdoansam.school.repository.TeacherRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationService {
    StudentRepository studentRepository;
    TeacherRepository teacherRepository;
    PasswordEncoder passwordEncoder;
    @NonFinal
    @Value("${jwt.signerKey}")
    String SIGNERKEY;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        Role role;
        try {
            role = Role.valueOf(request.getRole().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid role specified");
        }

        switch (role) {
            case STUDENT -> {
                var student = studentRepository.findById(request.getId())
                        .orElseThrow(() -> new BaseException(ErrorMessage.WRONG_CREDENTIALS));

                boolean authenticated = passwordEncoder.matches(request.getPassword(), student.getPassword());
                if (!authenticated) {
                    throw new BaseException(ErrorMessage.WRONG_CREDENTIALS);
                }

                String token = generateToken(student.getEmail(), Role.STUDENT.name());
                return AuthenticationResponse.builder()
                        .token(token)
                        .authenticated(true)
                        .build();
            }

            case TEACHER -> {
                var teacher = teacherRepository.findById(request.getId())
                        .orElseThrow(() -> new BaseException(ErrorMessage.WRONG_CREDENTIALS));

                boolean authenticated = passwordEncoder.matches(request.getPassword(), teacher.getPassword());
                if (!authenticated) {
                    throw new BaseException(ErrorMessage.WRONG_CREDENTIALS);

                }

                String token = generateToken(teacher.getEmail(), Role.TEACHER.name());
                return AuthenticationResponse.builder()
                        .token(token)
                        .authenticated(true)
                        .build();
            }

            default -> throw new RuntimeException("Unsupported role");
        }
    }

    String generateToken(String id, String role) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject(id)
                .issuer("School")
                .claim("scope", role)
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24) * 30)) // 30 days
                .build();

        Payload payload = new Payload(claims.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNERKEY.getBytes()));
            return jwsObject.serialize();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate token", e);
        }
    }

    String buildScope(Set<String> roles) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(roles)) {
            roles.forEach(stringJoiner::add);
        }
        return stringJoiner.toString();
    }
}
