package com.quocdoansam.school.repository;

import org.springframework.stereotype.Repository;

import com.quocdoansam.school.entity.Student;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    boolean existsByEmail(String email);
}
