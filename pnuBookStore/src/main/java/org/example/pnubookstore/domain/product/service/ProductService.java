package org.example.pnubookstore.domain.product.service;

import lombok.RequiredArgsConstructor;
import org.example.pnubookstore.core.exception.Exception404;
import org.example.pnubookstore.domain.product.dto.CreateProductDto;
import org.example.pnubookstore.domain.product.ProductExceptionStatus;
import org.example.pnubookstore.domain.product.entity.Product;
import org.example.pnubookstore.domain.product.entity.Subject;
import org.example.pnubookstore.domain.product.repository.ProductJpaRepository;
import org.example.pnubookstore.domain.product.repository.SubjectJpaRepository;
import org.example.pnubookstore.domain.product.repository.UserJpaRepositoryForProduct;
import org.example.pnubookstore.domain.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

    private final ProductJpaRepository productJpaRepository;
    private final UserJpaRepositoryForProduct userJpaRepositoryForProduct;
    private final SubjectJpaRepository subjectJpaRepository;

    @Transactional
    public Product createProduct(CreateProductDto createProductDto){
        User findSeller = userJpaRepositoryForProduct.findUserByEmail(createProductDto.getSellerEmail())
                .orElseThrow(() -> new Exception404(ProductExceptionStatus.USER_NOT_FOUND.getErrorMessage()));

        Subject findSubject = subjectJpaRepository.findBySubjectNameAndDepartmentAndProfessor(
                createProductDto.getSubjectName(), createProductDto.getDepartment(), createProductDto.getProfessor())
                .orElseThrow(() -> new Exception404(ProductExceptionStatus.SUBJECT_NOT_FOUND.getErrorMessage()));

        Product product = productJpaRepository.save(Product.builder()
                .seller(findSeller)
                .subject(findSubject)
                .productName(createProductDto.getProductName())
                .description(createProductDto.getDescription())
                .author(createProductDto.getAuthor())
                .pubDate(createProductDto.getPubDate())
                .isBargain(createProductDto.getIsBargain())
                .canBargainReason(createProductDto.getCanBargainReason())
                .saleStatus(createProductDto.getSaleStatus())
                .underline(createProductDto.getUnderline())
                .note(createProductDto.getNote())
                .naming(createProductDto.getNaming())
                .discolor(createProductDto.getDiscolor())
                .damage(createProductDto.getDamage())
                .build());

        return product;
    }


}
