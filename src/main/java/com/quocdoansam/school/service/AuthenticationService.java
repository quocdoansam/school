package com.quocdoansam.school.service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Set;
import java.util.StringJoiner;

import org.springframework.beans.factory.annotation.Autowired;
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

import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthenticationService {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @NonFinal
    @Value("${jwt.signerKey}")
    String SIGNERKEY;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        Role role;
        try {
            role = Role.valueOf(request.getRole().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BaseException(ErrorMessage.INVALID_ROLE_SPECIFIED);
        }
        switch (role) {
            case STUDENT -> {
                var student = studentRepository.findById(request.getId())
                        .orElseThrow(() -> new BaseException(ErrorMessage.WRONG_CREDENTIALS));

                boolean authenticated = passwordEncoder.matches(request.getPassword(), student.getPassword());
                if (!authenticated) {
                    throw new BaseException(ErrorMessage.WRONG_CREDENTIALS);
                }

                String token = generateToken(student.getId(), student.getRoles());
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

                String token = generateToken(teacher.getId(), teacher.getRoles());
                return AuthenticationResponse.builder()
                        .token(token)
                        .authenticated(true)
                        .build();
            }
            default -> throw new BaseException(ErrorMessage.UNSUPPORTED_ROLE);
        }
    }

    String generateToken(String id, Set<String> roles) {
        try {
            Instant now = Instant.now();
            Instant expiry = now.plus(Duration.ofDays(30));

            JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .subject(id)
                    .issuer("School")
                    .claim("scope", roles)
                    .issueTime(Date.from(now))
                    .expirationTime(Date.from(expiry))
                    .build();

            Payload payload = new Payload(claims.toJSONObject());
            JWSObject jwsObject = new JWSObject(header, payload);
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
