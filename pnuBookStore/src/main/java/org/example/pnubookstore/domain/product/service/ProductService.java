package org.example.pnubookstore.domain.product.service;

import lombok.RequiredArgsConstructor;
import org.example.pnubookstore.core.exception.Exception404;
import org.example.pnubookstore.domain.product.dto.CreateProductDto;
import org.example.pnubookstore.domain.product.ProductExceptionStatus;
import org.example.pnubookstore.domain.product.dto.FindProductDto;
import org.example.pnubookstore.domain.product.dto.FindProductsDto;
import org.example.pnubookstore.domain.product.entity.Product;
import org.example.pnubookstore.domain.product.entity.ProductPicture;
import org.example.pnubookstore.domain.product.entity.Subject;
import org.example.pnubookstore.domain.product.repository.ProductJpaRepository;
import org.example.pnubookstore.domain.product.repository.ProductPictureJpaRepository;
import org.example.pnubookstore.domain.product.repository.SubjectJpaRepository;
import org.example.pnubookstore.domain.product.repository.UserJpaRepositoryForProduct;
import org.example.pnubookstore.domain.user.entity.User;
import org.hibernate.annotations.processing.Find;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

    private final ProductJpaRepository productJpaRepository;
    private final UserJpaRepositoryForProduct userJpaRepositoryForProduct;
    private final SubjectJpaRepository subjectJpaRepository;
    private final ProductPictureJpaRepository productPictureJpaRepository;

    @Transactional
    // 물품 등록
    public void createProduct(CreateProductDto createProductDto){
        final String tempUrl = "http://example.com";

        User findSeller = userJpaRepositoryForProduct.findUserByEmail(createProductDto.getSellerEmail())
                .orElseThrow(() -> new Exception404(ProductExceptionStatus.USER_NOT_FOUND.getErrorMessage()));

        Subject findSubject = subjectJpaRepository.findBySubjectNameAndDepartmentAndProfessor(
                createProductDto.getSubjectName(), createProductDto.getDepartment(), createProductDto.getProfessor())
                .orElseThrow(() -> new Exception404(ProductExceptionStatus.SUBJECT_NOT_FOUND.getErrorMessage()));

        Product createProduct = productJpaRepository.save(
                Product.builder()
                    .seller(findSeller)
                    .subject(findSubject)
                    .productName(createProductDto.getProductName())
                        .price(createProductDto.getPrice())
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

        productPictureJpaRepository.save(
                ProductPicture.builder()
                        .url(tempUrl)
                        .product(createProduct)
                        .build());
    }

    // 물품 조회
    public FindProductDto findProduct(Long productId){
        Product findProduct = productJpaRepository.findById(productId)
                .orElseThrow(() -> new Exception404(ProductExceptionStatus.PRODUCT_NOT_FOUND.getErrorMessage()));

        List<String> productPictureUrlList = productPictureJpaRepository.findAllByProduct(findProduct)
                .orElseThrow(() -> new Exception404(ProductExceptionStatus.PRODUCT_PICTURES_NOT_FOUND.getErrorMessage()))
                .stream()
                .map(ProductPicture::getUrl)
                .toList();

        return FindProductDto.of(findProduct, productPictureUrlList);
    }

    public List<FindProductsDto> findProductList(){
        List<Product> productList = productJpaRepository.findAll();

        List<FindProductsDto> findProductsDtoList = new ArrayList<>();

        for (Product product : productList){
            String pictureUrl = productPictureJpaRepository.findFirstByProduct(product).getUrl();

            findProductsDtoList.add(
                    new FindProductsDto(product.getProductName(), product.getAuthor(), product.getPrice(), pictureUrl)
            );
        }

        return findProductsDtoList;
    }

}
