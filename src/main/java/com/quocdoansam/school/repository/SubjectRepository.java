package com.quocdoansam.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quocdoansam.school.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, String> {
    boolean existsByName(String name);
}
