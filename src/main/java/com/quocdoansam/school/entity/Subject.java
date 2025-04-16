package com.quocdoansam.school.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

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

@Table(name = "subject")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Subject {

    @SuppressWarnings("deprecation")
    @Id
    @GeneratedValue(generator = "subject-id-generator")
    @GenericGenerator(name = "subject-id-generator", strategy = "com.quocdoansam.school.util.SubjectIdGenerator")
    @Column(length = 20)
    String id;

    @Column(unique = true, nullable = false)
    String name;

    @Column(nullable = true, length = 3000)
    String description;

    @Column(nullable = false)
    int creditHour;

    @CreationTimestamp
    @Column(updatable = false)
    LocalDate createdAt;
}
