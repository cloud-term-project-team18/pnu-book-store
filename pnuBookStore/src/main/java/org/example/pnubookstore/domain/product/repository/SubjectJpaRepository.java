package org.example.pnubookstore.domain.product.repository;

import org.example.pnubookstore.domain.product.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubjectJpaRepository extends JpaRepository<Subject, Long> {
    Subject findBySubjectNameAndCollegeAndDepartmentAndProfessor(
            String subjectName, String college, String department, String professor);
}
