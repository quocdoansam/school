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

@Table(name = "teacher")
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Teacher {
    @SuppressWarnings("deprecation")
    @Id
    @GeneratedValue(generator = "teacher-id-generator")
    @GenericGenerator(name = "teacher-id-generator", strategy = "com.quocdoansam.school.util.TeacherIdGenerator")
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
