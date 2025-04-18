package com.quocdoansam.school.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@Table(name = "student")
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Student {

    @Id
    String id;

    @OneToOne
    @JoinColumn(name = "id")
    User user;

    @OneToOne
    String majorId;

    double gpa;

    @UpdateTimestamp
    LocalDateTime updatedAdt;
}
