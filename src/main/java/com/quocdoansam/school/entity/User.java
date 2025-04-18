package com.quocdoansam.school.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Table(name = "users")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @SuppressWarnings("deprecation")
    @Id
    @GeneratedValue(generator = "student-id-generator")
    @GenericGenerator(name = "student-id-generator", strategy = "com.quocdoansam.school.util.StudentIdGenerator")
    @Column(length = 20)
    String id;

    @Column(nullable = false)
    String password;

    @Column(nullable = false)
    String fullName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate dob;

    String gender;
    String hometown;
    String address;
    String email;

    Set<String> roles;

    @CreationTimestamp
    @Column(updatable = false)
    LocalDateTime createdAt;
}
