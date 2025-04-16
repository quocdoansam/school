package com.quocdoansam.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quocdoansam.school.entity.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, String> {
    boolean existsByEmail(String email);
}
