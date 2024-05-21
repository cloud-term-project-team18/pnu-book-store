package org.example.pnubookstore.domain.product.repository;

import org.example.pnubookstore.domain.product.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SubjectJpaRepository extends JpaRepository<Subject, Long> {
    Subject findBySubjectNameAndCollegeAndDepartmentAndProfessor(
            String subjectName, String college, String department, String professor);

    @Query("SELECT s.college FROM Subject s")
    List<String> findColleges();

    @Query("SELECT s.department FROM Subject s WHERE s.college = :college")
    List<String> findDepartments(@Param("college") String college);

    @Query("SELECT s.professor FROM Subject s WHERE s.college = :college AND s.department = :department")
    List<String> findProfessors(@Param("college") String college, @Param("department") String department);

    @Query("SELECT s.subjectName FROM Subject s WHERE " +
            "s.college = :college AND s.department = :department AND s.professor = :professor")
    List<String> findSubjectNames(@Param("college") String college,
                                  @Param("department") String department,
                                  @Param("professor") String professor);
}
