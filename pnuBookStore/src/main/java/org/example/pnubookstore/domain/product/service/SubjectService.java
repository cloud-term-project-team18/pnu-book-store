package org.example.pnubookstore.domain.product.service;

import lombok.RequiredArgsConstructor;
import org.example.pnubookstore.domain.product.dto.CreateProductDto;
import org.example.pnubookstore.domain.product.entity.Subject;
import org.example.pnubookstore.domain.product.repository.SubjectJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectJpaRepository subjectJpaRepository;
    public Subject createSubject(CreateProductDto createProductDto){
        return subjectJpaRepository.save(Subject.builder()
                .subjectName(createProductDto.getSubjectName())
                .department(createProductDto.getDepartment())
                .professor(createProductDto.getProfessor())
                .build());
    }
}
